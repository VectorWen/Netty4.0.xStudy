package com.vector.study.task.day1;

import com.vector.study.entity.HelloProtoBuf;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * author: vector.huang
 * date: 2016/12/17 15:21
 */
public class Day1ProtoServer {

    public static void run(int port) {
        EventLoopGroup bossEvent = new NioEventLoopGroup();
        EventLoopGroup workEvent = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossEvent, workEvent)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
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
                                            int readable = in.readableBytes();
                                            byte[] bytes = new byte[readable];
                                            in.writeBytes(bytes,in.readerIndex(),readable);
                                            HelloProtoBuf.Info info = HelloProtoBuf.Info.parseFrom(bytes);
                                            out.add(info);
                                        }
                                    })
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println(msg);

                                            HelloProtoBuf.Info info = HelloProtoBuf.Info.newBuilder()
                                                    .setId(1).setName("I'm ProtoBuf Server!!").build();

                                            ctx.channel().writeAndFlush(info);
                                        }

                                    });
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture future = b.bind(port).sync();

            System.out.println("启动成功");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossEvent.shutdownGracefully();
            workEvent.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        run(9090);
    }

}
