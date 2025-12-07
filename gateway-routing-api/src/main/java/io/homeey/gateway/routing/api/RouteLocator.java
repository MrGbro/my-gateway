package io.homeey.gateway.routing.api;

import io.homeey.gateway.plugin.api.context.GatewayRequest;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 23:16 2025-12-06
 **/
public interface RouteLocator {
    /**
     * 根据请求对象定位路由
     *
     * @param request 请求对象，包含路由查找所需的信息
     * @return 匹配的路由信息，如果未找到则返回null
     */
    Route locate(GatewayRequest request);
}
