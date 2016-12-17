package com.vector.study.task.day1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * author: vector.huang
 * date: 2016/12/17 09:16
 */
public class Day1Client {

    private static void run(int port) throws Exception {
        EventLoopGroup workEvent = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workEvent)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println(msg);
                                        }

                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            ctx.channel().writeAndFlush("Hello Server!!");
                                        }
                                    });
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = bootstrap.connect("127.0.0.1", port).sync();
            System.out.println("已经连接成功");
            future.channel().closeFuture().sync();
        } finally {
            workEvent.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        run(8080);
    }

}
