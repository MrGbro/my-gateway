package io.homeey.gateway.plugin.api.plugin;

import io.homeey.gateway.plugin.api.context.GatewayContext;

import java.util.concurrent.CompletionStage;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:26 2025-12-06
 **/
public interface AsyncGatewayPlugin {

    /**
     * 异步执行插件逻辑
     *
     * @param context 网关上下文，包含请求和响应相关信息
     * @return CompletableFuture形式的插件执行结果
     */
    CompletionStage<PluginResult> executeAsync(GatewayContext context);
}
