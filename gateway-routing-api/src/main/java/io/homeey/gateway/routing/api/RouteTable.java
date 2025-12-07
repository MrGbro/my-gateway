package io.homeey.gateway.routing.api;

import java.util.List;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 23:16 2025-12-06
 **/
public interface RouteTable {
    /**
     * 根据路由ID获取路由信息
     * 
     * @param id 路由ID
     * @return 路由对象，如果未找到则返回null
     */
    Route getById(String id);

    /**
     * 获取所有路由信息
     * 
     * @return 路由列表
     */
    List<Route> getAllRoutes();
}
