package com.vector.study.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * active 之后会就发送一个字符串
 * author: vector.huang
 * date: 2016/12/15 10:36
 */
public class SimpleStringHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

    }
}
