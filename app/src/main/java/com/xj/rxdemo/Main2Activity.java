package com.xj.rxdemo;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        testOkHttp();
    }


    private void testOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Response response = chain.proceed(chain.request());
                                Log.i("response", "" + response.body().toString()
                                        + " \ncode:" + response.code());
                                return response;
                            }
                        })
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .build();
                Request request = new Request.Builder()
                        .url("https://api.zhenai.com")
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    Log.i("https", response.body().string());
                    response.body().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                try {
//                    Response response = okHttpClient.newCall(request).execute();
//                    Log.i("https", "header===================================");
//                    Log.i("https", response.headers().toString());
//                    Log.i("https", "header===================================");
//                    Log.i("https", response.body().string());
//                    response.body().close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }).start();

    }
}
