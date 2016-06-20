package com.xj.rxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebSocketActivity extends AppCompatActivity {

    private WebSocketClient webSocketClient;
    private boolean isConnectOpen = false;
    private MessageBean messageBean;
    private UserInfo userInfo;

    @Bind(R.id.txtGetMsg)
    TextView txtGetMsg;
    @Bind(R.id.edtSendMsg)
    EditText edtSendMsg;
    @Bind(R.id.edtSenderUserId)
    EditText edtSenderUserId;
    @Bind(R.id.edtReceiverUserId)
    EditText edtReceiverUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);
        ButterKnife.bind(this);
        init();
    }

    @OnClick(R.id.btnConnect)
    void clickConnectBtn() {
        if (TextUtils.isEmpty(edtSenderUserId.getText())) {
            return;
        }
        messageBean.setSenderUserId(Integer.valueOf(edtSenderUserId.getText().toString()));
        userInfo.setUserId(Integer.valueOf(edtSenderUserId.getText().toString()));

        try {
            createWebSocketClient();
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnSend)
    void clickSendBtn() {
        if (webSocketClient == null || TextUtils.isEmpty(edtReceiverUserId.getText()) || TextUtils.isEmpty(edtSendMsg.getText())) {
            return;
        }
        messageBean.setReceiverUserId(Integer.valueOf(edtReceiverUserId.getText().toString()));
        messageBean.setMessage(edtSendMsg.getText().toString());
        Gson gson = new Gson();
        webSocketClient.send(gson.toJson(messageBean));
    }


    private void createWebSocketClient() throws URISyntaxException {
        String address = "ws://10.10.152.73:8887";
        Log.i("createWebSocketClient", address);
        if (webSocketClient != null) {
            webSocketClient = null;
        }
        webSocketClient = new WebSocketClient(new URI(address), new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("webSocketClient", "onOpen");
                isConnectOpen = true;
                sendUserInfo();
            }

            @Override
            public void onMessage(final String s) {
                Log.i("webSocketClient", "onMessage():" + s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtGetMsg.setText(s);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("webSocketClient", "onClose():" + i + " " + s + " " + b);
                isConnectOpen = false;
            }

            @Override
            public void onError(Exception e) {
                Log.i("webSocketClient", "onError" + e.toString());
            }
        };
    }

    private void sendUserInfo() {
        Gson gson = new Gson();
        webSocketClient.send(gson.toJson(userInfo));
    }

    private void init() {
        messageBean = new MessageBean();
        userInfo = new UserInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }
}
