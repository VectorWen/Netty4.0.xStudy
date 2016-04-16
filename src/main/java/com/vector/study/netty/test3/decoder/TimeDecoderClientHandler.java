package com.vector.study.netty.test3.decoder;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * author: vector.huang
 * date：2016/4/16 17:40
 */
public class TimeDecoderClientHandler extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes()>=8){
            long time = in.readLong();
            out.add(time);
        }
    }
}
