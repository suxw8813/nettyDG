package com.gmail.mosoft521.nettyDG.codec.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
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
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());//这行必须有，否则报错
                            ch.pipeline().addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            ch.pipeline().addLast(new ProtobufEncoder());
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
D:\tools\Java\jdk1.8.0_74\bin\java -Didea.launcher.port=7536 "-Didea.launcher.bin.path=D:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.1\bin" -Dfile.encoding=UTF-8 -classpath "D:\tools\Java\jdk1.8.0_74\jre\lib\charsets.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\deploy.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\ext\access-bridge-64.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\ext\cldrdata.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\ext\dnsns.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\ext\jaccess.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\ext\jfxrt.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\ext\localedata.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\ext\nashorn.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\ext\sunec.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\ext\sunjce_provider.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\ext\sunmscapi.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\ext\sunpkcs11.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\ext\zipfs.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\javaws.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\jce.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\jfr.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\jfxswt.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\jsse.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\management-agent.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\plugin.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\resources.jar;D:\tools\Java\jdk1.8.0_74\jre\lib\rt.jar;F:\ws_ij_alvin\nettyDG-alvin\target\classes;E:\java\repository\joda-time\joda-time\2.9.2\joda-time-2.9.2.jar;E:\java\repository\com\google\protobuf\protobuf-java\2.6.1\protobuf-java-2.6.1.jar;E:\java\repository\org\jboss\marshalling\jboss-marshalling\1.4.10.Final\jboss-marshalling-1.4.10.Final.jar;E:\java\repository\org\jboss\marshalling\jboss-marshalling-serial\1.4.10.Final\jboss-marshalling-serial-1.4.10.Final.jar;E:\java\repository\org\jibx\jibx-bind\1.2.6\jibx-bind-1.2.6.jar;E:\java\repository\org\jibx\jibx-run\1.2.6\jibx-run-1.2.6.jar;E:\java\repository\bcel\bcel\5.1\bcel-5.1.jar;E:\java\repository\regexp\regexp\1.2\regexp-1.2.jar;E:\java\repository\log4j\log4j\1.2.17\log4j-1.2.17.jar;E:\java\repository\com\thoughtworks\qdox\qdox\1.12.1\qdox-1.12.1.jar;E:\java\repository\org\jibx\jibx-extras\1.2.6\jibx-extras-1.2.6.jar;E:\java\repository\dom4j\dom4j\1.6.1\dom4j-1.6.1.jar;E:\java\repository\xml-apis\xml-apis\1.0.b2\xml-apis-1.0.b2.jar;E:\java\repository\org\jdom\jdom\1.1.3\jdom-1.1.3.jar;E:\java\repository\xpp3\xpp3\1.1.3.4.O\xpp3-1.1.3.4.O.jar;E:\java\repository\org\jibx\jibx-schema\1.2.6\jibx-schema-1.2.6.jar;E:\java\repository\org\jibx\jibx-tools\1.2.6\jibx-tools-1.2.6.jar;E:\java\repository\org\jibx\config\3rdparty\org\eclipse\org.eclipse.core.contenttype\3.4.100.v20110423-0524\org.eclipse.core.contenttype-3.4.100.v20110423-0524.jar;E:\java\repository\org\jibx\config\3rdparty\org\eclipse\org.eclipse.core.jobs\3.5.100.v20110404\org.eclipse.core.jobs-3.5.100.v20110404.jar;E:\java\repository\org\jibx\config\3rdparty\org\eclipse\org.eclipse.core.resources\3.7.100.v20110510-0712\org.eclipse.core.resources-3.7.100.v20110510-0712.jar;E:\java\repository\org\jibx\config\3rdparty\org\eclipse\org.eclipse.core.runtime\3.7.0.v20110110\org.eclipse.core.runtime-3.7.0.v20110110.jar;E:\java\repository\org\jibx\config\3rdparty\org\eclipse\org.eclipse.equinox.common\3.6.0.v20110523\org.eclipse.equinox.common-3.6.0.v20110523.jar;E:\java\repository\org\jibx\config\3rdparty\org\eclipse\org.eclipse.equinox.preferences\3.4.0.v20110502\org.eclipse.equinox.preferences-3.4.0.v20110502.jar;E:\java\repository\org\jibx\config\3rdparty\org\eclipse\org.eclipse.jdt.core\3.7.0.v_B61\org.eclipse.jdt.core-3.7.0.v_B61.jar;E:\java\repository\org\jibx\config\3rdparty\org\eclipse\org.eclipse.osgi\3.7.0.v20110613\org.eclipse.osgi-3.7.0.v20110613.jar;E:\java\repository\org\jibx\config\3rdparty\org\eclipse\org.eclipse.text\3.5.100.v20110505-0800\org.eclipse.text-3.5.100.v20110505-0800.jar;E:\java\repository\io\netty\netty-all\5.0.0.Alpha1\netty-all-5.0.0.Alpha1.jar;D:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.1\lib\idea_rt.jar" com.intellij.rt.execution.application.AppMain com.gmail.mosoft521.nettyDG.codec.protobuf.SubReqServer
Service accept client subscribe req : [subReqID: 0
userName: "Lilinfeng"
productName: "Netty Book For Protobuf"
address: "NanJing YuHuaTai"
address: "BeiJing LiuLiChang"
address: "ShenZhen HongShuLin"
]
Service accept client subscribe req : [subReqID: 1
userName: "Lilinfeng"
productName: "Netty Book For Protobuf"
address: "NanJing YuHuaTai"
address: "BeiJing LiuLiChang"
address: "ShenZhen HongShuLin"
]
Service accept client subscribe req : [subReqID: 2
userName: "Lilinfeng"
productName: "Netty Book For Protobuf"
address: "NanJing YuHuaTai"
address: "BeiJing LiuLiChang"
address: "ShenZhen HongShuLin"
]
Service accept client subscribe req : [subReqID: 3
userName: "Lilinfeng"
productName: "Netty Book For Protobuf"
address: "NanJing YuHuaTai"
address: "BeiJing LiuLiChang"
address: "ShenZhen HongShuLin"
]
Service accept client subscribe req : [subReqID: 4
userName: "Lilinfeng"
productName: "Netty Book For Protobuf"
address: "NanJing YuHuaTai"
address: "BeiJing LiuLiChang"
address: "ShenZhen HongShuLin"
]
Service accept client subscribe req : [subReqID: 5
userName: "Lilinfeng"
productName: "Netty Book For Protobuf"
address: "NanJing YuHuaTai"
address: "BeiJing LiuLiChang"
address: "ShenZhen HongShuLin"
]
Service accept client subscribe req : [subReqID: 6
userName: "Lilinfeng"
productName: "Netty Book For Protobuf"
address: "NanJing YuHuaTai"
address: "BeiJing LiuLiChang"
address: "ShenZhen HongShuLin"
]
Service accept client subscribe req : [subReqID: 7
userName: "Lilinfeng"
productName: "Netty Book For Protobuf"
address: "NanJing YuHuaTai"
address: "BeiJing LiuLiChang"
address: "ShenZhen HongShuLin"
]
Service accept client subscribe req : [subReqID: 8
userName: "Lilinfeng"
productName: "Netty Book For Protobuf"
address: "NanJing YuHuaTai"
address: "BeiJing LiuLiChang"
address: "ShenZhen HongShuLin"
]
Service accept client subscribe req : [subReqID: 9
userName: "Lilinfeng"
productName: "Netty Book For Protobuf"
address: "NanJing YuHuaTai"
address: "BeiJing LiuLiChang"
address: "ShenZhen HongShuLin"
]
 */