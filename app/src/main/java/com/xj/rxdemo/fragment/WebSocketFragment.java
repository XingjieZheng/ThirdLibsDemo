package com.xj.rxdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xj.rxdemo.Constants;
import com.xj.rxdemo.MessageBean;
import com.xj.rxdemo.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebSocketFragment extends Fragment {

    private WebSocketClient webSocketClient;
    private MessageBean messageBean;
    private MessageBean messageBeanReceive;

    @Bind(R.id.txtGetMsg)
    TextView txtGetMsg;
    @Bind(R.id.edtSendMsg)
    EditText edtSendMsg;
    @Bind(R.id.edtSenderUserId)
    EditText edtSenderUserId;
    @Bind(R.id.edtReceiverUserId)
    EditText edtReceiverUserId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_socket, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }


    @OnClick(R.id.btnConnect)
    void clickConnectBtn() {
        if (TextUtils.isEmpty(edtSenderUserId.getText())) {
            return;
        }
        messageBean.setSenderUserId(Integer.valueOf(edtSenderUserId.getText().toString()));

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
        messageBean.setType(MessageBean.TYPE_NORMAL_MESSAGE);
        Gson gson = new Gson();
        webSocketClient.send(gson.toJson(messageBean));
    }


    private void createWebSocketClient() throws URISyntaxException {
        String address = "ws://10.10.152.67:8080/websocket";
        Log.i("createWebSocketClient", address);
        if (webSocketClient != null) {
            webSocketClient.close();
            webSocketClient = null;
        }
        webSocketClient = new WebSocketClient(new URI(address), new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("webSocketClient", "onOpen");
                sendUserInfo();
            }

            @Override
            public void onMessage(final String s) {
                Log.i("webSocketClient", "onMessage():" + s);
                Gson gson = new Gson();
                messageBeanReceive = gson.fromJson(s, MessageBean.class);
                if (messageBeanReceive != null && messageBeanReceive.getMessage() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtGetMsg.setText(messageBeanReceive.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("webSocketClient", "onClose():" + i + " " + s + " " + b);
            }

            @Override
            public void onError(Exception e) {
                Log.i("webSocketClient", "onError" + e.toString());
            }
        };
    }

    private void sendUserInfo() {
        Gson gson = new Gson();
        messageBean.setType(MessageBean.TYPE_REGISTER_MESSAGE);
        messageBean.setMessage("");
        messageBean.setSecurityKey(Constants.WEB_SOCKET_SECURITY_KEY);
        webSocketClient.send(gson.toJson(messageBean));
    }

    private void init() {
        messageBean = new MessageBean();
        messageBeanReceive = new MessageBean();
    }


    public void recycle() {
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }
}
