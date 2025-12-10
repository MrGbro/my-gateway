package io.homeey.gateway.core.filter;

import io.homeey.gateway.plugin.api.context.GatewayContext;
import io.homeey.gateway.plugin.api.filter.GatewayFilter;
import io.homeey.gateway.plugin.api.filter.GatewayFilterChain;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:49 2025-12-06
 **/
public class DefaultGatewayFilterChain implements GatewayFilterChain {

    private final List<? extends GatewayFilter> filters;
    private final int index;
    private final Supplier<CompletionStage<Void>> terminalSupplier;

    public DefaultGatewayFilterChain(List<? extends GatewayFilter> filters,
                                     Supplier<CompletionStage<Void>> terminalSupplier) {
        this(filters, 0, terminalSupplier);
    }

    public DefaultGatewayFilterChain(List<? extends GatewayFilter> filters,
                                     int index,
                                     Supplier<CompletionStage<Void>> terminalSupplier) {
        this.filters = filters;
        this.index = index;
        this.terminalSupplier = terminalSupplier;
    }


    @Override
    public CompletionStage<Void> filter(GatewayContext context) {
        if (index == filters.size()) {
            return terminalSupplier.get();
        }
        GatewayFilter current = filters.get(index);
        DefaultGatewayFilterChain next = new DefaultGatewayFilterChain(filters, index + 1, terminalSupplier);
        try {
            return current.filter(context, next);
        } catch (Throwable ex) {
            CompletableFuture<Void> future = new CompletableFuture<>();
            future.completeExceptionally(ex);
            return future;
        }
    }
}
