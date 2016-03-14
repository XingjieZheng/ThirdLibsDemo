package com.xj;

import com.xj.tool.LogTool;

import java.io.IOException;

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
        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.networkInterceptors().add(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request().newBuilder()
//                        .addHeader("Cache-Control", "no-cache")
//                        .build();
//                return chain.proceed(request);
//            }
//        });

        RequestBody requestBody = new FormBody.Builder()
                .add("mobile", "13713709078")
                .add("password", "844733477")
                .build();
        Request request = new Request.Builder()
                .url("http://coffeeapi.yuanlai.com/account/login.do")
                .addHeader("Cache-Control", "no-cache")
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            LogTool.log(response.body().string());
            response.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
}
