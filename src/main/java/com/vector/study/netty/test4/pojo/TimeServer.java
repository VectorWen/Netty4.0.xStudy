package com.vector.study.netty.test4.pojo;

import com.vector.study.netty.test4.pojo.handler.TimeEncoderServerHandler;
import com.vector.study.netty.test4.pojo.handler.TimePOJOServerHandler;

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
 * date：2016/4/16 18:16
 */
public class TimeServer {

    /**
     * 这个例子没有什么变化，不写注释
     * 也希望通过这个方式让我们去看代码
     * @param port
     * @throws InterruptedException
     */
    public void run(int port) throws InterruptedException {

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(boss, worker);

            boot.channel(NioServerSocketChannel.class);
            boot.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeEncoderServerHandler());
                    ch.pipeline().addLast(new TimePOJOServerHandler());
                }
            });

            boot.option(ChannelOption.SO_BACKLOG, 128);
            boot.childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = boot.bind(port).sync();
            future.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new TimeServer().run(8080);
    }

}
