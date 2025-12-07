package io.homeey.gateway.core.dispatcher;

import io.homeey.gateway.plugin.api.context.GatewayContext;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:48 2025-12-06
 **/
public class ErrorHandler {
    public void handleNotFound(GatewayContext ctx) {
        ctx.getResponse().setStatus(404);
        //具体由transport层处理
    }

    public void handleException(GatewayContext ctx, Throwable e) {
        ctx.setError(e);
        ctx.getResponse().setStatus(500);
        //具体由transport层处理
        //具体可以交给ON_ERROR插件处理
    }
}
