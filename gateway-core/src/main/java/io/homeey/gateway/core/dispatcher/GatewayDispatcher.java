package io.homeey.gateway.core.dispatcher;

import io.homeey.gateway.common.exception.GatewayException;
import io.homeey.gateway.core.filter.DefaultGatewayFilterChain;
import io.homeey.gateway.core.filter.FilterChainFactory;
import io.homeey.gateway.core.filter.UpstreamInvoker;
import io.homeey.gateway.plugin.api.ExecutionPhase;
import io.homeey.gateway.plugin.api.context.GatewayContext;
import io.homeey.gateway.plugin.api.filter.GatewayFilter;
import io.homeey.gateway.routing.api.Route;
import io.homeey.gateway.routing.api.RouteLocator;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:48 2025-12-06
 **/
public class GatewayDispatcher {
    private final RouteLocator routeLocator;
    private final FilterChainFactory filterChainFactory;
    private final UpstreamInvoker upstreamInvoker;
    private final ErrorHandler errorHandler;

    public GatewayDispatcher(RouteLocator routeLocator, FilterChainFactory filterChainFactory, UpstreamInvoker upstreamInvoker, ErrorHandler errorHandler) {
        this.routeLocator = routeLocator;
        this.filterChainFactory = filterChainFactory;
        this.upstreamInvoker = upstreamInvoker;
        this.errorHandler = errorHandler;
    }

    public void handle(GatewayContext ctx) {
        doBeforeRouting(ctx)
                .thenCompose(v -> doBeforeRouting(ctx))
                .thenCompose(v -> doRouting(ctx))
                .thenCompose(route -> doAfterRouting(ctx, route))
                .thenCompose(route -> doUpstream(ctx, route))
                .thenCompose(route -> doAfterUpstream(ctx, route))
                .exceptionally(e -> {
                    errorHandler.handleException(ctx, e);
                    return null;
                });
    }

    private CompletionStage<Void> executeFilters(GatewayContext ctx, Route route, ExecutionPhase phase) {
        List<? extends GatewayFilter> gatewayFilters = filterChainFactory.buildPreUpstreamFilters(route, phase);
        DefaultGatewayFilterChain chain = new DefaultGatewayFilterChain(gatewayFilters, () -> CompletableFuture.completedFuture(null));
        ctx.setCurrentPhase(phase);
        return chain.filter(ctx);
    }

    private CompletionStage<Void> doBeforeRouting(GatewayContext ctx) {
        return executeFilters(ctx, null, ExecutionPhase.BEFORE_ROUTING);
    }

    private CompletionStage<Route> doRouting(GatewayContext ctx) {
        try {
            Route route = routeLocator.locate(ctx.getRequest());
            if (route == null) {
                errorHandler.handleNotFound(ctx);
                CompletableFuture<Route> failure = new CompletableFuture<>();
                failure.completeExceptionally(new GatewayException("Route Not Found"));
                return failure;
            }
            ctx.setRouterId(route.id());
            return CompletableFuture.completedFuture(route);
        } catch (Exception e) {
            CompletableFuture<Route> failure = new CompletableFuture<>();
            failure.completeExceptionally(e);
            return failure;
        }
    }

    private CompletionStage<Route> doAfterRouting(GatewayContext ctx, Route route) {
        return executeFilters(ctx, route, ExecutionPhase.AFTER_ROUTING).thenApply(v -> route);
    }

    private CompletionStage<Route> doUpstream(GatewayContext ctx, Route route) {
        ctx.setCurrentPhase(ExecutionPhase.BEFORE_UPSTREAM);
        return upstreamInvoker.invoke(ctx).thenApply(v -> route);
    }

    private CompletionStage<Route> doAfterUpstream(GatewayContext ctx, Route route) {
        return executeFilters(ctx, route, ExecutionPhase.AFTER_UPSTREAM).thenApply(v -> route);
    }
}
