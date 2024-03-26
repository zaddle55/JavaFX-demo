package com.zaddle.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class SocketBase {
    protected Consumer<Socket> connectCallback = socket -> {}; // 连接回调
    protected Consumer<Socket> receiveCallback = socket -> {}; // 接收回调
    protected Consumer<Socket> errorCallback = e -> {}; // 错误回调
    Socket conn; // 连接对象
    /*
     * 连接服务器
     */
    public void addConnectListener(Consumer<Socket> listener) {
        connectCallback = listener;
    }
    /*
     * 接收消息
     */
    public void addMessageListener(Consumer<String> listener) {
        receiveCallback = listener;
    }
    /*
     * 发送消息
     */
    public void addErrorListener(Consumer<Socket> listener) {
        errorCallback = listener;
    }
    /*
     * 用于socket对象的连接与消息接收
     */
    protected void handleConnect(Socket socket) throws IOException {
        conn = socket;
        connectCallback.accept(conn);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String msg;
        while ((msg = in.readLine()) != null) {
            receiveCallback.accept(msg);
        }
        handleError(new IOException("Remote server closed the connection"));
    }
    protected void handleError(IOException e) {
        conn = null;
        errorCallback.accept(e);
    }
    /*
     * Socket对象之间输入输出流的发送
     */
    public boolean sendLine(String line) {
        if (conn == null) {
            errorCallback.accept(new IOException("Not connected"));
            return false;
        }
        try {
            PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
            out.println(line);
            return true;
        } catch (IOException e) {
            handleError(e);
            
        }
        return false;
    }
}