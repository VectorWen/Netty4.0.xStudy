package com.vector.study.netty.test4.pojo.handler;

import com.vector.study.netty.test4.pojo.model.TimeModel;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * author: vector.huang
 * date：2016/4/16 18:18
 */
public class TimeDecoderClientHandler extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes()>=8){
            out.add(new TimeModel(in.readLong()));
        }
    }
}
