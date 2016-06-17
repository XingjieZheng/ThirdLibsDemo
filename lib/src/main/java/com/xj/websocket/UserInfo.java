package com.xj.websocket;

/**
 * Created by XingjieZheng
 * on 2016/6/17.
 */
public class UserInfo {

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private int userId;
    private String address;
}
