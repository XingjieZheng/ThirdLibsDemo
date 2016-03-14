package com.xj.service;

import com.xj.AccountLoginBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by xj on
 * 2016/3/12.
 */
public interface ApiService {

//    String cookie = "platform=0;" +
//            "model=MI 4LTE;" +
//            "channelId=6b7kxj4n1f;versionName=1.3.0;" +
//            "resolution=1080x1920;" +
//            "realDeviceId=866963025298896;" +
//            "deviceId=4f4a17a9b7b4e6ee;" +
//            "version=20160217;";
//
//    @Headers({
//            "Accept-Encoding:gzip, deflate",
//            "Accept-Charset:UTF-8,*;q=0.5",
//            "Cache-Control:no-cache",
//            "Cookie:" + cookie
//            })
//    @Multipart
    @FormUrlEncoded
    @POST("account/login.do")
    Call<AccountLoginBean> login(@Field("mobile") String account, @Field("password") String password);
}
