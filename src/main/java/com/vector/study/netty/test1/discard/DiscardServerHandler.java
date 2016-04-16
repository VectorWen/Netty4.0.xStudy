package com.vector.study.netty.test1.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/** 
 * 
 * 这是一个Handler(处理器)，用来处理I/O 事件的。<br/>
 * Netty 把每一个动作、每一个改变都称为事件，每一个事件都是可被处理的。<br/>
 * <br/>
 * 理解ChannelInboundHandlerAdapter
 * <li> Channel -- 处理通道的
 * <li> Inbound -- 入站的事件
 * <li> Handler -- 是一个处理器
 * <li> Adapter -- 空实现了接口ChannelInboundHandler，不想实现的方法咋不要
 * <br />
 * 一个处理入站消息的处理器（入站：从外面进来）<br/>
 * 
 * use<br />
 * <br />
 * 加入到PipelineChannel 中，用来处理入站的消息事件<br />
 * pipelineChannel.addLast(new DiscardServerHandler());
 *
 * @author VectorHuang
 * @email  642378415@qq.com
 * @date 2016年1月17日 下午8:55:12 
 * @version 1.0 
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter{

	/**
	 * 当现实的Channel 有可读消息的时候该方法被调用。
	 * 
	 * @parameter ctx 这次通信的上下文
	 * @parameter msg 该例子中这个对象是ByteBuf 对象，是一个引用计数对象，必须显性调用release() 方法
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
	}

	/**
	 * 出现异常的时候调用该方法
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		cause.printStackTrace();
		ctx.close();
		
	}
	
}
