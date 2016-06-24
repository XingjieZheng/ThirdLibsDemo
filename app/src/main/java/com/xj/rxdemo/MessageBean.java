package com.xj.rxdemo;

/**
 * Created by XingjieZheng
 * on 2016/6/17.
 */
public class MessageBean {

    public static final int TYPE_NORMAL_MESSAGE = 0;
    public static final int TYPE_REGISTER_MESSAGE = 1;

    public int getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(int receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public int getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(int senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    private int senderUserId;
    private int receiverUserId;
    private String message;
}
