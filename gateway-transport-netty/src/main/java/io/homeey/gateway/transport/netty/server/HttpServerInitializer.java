package io.homeey.gateway.transport.netty.server;

import io.homeey.gateway.core.dispatcher.GatewayDispatcher;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 22:06 2025-12-07
 **/
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private final GatewayDispatcher dispatcher;

    public HttpServerInitializer(GatewayDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("log", new LoggingHandler(LogLevel.DEBUG));
        pipeline.addLast("httpCodec", new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(64 * 1024));
        pipeline.addLast("handler", new GatewayInboundHandler(dispatcher));
    }
}
