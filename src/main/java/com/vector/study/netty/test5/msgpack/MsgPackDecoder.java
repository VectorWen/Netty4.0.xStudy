package com.vector.study.netty.test5.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.util.List;

/**
 * author: vector.huang
 * date: 2016/12/15 09:57
 */
public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {

        int length = msg.readableBytes();
        byte[] arr = new byte[length];
        msg.getBytes(msg.readerIndex(),arr,0,length);

        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(arr);
        String name = unpacker.unpackString();
        out.add(name);
    }
}
