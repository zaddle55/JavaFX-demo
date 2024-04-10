package com.zaddle.util;

import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class SocketServer extends SocketBase {

    private final int port; // 设置服务器监听端口

    public SocketServer(int port) {
        this.port = port;
    }
    /*
    * 服务器启动
    */
    public void start(){
        var exec = Executors.newSingleThreadExecutor();
        exec.submit(() -> {
            try (ServerSocket server = new ServerSocket(port)) {
                handleConnect(server.accept());
            } catch (IOException e) {
                handleError(e);
            }
            exec.shutdown();
        });
    }
    /*
    * 返回服务器IP地址
    */
    public static String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}