package com.vector.study.netty.test4.pojo.handler;

import com.vector.study.netty.test4.pojo.model.TimeModel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * author: vector.huang
 * date：2016/4/16 18:08
 */
public class TimeEncoderServerHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        TimeModel timeModel = (TimeModel) msg;
        ByteBuf byteBuf = ctx.alloc().buffer(8);
        byteBuf.writeLong(timeModel.timeMillis());
        ctx.write(byteBuf,promise);
    }
}
