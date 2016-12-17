package com.vector.study.task.day1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * author: vector.huang
 * date: 2016/12/17 08:55
 */
public class Day1Server {

    public static void run(int port) throws Exception{

        EventLoopGroup bossEvent = new NioEventLoopGroup();
        EventLoopGroup workEvent = new NioEventLoopGroup();

        try{

            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossEvent, workEvent)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new ChannelInboundHandlerAdapter(){
                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println(msg);
                                        }

                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {

                                            ctx.channel().write("Hello Client!!");                                  ctx.channel().writeAndFlush("Hello Client!!");

                                        }
                                    });
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.TCP_NODELAY,true);

            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("服务器启动成功");
            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();

        }finally {
            bossEvent.shutdownGracefully();
            workEvent.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        run(8080);
    }
}
