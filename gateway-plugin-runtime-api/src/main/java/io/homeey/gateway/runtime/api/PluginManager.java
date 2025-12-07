package io.homeey.gateway.runtime.api;

import io.homeey.gateway.plugin.api.ExecutionPhase;
import io.homeey.gateway.plugin.api.plugin.GatewayPlugin;
import io.homeey.gateway.routing.api.Route;

import java.util.List;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 23:27 2025-12-06
 **/
public interface PluginManager {

    /**
     * 根据路由和执行阶段获取对应的插件列表
     *
     * @param route 路由信息
     * @param phase 执行阶段
     * @return 适用于该路由和执行阶段的插件列表
     */
    List<GatewayPlugin> getPluginsForRouteAndPhase(Route route, ExecutionPhase phase);
}
