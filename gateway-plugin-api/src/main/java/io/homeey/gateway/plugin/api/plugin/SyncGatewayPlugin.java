package io.homeey.gateway.plugin.api.plugin;

import io.homeey.gateway.plugin.api.context.GatewayContext;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:25 2025-12-06
 **/
public interface SyncGatewayPlugin extends GatewayPlugin {

    /**
     * 同步执行插件逻辑
     *
     * @param context 网关上下文，包含请求和响应相关信息
     * @return 插件执行结果
     * @throws Exception 执行过程中可能抛出的异常
     */
    PluginResult executeSync(GatewayContext context) throws Exception;
}
