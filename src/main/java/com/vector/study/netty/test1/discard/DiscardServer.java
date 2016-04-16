package com.vector.study.netty.test1.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author VectorHuang
 * @version 1.0
 * @email 642378415@qq.com
 * @date 2016年1月17日 下午8:55:12
 */
public class DiscardServer {

    private int mPort;

    public DiscardServer(int port) {
        mPort = port;
    }

    public void run() throws Exception {

        /**
         * NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，
         * Netty 提供了许多不同的 EventLoopGroup 的实现用来处理不同的传输。
         * 在这个例子中我们实现了一个服务端的应用，因此会有2个 NioEventLoopGroup 会被使用。
         * 第一个经常被叫做‘boss’，用来接收进来的连接。
         * 第二个经常被叫做‘worker’，用来处理已经被接收的连接。
         * 一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
         * 如何知道多少个线程已经被使用，如何映射到已经创建的 Channel上都需要依赖于 EventLoopGroup 的实现，
         * 并且可以通过构造函数来配置他们的关系。
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(); //boss 多线程事件循环器
        EventLoopGroup workerGroup = new NioEventLoopGroup();//worker 多线程事件循环器
        try {
            /**
             * ServerBootstrap 是一个启动 NIO 服务的辅助启动类。
             * 你可以在这个服务中直接使用 Channel，但是这会是一个复杂的处理过程，
             * 在很多情况下你并不需要这样做。
             */
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            /**
             * 当客户端连接上来的时候就会产生新的Channel
             * NioServerSocketChannel 类指定了该新的 Channel 如何连接进来
             * Nio就是异步连接进来
             */
            b.channel(NioServerSocketChannel.class);
            /**
             * 当有新的Channel 连接进来之后就会调用该ChannelInitializer来初始化该Channel
             * 主要工作就是添加很多ChannelHandler 到PipelineChannel上，用来处理数据
             * 当你的程序变的复杂时，可能你会增加更多的处理类到 pipline 上
             */
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    /**
                     * 当有新的Channel需要处理的时候
                     * 把DiscardServerHandler 添加到PipelineChannel 上
                     */
                    ch.pipeline().addLast(new DiscardServerHandler());
                }
            });

            /**
             * 你可以设置这里指定的 Channel 实现的配置参数。
             * 我们正在写一个TCP/IP 的服务端，因此我们被允许
             * 设置 socket 的参数选项比如tcpNoDelay 和 keepAlive。
             * 请参考 ChannelOption 和详细的 ChannelConfig 实现的接口文档
             * 以此可以对ChannelOption 的有一个大概的认识。
             */
            b.option(ChannelOption.SO_BACKLOG, 128);
            /**
             * 你关注过 option() 和 childOption() 吗？
             * option() 是提供给NioServerSocketChannel 用来接收进来的连接。
             * childOption() 是提供给由父管道 ServerChannel 接收到的连接，
             * 在这个例子中也是 NioServerSocketChannel。
             */
            b.childOption(ChannelOption.SO_KEEPALIVE, true);
            b.childOption(ChannelOption.TCP_NODELAY, true);

            /**
             * 绑定端口，开始接收进来的连接
             */
            ChannelFuture f = b.bind(mPort).sync();

            /**
             * 等待服务器  socket 关闭 。
             * 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
             */
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new DiscardServer(8080).run();
    }

}
