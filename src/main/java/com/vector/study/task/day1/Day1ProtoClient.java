package com.vector.study.task.day1;

import com.vector.study.entity.HelloProtoBuf;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * author: vector.huang
 * date: 2016/12/17 15:35
 */
public class Day1ProtoClient {

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
                                    .addLast(new ByteToMessageCodec<HelloProtoBuf.Info>() {
                                        @Override
                                        protected void encode(ChannelHandlerContext ctx, HelloProtoBuf.Info msg, ByteBuf out) throws Exception {
                                            out.writeBytes(msg.toByteArray());
                                        }

                                        @Override
                                        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                                            out.add(HelloProtoBuf.Info.parseFrom(in.array()));
                                        }
                                    })
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println(msg);
                                        }

                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            HelloProtoBuf.Info info = HelloProtoBuf.Info.newBuilder().setId(2)
                                                    .setName("1").build();
                                            ctx.channel().writeAndFlush(info);
                                        }
                                    });
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = b.connect("127.0.0.1", port).sync();

            System.out.println("连接成功");

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workEvent.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        run(9090);
    }

}
