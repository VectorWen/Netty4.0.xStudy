package com.vector.study.netty.test3.pojo;

import java.text.SimpleDateFormat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * author: vector.huang
 * date：2016/4/16 17:43
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        long time = (long) msg;
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(time);
        System.out.println(date);
    }
}
