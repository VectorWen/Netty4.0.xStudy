package com.vector.study.netty.test3.decoder;

import java.text.SimpleDateFormat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * author: vector.huang
 * date：2016/4/16 17:29
 */
public class CacheBufClientHandler extends ChannelInboundHandlerAdapter {

    private ByteBuf buf;

    /**
     * 生命周期的开始
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        buf = ctx.alloc().buffer(4);
    }

    /**
     * 生命周期的结束
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        buf.release();
        buf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        /**
         * 可读的数据加入到缓存中
         */
        ByteBuf m = (ByteBuf) msg;
        buf.writeBytes(m);
        m.release();

        /**
         * 只有够了8个字节的数据才读，不然还是留在缓存中
         * 读了之后就关闭channel
         */
        if(buf.readableBytes() >= 8){
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(buf.readLong());
            System.out.println(time);
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
