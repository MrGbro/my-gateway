package io.homeey.gateway.core.filter;

import io.homeey.gateway.plugin.api.context.GatewayContext;
import io.homeey.gateway.plugin.api.filter.GatewayFilter;
import io.homeey.gateway.plugin.api.filter.GatewayFilterChain;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:49 2025-12-06
 **/
public class DefaultGatewayFilterChain implements GatewayFilterChain {

    private final List<GatewayFilter> filters;
    private final int index;
    private final UpstreamInvoker upstreamInvoker;

    public DefaultGatewayFilterChain(List<GatewayFilter> filters,
                                     UpstreamInvoker upstreamInvoker) {
        this.filters = filters;
        this.index = 0;
        this.upstreamInvoker = upstreamInvoker;
    }

    public DefaultGatewayFilterChain(List<GatewayFilter> filters,
                                     int index,
                                     UpstreamInvoker upstreamInvoker) {
        this.filters = filters;
        this.index = index;
        this.upstreamInvoker = upstreamInvoker;
    }


    @Override
    public CompletionStage<Void> filter(GatewayContext context) {
        if (index == filters.size()) {
            return upstreamInvoker.invoke(context);
        }
        GatewayFilter current = filters.get(index);
        DefaultGatewayFilterChain next = new DefaultGatewayFilterChain(filters, index + 1, upstreamInvoker);
        try {
            return current.filter(context, next);
        } catch (Throwable ex) {
            CompletableFuture<Void> future = new CompletableFuture<>();
            future.completeExceptionally(ex);
            return future;
        }
    }
}
