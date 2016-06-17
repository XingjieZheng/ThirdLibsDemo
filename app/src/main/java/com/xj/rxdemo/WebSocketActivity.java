package com.xj.rxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

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

    @Bind(R.id.txtGetMsg)
    TextView txtGetMsg;
    @Bind(R.id.edtSendMsg)
    EditText edtSendMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnConnect)
    void clickConnectBtn() {
        try {
            createWebSocketClient();
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnSend)
    void clickSendBtn() {
        if (webSocketClient == null) {
            return;
        }
        String msg = "Empty";
        msg = edtSendMsg.getText().toString();
        webSocketClient.send(msg);
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
            }

            @Override
            public void onError(Exception e) {
                Log.i("webSocketClient", "onError" + e.toString());
            }
        };
    }
}
