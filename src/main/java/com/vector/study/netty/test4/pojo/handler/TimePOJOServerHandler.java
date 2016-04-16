package com.vector.study.netty.test4.pojo.handler;

import com.vector.study.netty.test4.pojo.model.TimeModel;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * author: vector.huang
 * date：2016/4/16 18:14
 */
public class TimePOJOServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelFuture f = ctx.writeAndFlush(new TimeModel());
        f.addListener(ChannelFutureListener.CLOSE);
    }
}
