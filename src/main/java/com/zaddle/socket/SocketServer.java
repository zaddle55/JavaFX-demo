package com.zaddle.socket;

import javafx.concurrent.Task;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.ArrayList;

public class SocketServer extends ServerSocket {

    /*
    * 定义int变量port用于设置服务器监听端口
    * 定义String变量ip用于返回服务器IP地址
    * 定义String变量作为通信结束标志
    */
    private static final int SERVER_PORT = 8890;
    private static final String END_FLAG = "eof";
    private final String SERVER_IP;

    // 构造方法
    public SocketServer() throws IOException {
        super(SERVER_PORT);
        SERVER_IP = getIp();
    }

    private List<String> userNames = new ArrayList(); // 初始化客户端名称列表
    private static List<Task> threadList = new ArrayList<Task>(); // 服务器已启用线程集合
    private static BlockingQueue<String> msgQueue = new ArrayBlockingQueue<String>(
            20); // 存放消息的队列


    public void start() {
        try {
            while (true) {
                Socket socket = this.accept();
                Task task = new Task(socket);
                threadList.add(task);
                new Thread(task).start();
            }
        } catch (IOException e) {
            handleError(e);
        }

    }

    public void shutdown() {
        threadList.forEach(task -> {
            task.sendLine(END_FLAG);
        });
        ServerSocket server = this;
        try {
            server.close();
        } catch (IOException e) {
            handleError(e);
        }
    }

    class Task implements Runnable {
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;
        private String userName;

        public Task(Socket socket) {
            this.socket = socket;
            this.userName = "User" + threadList.size();
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            } catch (IOException e) {
                handleError(e);
            }
        }

        @Override
        public void run() {
            try {
                String line;
                sendLine(userName + ":Connected");
                while ((line = reader.readLine()) != null) {
                    if (line.equals(END_FLAG)) {
                        break;
                    }
                    msgQueue.put(line);
                }
            } catch (IOException | InterruptedException e) {
                handleError(e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    handleError(e);
                }
            }
        }

        public void sendLine(String line) {
            writer.println(line);
            writer.flush();
        }
    }

    public void handleError(Exception e) {
        e.printStackTrace();
    }

    public static String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parseMessage(String msg, List<String> userNames) {
        String[] msgArr = msg.split(":");
        if (msgArr[1].equals("Connected")) {
            userNames.add(msgArr[0]);
        } else {
            System.out.println(msg);
        }
    }
}
