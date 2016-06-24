package com.xj.websocket;

import com.google.gson.Gson;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by XingjieZheng
 * on 2016/6/17.
 */
public class ChatWebSocketServer extends WebSocketServer {

    private static final String USER_ID = "userId";
    private static final String SENDER_USER_ID = "senderUserId";
    private static final String RECEIVER_USER_ID = "receiverUserId";
    private static final String MESSAGE = "message";
    private HashMap<Integer, String> addressMap;

    public ChatWebSocketServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        addressMap = new HashMap<>();
    }


    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println("onOpen(), remote address:" + webSocket.getRemoteSocketAddress().getAddress().getHostAddress()
                + "\t" + webSocket.getRemoteSocketAddress().getHostName()
                + "\t" + webSocket.getRemoteSocketAddress().getPort());
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println("onClose(), remote address:" + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
        String address = webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + ":"
                + webSocket.getRemoteSocketAddress().getPort();
        Iterator iterator = addressMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) iterator.next();
            if (address.equals(entry.getValue())) {
                addressMap.remove(entry.getKey());
            }
        }

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println("onMessage(), remote address:" + webSocket.getRemoteSocketAddress().getAddress().getHostAddress()
                + "\t" + s);

        if (s != null && s.contains(USER_ID)) {
            Gson gson = new Gson();
            UserInfo userInfo = gson.fromJson(s, UserInfo.class);
            if (userInfo != null && userInfo.getUserId() != 0) {
                String address = webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + ":"
                        + webSocket.getRemoteSocketAddress().getPort();
                userInfo.setAddress(address);
                if (addressMap.containsKey(userInfo.getUserId())) {
                    addressMap.replace(userInfo.getUserId(), userInfo.getAddress());
                } else {
                    addressMap.put(userInfo.getUserId(), userInfo.getAddress());
                }
            }
        } else if (s != null && s.contains(SENDER_USER_ID) && s.contains(RECEIVER_USER_ID) && s.contains(MESSAGE)) {
            Gson gson = new Gson();
            MessageBean messageBean = gson.fromJson(s, MessageBean.class);
            if (messageBean != null) {
                String address = addressMap.get(messageBean.getReceiverUserId());
                if (address != null) {
                    sendToSomeone(messageBean.getMessage(), address);
                } else {
                    System.out.println("error: receiver (userId:" + messageBean.getReceiverUserId() + ") does not connect to the server!");
                }
            }
        }

        //print message
        System.out.println("client list start---------------------------------");
        Iterator iterator = addressMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) iterator.next();
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println("client list end-----------------------------------");
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        System.out.println("onError(), remote address:" + webSocket.getRemoteSocketAddress().getAddress().getHostAddress()
                + "\t" + e.toString());
    }

    public void sendToAll(String msg) {
        if (msg == null) {
            return;
        }
        Collection<WebSocket> collection = connections();
        synchronized (collection) {
            for (WebSocket webSocket : collection) {
                System.out.println(webSocket.getRemoteSocketAddress().getAddress());
                webSocket.send(msg);
            }
        }
    }

    public void sendToSomeone(String msg, String address) {
        if (msg == null || address == null) {
            return;
        }
        String addressAll[] = address.split(":");
        if (addressAll.length != 2) {
            return;
        }
        String ip = addressAll[0];
        String port = addressAll[1];

        boolean isSendSuccessfully = false;
        Collection<WebSocket> collection = connections();
        synchronized (collection) {
            for (WebSocket webSocket : collection) {
                if (ip.equals(webSocket.getRemoteSocketAddress().getAddress().getHostAddress())
                        && port.equals(String.valueOf(webSocket.getRemoteSocketAddress().getPort()))) {
                    webSocket.send(msg);
                    isSendSuccessfully = true;
                    System.out.println("Send " + msg + " to " + address + " successfully.");
                }
            }
        }
        if (!isSendSuccessfully) {
            System.out.println("Send " + msg + " to " + address + "fail, cause by count not find ip: " + address);
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8080;
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
