package io.homeey.gateway.bootstrap;

import io.homeey.gateway.common.exception.GatewayException;
import io.homeey.gateway.config.routing.*;
import io.homeey.gateway.core.dispatcher.ErrorHandler;
import io.homeey.gateway.core.dispatcher.GatewayDispatcher;
import io.homeey.gateway.core.filter.FilterChainFactory;
import io.homeey.gateway.core.filter.UpstreamInvoker;
import io.homeey.gateway.plugin.api.context.GatewayContext;
import io.homeey.gateway.plugin.runtime.manager.DefaultPluginManager;
import io.homeey.gateway.routing.api.Route;
import io.homeey.gateway.routing.api.RouteTable;
import io.homeey.gateway.transport.netty.server.NettyHttpServer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 22:49 2025-12-07
 **/
public class GatewayApplication {
    static void main(String[] args) throws InterruptedException {
        RouteTableHolder routeTableHolder = new RouteTableHolder();
        RouteTable routeTable = buildRouteTable();
        routeTableHolder.update(routeTable);

        DefaultRouteLocator routeLocator = new DefaultRouteLocator(routeTableHolder);

        // plugin manager
        DefaultPluginManager manager = new DefaultPluginManager();
        // 加载插件
        Path path = Paths.get("plugins");
        manager.loadAndRegisterPlugins(path);
        //重建路由表
        manager.rebuildRoutePlugins(routeTable.getAllRoutes());

        ExecutorService executorService = Executors.newFixedThreadPool(16);
        FilterChainFactory filterChainFactory = new FilterChainFactory(manager, executorService);

        UpstreamInvoker upstreamInvoker = new UpstreamInvoker() {
            @Override
            public CompletionStage<Void> invoke(GatewayContext context) {
                String path = context.getRequest().path();
                context.getResponse().setStatus(200);
                context.getResponse().setHeaders("Content-Type", "text/plain; charset=utf-8");
                try {
                    context.getResponse().getBodyOutput().write(("Hello from gateway, path=" + path).getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    throw new GatewayException("Failed to write response", e);
                }
                return CompletableFuture.completedFuture(null);
            }
        };

        ErrorHandler errorHandler = new ErrorHandler();

        GatewayDispatcher gatewayDispatcher = new GatewayDispatcher(routeLocator,
                filterChainFactory,
                upstreamInvoker,
                errorHandler);

        NettyHttpServer nettyHttpServer = new NettyHttpServer(80, gatewayDispatcher);
        nettyHttpServer.start();
    }


    private static RouteTable buildRouteTable() {
        DefaultUpstream upstream = new DefaultUpstream("dummy-upstream", List.of("http://127.0.0.1:8080"));

        Map<String, Object> hrConfig = new HashMap<>();
        HashMap<String, String> responseAdd = new HashMap<>();
        responseAdd.put("X-Gateway-By", "Netty-Gateway");
        hrConfig.put("responseAdd", responseAdd);

        Route.PluginBinding binding = new DefaultPluginBinding("header-rewrite", true, hrConfig);
        List<Route.PluginBinding> bindings = List.of(binding);

        Route route = new DefaultRoute("dummy-route",
                "localhost",
                "/api/**",
                "GET", upstream,
                bindings);


        var map = new HashMap<String, Route>();
        map.put(route.id(), route);
        return new DefaultRouteTable(map);
    }
}
