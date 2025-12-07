package io.homeey.gateway.config.routing;

import io.homeey.gateway.common.enums.HostTagEnum;
import io.homeey.gateway.plugin.api.context.GatewayRequest;
import io.homeey.gateway.routing.api.Route;
import io.homeey.gateway.routing.api.RouteLocator;
import io.homeey.gateway.routing.api.RouteTable;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 21:40 2025-12-07
 **/
public class DefaultRouteLocator implements RouteLocator {

    private final RouteTableHolder holder;

    public DefaultRouteLocator(RouteTableHolder holder) {
        this.holder = holder;
    }


    @Override
    public Route locate(GatewayRequest request) {
        RouteTable current = holder.current();
        if (current == null) {
            return null;
        }

        String reqHost = request.getHeader("Host");
        String reqPath = request.path();
        String reqMethod = request.method();

        for (Route route : current.getAllRoutes()) {
            if (route.host() != null) {
                HostTagEnum cfTag = HostTagEnum.fromValue(route.host());
                HostTagEnum reqTag = HostTagEnum.fromValue(reqHost);
                if (reqHost == null) {
                    continue;
                }
                if (cfTag == reqTag) {
                    if (cfTag == null && !reqHost.equalsIgnoreCase(route.host())) {
                        continue;
                    }
                } else {
                    continue;
                }
            }
            if (route.method() != null && !route.method().equalsIgnoreCase(reqMethod)) {
                continue;
            }
            //这里后面可以更精准点
            // 处理路径匹配，支持正则表达式和通配符模式
            String routePath = route.path();
            if (routePath != null) {
                // 处理 ** 通配符模式（如 /api/** 匹配 /api/test）
                if (routePath.endsWith("/**")) {
                    String prefix = routePath.substring(0, routePath.length() - 3);
                    if (!reqPath.startsWith(prefix)) {
                        continue;
                    }
                }
                // 处理正则表达式模式（以 ^ 开头或以 $ 结尾）
                else if (routePath.startsWith("^") || routePath.endsWith("$")) {
                    if (!reqPath.matches(routePath)) {
                        continue;
                    }
                }
                // 普通前缀匹配
                else if (!reqPath.startsWith(routePath)) {
                    continue;
                }
            }
            return route;
        }
        return null;
    }
}
