package com.gmail.mosoft521.nettyDG.frame.correct;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeServer {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new TimeServer().bind(port);
    }

    public void bind(int port) throws Exception {
        // 配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());
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

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel arg0) throws Exception {
            arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));
            arg0.pipeline().addLast(new StringDecoder());
            arg0.pipeline().addLast(new TimeServerHandler());
        }
    }
}
/*
The time server receive order : QUERY TIME ORDER ; the counter is : 1
The time server receive order : QUERY TIME ORDER ; the counter is : 2
The time server receive order : QUERY TIME ORDER ; the counter is : 3
The time server receive order : QUERY TIME ORDER ; the counter is : 4
The time server receive order : QUERY TIME ORDER ; the counter is : 5
The time server receive order : QUERY TIME ORDER ; the counter is : 6
The time server receive order : QUERY TIME ORDER ; the counter is : 7
The time server receive order : QUERY TIME ORDER ; the counter is : 8
The time server receive order : QUERY TIME ORDER ; the counter is : 9
The time server receive order : QUERY TIME ORDER ; the counter is : 10
The time server receive order : QUERY TIME ORDER ; the counter is : 11
The time server receive order : QUERY TIME ORDER ; the counter is : 12
The time server receive order : QUERY TIME ORDER ; the counter is : 13
The time server receive order : QUERY TIME ORDER ; the counter is : 14
The time server receive order : QUERY TIME ORDER ; the counter is : 15
The time server receive order : QUERY TIME ORDER ; the counter is : 16
The time server receive order : QUERY TIME ORDER ; the counter is : 17
The time server receive order : QUERY TIME ORDER ; the counter is : 18
The time server receive order : QUERY TIME ORDER ; the counter is : 19
The time server receive order : QUERY TIME ORDER ; the counter is : 20
The time server receive order : QUERY TIME ORDER ; the counter is : 21
The time server receive order : QUERY TIME ORDER ; the counter is : 22
The time server receive order : QUERY TIME ORDER ; the counter is : 23
The time server receive order : QUERY TIME ORDER ; the counter is : 24
The time server receive order : QUERY TIME ORDER ; the counter is : 25
The time server receive order : QUERY TIME ORDER ; the counter is : 26
The time server receive order : QUERY TIME ORDER ; the counter is : 27
The time server receive order : QUERY TIME ORDER ; the counter is : 28
The time server receive order : QUERY TIME ORDER ; the counter is : 29
The time server receive order : QUERY TIME ORDER ; the counter is : 30
The time server receive order : QUERY TIME ORDER ; the counter is : 31
The time server receive order : QUERY TIME ORDER ; the counter is : 32
The time server receive order : QUERY TIME ORDER ; the counter is : 33
The time server receive order : QUERY TIME ORDER ; the counter is : 34
The time server receive order : QUERY TIME ORDER ; the counter is : 35
The time server receive order : QUERY TIME ORDER ; the counter is : 36
The time server receive order : QUERY TIME ORDER ; the counter is : 37
The time server receive order : QUERY TIME ORDER ; the counter is : 38
The time server receive order : QUERY TIME ORDER ; the counter is : 39
The time server receive order : QUERY TIME ORDER ; the counter is : 40
The time server receive order : QUERY TIME ORDER ; the counter is : 41
The time server receive order : QUERY TIME ORDER ; the counter is : 42
The time server receive order : QUERY TIME ORDER ; the counter is : 43
The time server receive order : QUERY TIME ORDER ; the counter is : 44
The time server receive order : QUERY TIME ORDER ; the counter is : 45
The time server receive order : QUERY TIME ORDER ; the counter is : 46
The time server receive order : QUERY TIME ORDER ; the counter is : 47
The time server receive order : QUERY TIME ORDER ; the counter is : 48
The time server receive order : QUERY TIME ORDER ; the counter is : 49
The time server receive order : QUERY TIME ORDER ; the counter is : 50
The time server receive order : QUERY TIME ORDER ; the counter is : 51
The time server receive order : QUERY TIME ORDER ; the counter is : 52
The time server receive order : QUERY TIME ORDER ; the counter is : 53
The time server receive order : QUERY TIME ORDER ; the counter is : 54
The time server receive order : QUERY TIME ORDER ; the counter is : 55
The time server receive order : QUERY TIME ORDER ; the counter is : 56
The time server receive order : QUERY TIME ORDER ; the counter is : 57
The time server receive order : QUERY TIME ORDER ; the counter is : 58
The time server receive order : QUERY TIME ORDER ; the counter is : 59
The time server receive order : QUERY TIME ORDER ; the counter is : 60
The time server receive order : QUERY TIME ORDER ; the counter is : 61
The time server receive order : QUERY TIME ORDER ; the counter is : 62
The time server receive order : QUERY TIME ORDER ; the counter is : 63
The time server receive order : QUERY TIME ORDER ; the counter is : 64
The time server receive order : QUERY TIME ORDER ; the counter is : 65
The time server receive order : QUERY TIME ORDER ; the counter is : 66
The time server receive order : QUERY TIME ORDER ; the counter is : 67
The time server receive order : QUERY TIME ORDER ; the counter is : 68
The time server receive order : QUERY TIME ORDER ; the counter is : 69
The time server receive order : QUERY TIME ORDER ; the counter is : 70
The time server receive order : QUERY TIME ORDER ; the counter is : 71
The time server receive order : QUERY TIME ORDER ; the counter is : 72
The time server receive order : QUERY TIME ORDER ; the counter is : 73
The time server receive order : QUERY TIME ORDER ; the counter is : 74
The time server receive order : QUERY TIME ORDER ; the counter is : 75
The time server receive order : QUERY TIME ORDER ; the counter is : 76
The time server receive order : QUERY TIME ORDER ; the counter is : 77
The time server receive order : QUERY TIME ORDER ; the counter is : 78
The time server receive order : QUERY TIME ORDER ; the counter is : 79
The time server receive order : QUERY TIME ORDER ; the counter is : 80
The time server receive order : QUERY TIME ORDER ; the counter is : 81
The time server receive order : QUERY TIME ORDER ; the counter is : 82
The time server receive order : QUERY TIME ORDER ; the counter is : 83
The time server receive order : QUERY TIME ORDER ; the counter is : 84
The time server receive order : QUERY TIME ORDER ; the counter is : 85
The time server receive order : QUERY TIME ORDER ; the counter is : 86
The time server receive order : QUERY TIME ORDER ; the counter is : 87
The time server receive order : QUERY TIME ORDER ; the counter is : 88
The time server receive order : QUERY TIME ORDER ; the counter is : 89
The time server receive order : QUERY TIME ORDER ; the counter is : 90
The time server receive order : QUERY TIME ORDER ; the counter is : 91
The time server receive order : QUERY TIME ORDER ; the counter is : 92
The time server receive order : QUERY TIME ORDER ; the counter is : 93
The time server receive order : QUERY TIME ORDER ; the counter is : 94
The time server receive order : QUERY TIME ORDER ; the counter is : 95
The time server receive order : QUERY TIME ORDER ; the counter is : 96
The time server receive order : QUERY TIME ORDER ; the counter is : 97
The time server receive order : QUERY TIME ORDER ; the counter is : 98
The time server receive order : QUERY TIME ORDER ; the counter is : 99
The time server receive order : QUERY TIME ORDER ; the counter is : 100
 */