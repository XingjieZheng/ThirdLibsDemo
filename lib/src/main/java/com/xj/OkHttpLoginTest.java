package com.xj;

import com.xj.tool.LogTool;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xj
 * on 2016/3/14.
 */
public class OkHttpLoginTest {


    public static void main(String args[]) {

        runOkHttp();
    }

    private static void runOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        RequestBody requestBody = new FormBody.Builder()
                .add("mobile", "13713709078")
                .add("password", "123456")
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8080/account/login.do")
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            LogTool.log("header===================================");
            LogTool.log(response.headers().toString());
            LogTool.log("header===================================\n");
            LogTool.log("body=====================================");
            LogTool.log("body " + response.body().string());
            LogTool.log("body=====================================\n");
            response.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
