###关于 Socket Buffer的一个小警告

基于流的传输比如 TCP/IP, 接收到数据是存在 socket 接收的 buffer 中。
不幸的是，基于流的传输并不是一个数据包队列，而是一个字节队列。
意味着，即使你发送了2个独立的数据包，操作系统也不会作为2个消息处理而仅仅是作为一连串的字节而言。
因此这是不能保证你远程写入的数据就会准确地读取。

举个例子，让我们假设操作系统的 TCP/TP 协议栈已经接收了3个数据包：

ABC   DEF    GHI

由于基于流传输的协议的这种普通的性质，在你的应用程序里读取数据的时候会有很高的可能性被分成下面的片段

AB   CDE  FG  H   I

###Solution 1

```java

CacheBufClientHandler

```

###Solution 2

```java

TimeDecoderClientHandler extends ByteToMessageDecoder
TimeClientHandler extends ChannelInboundHandlerAdapter

```