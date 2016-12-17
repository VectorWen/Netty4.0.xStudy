package com.vector.study.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * author: vector.huang
 * date: 2016/12/15 09:46
 */
public class EasiestServer {

    public void run(ChannelInitializer<SocketChannel> channelInitializer, int port) throws InterruptedException {

        EventLoopGroup bossEventLoop = new NioEventLoopGroup();
        EventLoopGroup workEventLoop = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossEventLoop, workEventLoop)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channelInitializer)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = serverBootstrap.bind(port).sync();

            future.channel().close();
        } finally {
            bossEventLoop.shutdownGracefully();
            workEventLoop.shutdownGracefully();
        }
    }

}
