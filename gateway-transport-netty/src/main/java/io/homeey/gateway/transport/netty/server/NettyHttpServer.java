package io.homeey.gateway.transport.netty.server;

import io.homeey.gateway.core.dispatcher.GatewayDispatcher;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 22:05 2025-12-07
 **/
public class NettyHttpServer {
    private final int port;
    private final GatewayDispatcher gatewayDispatcher;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public NettyHttpServer(int port, GatewayDispatcher gatewayDispatcher) {
        this.port = port;
        this.gatewayDispatcher = gatewayDispatcher;
    }

    public void start() throws InterruptedException {
        bossGroup = new MultiThreadIoEventLoopGroup(1, NioIoHandler.newFactory());
        workerGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer(gatewayDispatcher))
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture bindFuture = server.bind(port).sync();
            System.out.println("Server started on port " + port);
            bindFuture.channel().closeFuture().sync();
        } finally {
            shutdown();
        }
    }

    private void shutdown() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}
