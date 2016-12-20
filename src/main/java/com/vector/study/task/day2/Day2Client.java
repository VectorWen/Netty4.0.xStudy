package com.vector.study.task.day2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * author: vector.huang
 * date: 2016/12/19 09:36
 */
public class Day2Client {

    public static void run(int port) {
        EventLoopGroup workEvent = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workEvent)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(22, 0, 4, 0, 4, false))
                                    .addLast(new MessageToByteEncoder<String>() {
                                        @Override
                                        protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
                                            byte[] data = msg.getBytes();
                                            out.writeInt(data.length).writeBytes(data);
                                        }
                                    })
                                    .addLast(new StringDecoder())
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            for (int i = 0; i < 100; i++) {
                                                ctx.channel().writeAndFlush("Hello Server Day" + i);
                                            }
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println(msg);
                                        }
                                    });
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = b.connect("127.0.0.1", port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workEvent.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        run(8080);
    }
}
