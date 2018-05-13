package com.gmail.mosoft521.nettyDG.nio;

public class TimeClient {

    public static void main(String[] args) {

        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001")
                .start();
    }
}
/*
Send order 2 server succeed.
Now is : Fri Mar 04 20:23:05 CST 2016
 */