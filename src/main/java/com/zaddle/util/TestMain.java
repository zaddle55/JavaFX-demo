package com.zaddle.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
public class TestMain {
    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        // var t = AudioPlayer.playBgm("./majSoul.wav");
        Thread.sleep(2000);
        // t.stop();

//        EventCenter.subscribe("Test", e -> {
//            if (e instanceof ExampleEvent event) {
//                System.out.println(event.a);
//            }
//        });
//
//        EventCenter.publish("Test", new ExampleEvent(1));

        SocketServer server = new SocketServer(1234);
        server.addConnectListener(socket -> {
            System.out.println("Connected");
            server.sendLine("Hello, this is server");
        });
        server.addMessageListener(line -> {
            System.out.println("Received: " + line);
        });
        server.addErrorListener(e -> e.printStackTrace());
        server.start();

        SocketClient client = new SocketClient("10.32.78.223", 1234);
        client.addConnectListener(socket -> {
            System.out.println("Connected");
            client.sendLine("Hello, this is client 1");
        });
        client.addMessageListener(line -> {
            System.out.println("Received: " + line);
        });
        client.addErrorListener(e -> e.printStackTrace());
//        client.connect();
//        client.disconnect();
    }
}
