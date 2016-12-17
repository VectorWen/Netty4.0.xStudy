package com.vector.study.netty.test5.msgpack;

import com.vector.study.netty.EasiestServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * author: vector.huang
 * date: 2016/12/15 09:56
 */
public class ServerMain {

    public static void main(String[] args) throws InterruptedException {
        new EasiestServer().run(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
            }
        },8089);
    }

}
