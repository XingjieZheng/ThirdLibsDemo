package com.xj.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;

/**
 * Created by XingjieZheng
 * on 2016/6/17.
 */
public class ChatWebSocketServer extends WebSocketServer {

    public ChatWebSocketServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public ChatWebSocketServer(InetSocketAddress inetSocketAddress) {
        super(inetSocketAddress);
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println("onOpen(), remote address:" + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println("onClose(), remote address:" + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println("onMessage(), remote address:" + webSocket.getRemoteSocketAddress().getAddress().getHostAddress()
                + "\n" + s);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        System.out.println("onError(), remote address:" + webSocket.getRemoteSocketAddress().getAddress().getHostAddress()
                + "\n" + e.toString());
    }

    public void sendToAll(String msg) {
        Collection<WebSocket> collection = connections();
        synchronized (collection) {
            for (WebSocket webSocket : collection) {
                System.out.println(webSocket.getRemoteSocketAddress().getAddress());
                webSocket.send(msg);
            }
        }

    }


    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8887;
        ChatWebSocketServer webSocketServer = new ChatWebSocketServer(port);
        webSocketServer.start();
        String ip = InetAddress.getLocalHost().getHostAddress();
        System.out.println("server starts, ip is " + ip + " port is " + port);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String stringIn = bufferedReader.readLine();
            System.out.println("send to client:" + stringIn);
            webSocketServer.sendToAll(stringIn);
            if ("exit".equals(stringIn)) {
                webSocketServer.stop();
                break;
            } else if ("restart".equals(stringIn)) {
                webSocketServer.stop();
                webSocketServer.start();
                break;
            }
        }
    }
}
