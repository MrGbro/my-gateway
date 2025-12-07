package io.homeey.gateway.core.filter;

import io.homeey.gateway.common.exception.PluginException;
import io.homeey.gateway.plugin.api.*;
import io.homeey.gateway.plugin.api.context.GatewayContext;
import io.homeey.gateway.plugin.api.context.GatewayResponse;
import io.homeey.gateway.plugin.api.filter.GatewayFilter;
import io.homeey.gateway.plugin.api.filter.GatewayFilterChain;
import io.homeey.gateway.plugin.api.plugin.*;

import java.util.concurrent.*;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:49 2025-12-06
 **/
public class PluginFilterAdapter implements GatewayFilter {

    private final GatewayPlugin plugin;
    private final ExecutionPhase phase;
    private final ExecutorService businessExecutor;

    public PluginFilterAdapter(GatewayPlugin plugin,
                               ExecutionPhase phase,
                               ExecutorService businessExecutor) {
        this.plugin = plugin;
        this.phase = phase;
        this.businessExecutor = businessExecutor;
    }

    @Override
    public CompletionStage<Void> filter(GatewayContext context,
                                        GatewayFilterChain chain) {
        if (plugin.getSupportedPhases().contains(phase)) {
            return chain.filter(context);
        }
        context.setCurrentPhase(phase);
        if (plugin instanceof SyncGatewayPlugin syncPlugin) {
            return executeSync(syncPlugin, context, chain);
        }
        if (plugin instanceof AsyncGatewayPlugin asyncPlugin) {
            return executeAsync(asyncPlugin, context, chain);
        }
        return chain.filter(context);
    }

    private CompletionStage<Void> executeAsync(AsyncGatewayPlugin asyncPlugin,
                                               GatewayContext context,
                                               GatewayFilterChain chain) {
        return asyncPlugin.executeAsync(context)
                .thenCompose(result -> handleResult(result, context, chain));
    }

    private CompletionStage<Void> executeSync(SyncGatewayPlugin syncPlugin,
                                              GatewayContext context,
                                              GatewayFilterChain chain) {
        Callable<PluginResult> task = () -> syncPlugin.executeSync(context);
        CompletionStage<PluginResult> resultStage;
        boolean inEventLoop = context.isInEventLoop();
        boolean isBlocking = plugin.getClass().isAnnotationPresent(BlockingPlugin.class);
        if (inEventLoop && isBlocking) {
            resultStage = CompletableFuture.supplyAsync(() -> {
                try {
                    return task.call();
                } catch (Exception e) {
                    throw new CompletionException(e);
                }
            }, businessExecutor);
        } else {
            CompletableFuture<PluginResult> future = new CompletableFuture<>();
            try {
                future.complete(task.call());
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
            resultStage = future;
        }
        return resultStage.thenCompose(result -> handleResult(result, context, chain));
    }

    private CompletionStage<Void> handleResult(PluginResult result,
                                               GatewayContext context,
                                               GatewayFilterChain chain) {
        if (!result.isSuccess()) {
            throw new CompletionException(new PluginException("Plugin execution failed: " + plugin.getName()));
        }

        if (!result.isContinueChain()) {
            Object earlyResponse = result.getEarlyResponse();
            if (earlyResponse != null) {
                //这里先预留，后续决定如何实现
            }
            return CompletableFuture.completedFuture(null);
        }
        return chain.filter(context);
    }
}
