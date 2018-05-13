package com.gmail.mosoft521.nettyDG.frame.correct;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeClient {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new TimeClient().connect(port, "127.0.0.1");
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
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new TimeClientHandler());
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
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 1
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 2
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 3
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 4
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 5
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 6
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 7
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 8
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 9
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 10
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 11
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 12
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 13
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 14
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 15
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 16
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 17
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 18
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 19
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 20
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 21
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 22
Now is : Thu Mar 17 17:03:24 CST 2016 ; the counter is : 23
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 24
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 25
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 26
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 27
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 28
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 29
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 30
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 31
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 32
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 33
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 34
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 35
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 36
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 37
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 38
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 39
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 40
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 41
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 42
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 43
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 44
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 45
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 46
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 47
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 48
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 49
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 50
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 51
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 52
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 53
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 54
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 55
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 56
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 57
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 58
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 59
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 60
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 61
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 62
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 63
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 64
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 65
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 66
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 67
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 68
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 69
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 70
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 71
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 72
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 73
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 74
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 75
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 76
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 77
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 78
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 79
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 80
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 81
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 82
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 83
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 84
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 85
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 86
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 87
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 88
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 89
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 90
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 91
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 92
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 93
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 94
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 95
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 96
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 97
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 98
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 99
Now is : Thu Mar 17 17:03:25 CST 2016 ; the counter is : 100
 */