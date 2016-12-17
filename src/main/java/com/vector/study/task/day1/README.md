### 第一天犯的错误及总结

1. SO_BACKLOG 忘记socket 的缓存是通过backlog 来配置了, backlog 积压的意思
2. ChannelInboundHandler 管道入境处理器, Netty把新连接进来、写数据进来当成入境(Inbound)
3. bootstrap.bind(port).sync(); 在服务端的时候调用这个, bind 用来绑定端口,但是这个是异步的,sync 是用来同步。当方法返回的时候证明端口绑定成功
4. future.channel().closeFuture().sync(); 等待服务端端口被关闭, 这里会等待连接了