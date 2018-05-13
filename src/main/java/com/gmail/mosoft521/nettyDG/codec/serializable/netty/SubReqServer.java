package com.gmail.mosoft521.nettyDG.codec.serializable.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SubReqServer {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new SubReqServer().bind(port);
    }

    public void bind(int port) throws Exception {
        // 配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new SubReqServerHandler());
                        }
                    });

            // 绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();

            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
/*
Service accept client subscrib req : [SubscribeReq [subReqID=0, userName=Lilinfeng, productName=Netty 最佳实践和原理分析, phoneNumber=138xxxxxxxxx, address=南京市雨花台区软件大道101号华为基地]]
Service accept client subscrib req : [SubscribeReq [subReqID=1, userName=Lilinfeng, productName=Netty 最佳实践和原理分析, phoneNumber=138xxxxxxxxx, address=南京市雨花台区软件大道101号华为基地]]
Service accept client subscrib req : [SubscribeReq [subReqID=2, userName=Lilinfeng, productName=Netty 最佳实践和原理分析, phoneNumber=138xxxxxxxxx, address=南京市雨花台区软件大道101号华为基地]]
Service accept client subscrib req : [SubscribeReq [subReqID=3, userName=Lilinfeng, productName=Netty 最佳实践和原理分析, phoneNumber=138xxxxxxxxx, address=南京市雨花台区软件大道101号华为基地]]
Service accept client subscrib req : [SubscribeReq [subReqID=4, userName=Lilinfeng, productName=Netty 最佳实践和原理分析, phoneNumber=138xxxxxxxxx, address=南京市雨花台区软件大道101号华为基地]]
Service accept client subscrib req : [SubscribeReq [subReqID=5, userName=Lilinfeng, productName=Netty 最佳实践和原理分析, phoneNumber=138xxxxxxxxx, address=南京市雨花台区软件大道101号华为基地]]
Service accept client subscrib req : [SubscribeReq [subReqID=6, userName=Lilinfeng, productName=Netty 最佳实践和原理分析, phoneNumber=138xxxxxxxxx, address=南京市雨花台区软件大道101号华为基地]]
Service accept client subscrib req : [SubscribeReq [subReqID=7, userName=Lilinfeng, productName=Netty 最佳实践和原理分析, phoneNumber=138xxxxxxxxx, address=南京市雨花台区软件大道101号华为基地]]
Service accept client subscrib req : [SubscribeReq [subReqID=8, userName=Lilinfeng, productName=Netty 最佳实践和原理分析, phoneNumber=138xxxxxxxxx, address=南京市雨花台区软件大道101号华为基地]]
Service accept client subscrib req : [SubscribeReq [subReqID=9, userName=Lilinfeng, productName=Netty 最佳实践和原理分析, phoneNumber=138xxxxxxxxx, address=南京市雨花台区软件大道101号华为基地]]

 */