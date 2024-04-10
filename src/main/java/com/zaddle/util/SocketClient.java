package com.zaddle.util;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class SocketClient extends SocketBase {
    /**
     * Socket client class
     * @param address
     * @param port
     */
    String address;
    int port;

    public SocketClient(String address, int port) {
        this.address = address;
        this.port = port;
    }
    /*
     * 连接服务器
     */
    public void connect() {
        var exec = Executors.newSingleThreadExecutor();
        exec.submit(() -> {
            try {
                handleConnect(new Socket(address, port));
            } catch (IOException e) {
                handleError(e);
            }
            exec.shutdown();
        });
    }
    /*
     * 断开连接
     */
    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (IOException e) {
                handleError(e);
            }
        }
    }
}