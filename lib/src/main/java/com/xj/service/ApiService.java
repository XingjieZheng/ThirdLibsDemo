package com.xj.service;

import retrofit2.http.GET;

/**
 * Created by xj on
 * 2016/3/12.
 */
public interface ApiService {

    @GET("/account/login.do")
    String login(String account, String password);
}
