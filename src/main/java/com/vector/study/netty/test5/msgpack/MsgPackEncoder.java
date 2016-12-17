package com.vector.study.netty.test5.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

/**
 * author: vector.huang
 * date: 2016/12/15 09:52
 */
public class MsgPackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        packer.packString(msg.toString()).close();
        out.writeBytes(packer.toByteArray());

    }
}
