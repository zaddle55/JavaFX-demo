package com.zaddle.socket;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketClient extends Socket {
    private static final String END_FLAG = "eof";
    private final int SERVER_PORT;
    private final String SERVER_IP;
    private Socket client;

    public SocketClient(String ip, int port) throws IOException {
        super(ip, port);
        SERVER_IP = ip;
        SERVER_PORT = port;
    }
    // 客户端启动
    public void connect() {
        try {
            client = new Socket(SERVER_IP, SERVER_PORT);
            new Thread(new Task(client)).start();
        } catch (IOException e) {
            handleError(e);
        }
    }
    // 客户端关闭
    public void disconnect() {
        try {
            client.close();
        } catch (IOException e) {
            handleError(e);
        }
    }
    // 任务线程
    class Task implements Runnable {
        private final Socket socket;
        private final BufferedReader reader;
        private final OutputStreamWriter writer;
        public Task(Socket socket) throws IOException {
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new OutputStreamWriter(socket.getOutputStream());
        }
        @Override
        public void run() {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals(END_FLAG)) {
                        break;
                    }
                    handleMessage(line);
                }
            } catch (IOException e) {
                handleError(e);
            } finally {
                try {
                    writer.close();
                    socket.close();
                } catch (IOException e) {
                    handleError(e);

                }
            }
        }
        public void sendLine(String line) {
            try {
                writer.write(line + "\n");
                writer.flush();
            } catch (IOException e) {
                handleError(e);
            }
        }
    }

    public void handleMessage(String line) {
        System.out.println("Received: " + line);
    }

    public void handleError(IOException e) {
        e.printStackTrace();
    }

}
