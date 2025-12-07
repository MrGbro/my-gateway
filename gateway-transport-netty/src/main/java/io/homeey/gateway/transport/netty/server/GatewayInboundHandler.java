package io.homeey.gateway.transport.netty.server;

import io.homeey.gateway.core.context.DefaultGatewayContext;
import io.homeey.gateway.core.dispatcher.GatewayDispatcher;
import io.homeey.gateway.transport.netty.bridge.NettyGatewayRequest;
import io.homeey.gateway.transport.netty.bridge.NettyGatewayResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 22:07 2025-12-07
 **/
public class GatewayInboundHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final GatewayDispatcher dispatcher;

    public GatewayInboundHandler(GatewayDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        boolean isInEventLoop = ctx.channel().eventLoop().inEventLoop();

        NettyGatewayRequest request = new NettyGatewayRequest(msg);
        NettyGatewayResponse response = new NettyGatewayResponse();
        DefaultGatewayContext context = new DefaultGatewayContext(request, response, isInEventLoop);
        //调用Core层处理
        dispatcher.handle(context);

        writeResponse(ctx, msg, response);
    }

    private void writeResponse(ChannelHandlerContext ctx, FullHttpRequest req, NettyGatewayResponse gwResponse) {
        //todo
        byte[] bodyBytes = gwResponse.getBodyBytes();
        boolean keepAlive = HttpUtil.isKeepAlive(req);
        DefaultFullHttpResponse nettyResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.valueOf(gwResponse.getStatus()),
                ctx.alloc().buffer(bodyBytes.length).writeBytes(bodyBytes));

        gwResponse.getHeaders().forEach((name, value) -> nettyResponse.headers().set(name, value));
        nettyResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, bodyBytes.length);
        if (keepAlive) {
            nettyResponse.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.writeAndFlush(nettyResponse);
        } else {
            ctx.writeAndFlush(nettyResponse).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //todo 打日志
        ctx.close();
    }
}
