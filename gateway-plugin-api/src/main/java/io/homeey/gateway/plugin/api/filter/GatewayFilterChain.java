package io.homeey.gateway.plugin.api.filter;

import io.homeey.gateway.plugin.api.context.GatewayContext;

import java.util.concurrent.CompletionStage;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:39 2025-12-06
 **/
public interface GatewayFilterChain {
    /**
     * 执行网关过滤器链
     *
     * @param context 网关上下文对象，包含请求和响应信息
     * @return CompletionStage<Void> 异步执行结果
     */
    CompletionStage<Void> filter(GatewayContext context);
}
