package io.homeey.gateway.plugin.api.filter;

import io.homeey.gateway.plugin.api.context.GatewayContext;

import java.util.concurrent.CompletionStage;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:31 2025-12-06
 **/
public interface GatewayFilter {

    /**
     * 执行过滤器逻辑
     *
     * @param context 网关上下文，包含请求和响应信息
     * @param chain   过滤器链，用于继续执行下一个过滤器
     * @return CompletableFuture<Void> 异步执行结果
     */
    CompletionStage<Void> filter(GatewayContext context, GatewayFilterChain chain);
}
