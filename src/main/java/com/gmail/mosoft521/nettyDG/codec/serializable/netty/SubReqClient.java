package com.gmail.mosoft521.nettyDG.codec.serializable.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class SubReqClient {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new SubReqClient().connect(port, "127.0.0.1");
    }

    public void connect(int port, String host) throws Exception {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new SubReqClientHandler());
                        }
                    });

            // 发起异步连接操作
            ChannelFuture f = b.connect(host, port).sync();

            // 当代客户端链路关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }
}
/*
Receive server response : [SubscribeResp [subReqID=0, respCode=0, desc=Netty book order succeed, 3 days later, sent to the designated address]]
Receive server response : [SubscribeResp [subReqID=1, respCode=0, desc=Netty book order succeed, 3 days later, sent to the designated address]]
Receive server response : [SubscribeResp [subReqID=2, respCode=0, desc=Netty book order succeed, 3 days later, sent to the designated address]]
Receive server response : [SubscribeResp [subReqID=3, respCode=0, desc=Netty book order succeed, 3 days later, sent to the designated address]]
Receive server response : [SubscribeResp [subReqID=4, respCode=0, desc=Netty book order succeed, 3 days later, sent to the designated address]]
Receive server response : [SubscribeResp [subReqID=5, respCode=0, desc=Netty book order succeed, 3 days later, sent to the designated address]]
Receive server response : [SubscribeResp [subReqID=6, respCode=0, desc=Netty book order succeed, 3 days later, sent to the designated address]]
Receive server response : [SubscribeResp [subReqID=7, respCode=0, desc=Netty book order succeed, 3 days later, sent to the designated address]]
Receive server response : [SubscribeResp [subReqID=8, respCode=0, desc=Netty book order succeed, 3 days later, sent to the designated address]]
Receive server response : [SubscribeResp [subReqID=9, respCode=0, desc=Netty book order succeed, 3 days later, sent to the designated address]]
 */