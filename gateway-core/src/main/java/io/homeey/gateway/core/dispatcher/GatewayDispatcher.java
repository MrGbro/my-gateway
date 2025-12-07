package io.homeey.gateway.core.dispatcher;

import io.homeey.gateway.core.filter.DefaultGatewayFilterChain;
import io.homeey.gateway.core.filter.FilterChainFactory;
import io.homeey.gateway.core.filter.UpstreamInvoker;
import io.homeey.gateway.plugin.api.context.GatewayContext;
import io.homeey.gateway.plugin.api.filter.GatewayFilter;
import io.homeey.gateway.routing.api.Route;
import io.homeey.gateway.routing.api.RouteLocator;

import java.util.List;
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

    public GatewayDispatcher(RouteLocator routeLocator,
                             FilterChainFactory filterChainFactory,
                             UpstreamInvoker upstreamInvoker,
                             ErrorHandler errorHandler) {
        this.routeLocator = routeLocator;
        this.filterChainFactory = filterChainFactory;
        this.upstreamInvoker = upstreamInvoker;
        this.errorHandler = errorHandler;
    }

    public void handle(GatewayContext ctx) {
        try {
            Route route = routeLocator.locate(ctx.getRequest());
            if (route == null) {
                errorHandler.handleNotFound(ctx);
                return;
            }
            ctx.setRouterId(route.id());

            List<GatewayFilter> gatewayFilters = filterChainFactory.buildPreUpstreamFilters(route);
            DefaultGatewayFilterChain chain = new DefaultGatewayFilterChain(gatewayFilters, upstreamInvoker);
            CompletionStage<Void> stage = chain.filter(ctx);
            stage.whenComplete((v, ex) -> {
                if (ex != null) {
                    errorHandler.handleException(ctx, ex);
                }
            });
        } catch (Exception e) {
            errorHandler.handleException(ctx, e);
        }
    }
}
