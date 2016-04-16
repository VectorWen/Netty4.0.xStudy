package com.vector.study.netty.test3.decoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * author: vector.huang
 * date：2016/4/16 13:18
 */
public class TimeDecoderClient {

    /**
     * 编写服务端和客户端最大的并且唯一不同的使用了不同的BootStrap 和 Channel的实现。
     */

    public void run(String host,int port) throws InterruptedException {

        EventLoopGroup worker = new NioEventLoopGroup();

        try {

            Bootstrap boot = new Bootstrap();
            boot.group(worker);

            boot.channel(NioSocketChannel.class);

            boot.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {

                    /**
                     * 添加两个handler
                     */

                    ch.pipeline().addLast(new TimeDecoderClientHandler());
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });

            boot.option(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture f = boot.connect(host,port).sync();

            f.channel().closeFuture().sync();

        }finally {
            worker.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new TimeDecoderClient().run("127.0.0.1",8080);
    }

}
