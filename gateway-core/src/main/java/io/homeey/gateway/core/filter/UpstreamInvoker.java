package io.homeey.gateway.core.filter;

import io.homeey.gateway.plugin.api.context.GatewayContext;

import java.util.concurrent.CompletionStage;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 18:16 2025-12-06
 **/
public interface UpstreamInvoker {
    /**
     * 调用上游服务
     *
     * @param context 网关上下文，包含请求和响应的相关信息
     * @return 异步执行结果，表示调用完成后的状态
     */
    CompletionStage<Void> invoke(GatewayContext context);
}
