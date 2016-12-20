package com.vector.study.task.day2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * author: vector.huang
 * date: 2016/12/19 09:29
 */
public class Day2Server {

    public static void run(int port){
        EventLoopGroup bossEvent = new NioEventLoopGroup();
        EventLoopGroup workEvent = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossEvent,workEvent)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4,true))
                                    .addLast(new MessageToByteEncoder<String>() {
                                        @Override
                                        protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
                                            byte[] data = msg.getBytes();
                                            out.writeInt(data.length).writeBytes(data);
                                        }
                                    })
                                    .addLast(new StringDecoder())
                                    .addLast(new ChannelInboundHandlerAdapter(){
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            ctx.channel().writeAndFlush("Hello Client Day2");
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println(msg);
                                        }
                                    });
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture future = b.bind(port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossEvent.shutdownGracefully();
            workEvent.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        run(8080);
    }

}
