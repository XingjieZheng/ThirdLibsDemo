package com.xj;

import com.xj.tool.LogTool;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xj
 * on 2016/3/14.
 */
public class OkHttpTest {


    public static void main(String args[]) {
        runOkHttp();
    }

    private static void runOkHttp() {
        File file = new File("/cache/");
        int cacheSize = 2 << 19;
//        LogTool.log(file.getAbsolutePath() + " " + cacheSize);
        Cache cache = new Cache(file, cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache).build();

//        RequestBody requestBody = new FormBody.Builder()
//                .add("mobile", "13713709078")
//                .add("password", "844733477")
//                .build();
        Request request = new Request.Builder()
//                .url("http://coffeeapi.yuanlai.com/account/login.do")
                .url("http://publicobject.com/helloworld.txt")
//                .addHeader("Cache-Control", "max-age=30")
//                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
//            LogTool.log("header===================================");
//            LogTool.log(response.headers().toString());
//            LogTool.log("header===================================");
//            LogTool.log(response.body().string());
            LogTool.log("networkResponse " + response.networkResponse());
            LogTool.log("cacheResponse " + response.cacheResponse());
            response.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            Response response2 = okHttpClient.newCall(request).execute();
////            LogTool.log("header===================================");
////            LogTool.log(response2.headers().toString());
////            LogTool.log("header===================================");
////            LogTool.log(response2.body().string());
//            LogTool.log("networkResponse " + response2.networkResponse());
//            LogTool.log("cacheResponse " + response2.cacheResponse());
//            response2.body().close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
