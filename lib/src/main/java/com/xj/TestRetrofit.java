package com.xj;

import com.xj.service.ApiService;
import com.xj.tool.LogTool;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xj
 * on 2016/3/12.
 */
public class TestRetrofit {

    public static void main(String args[]) {
        testRetrofit();
    }

    public static void testRetrofit() {

//        String cookie = "platform=0;" +
//                "model=MI 4LTE;" +
//                "channelId=6b7kxj4n1f;versionName=1.3.0;" +
//                "resolution=1080x1920;" +
//                "realDeviceId=866963025298896;" +
//                "deviceId=4f4a17a9b7b4e6ee;" +
//                "version=20160217;";
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request request = chain.request().newBuilder()
//                        .addHeader("Accept-Encoding", "gzip, deflate")
//                        .addHeader("Accept-Charset", "UTF-8,*;q=0.5")
//                        .addHeader("Cache-Control", "no-cache")
//                        .addHeader("Cookie", cookie)
//                        .build();
//                return chain.proceed(request);
//            }
//        };
//        List<Interceptor> list = okHttpClient.interceptors();
//        list.add(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://coffeeapi.yuanlai.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<AccountLoginBean> call = apiService.login("13713709078", "844733477");
        try {
            Response<AccountLoginBean> response = call.execute();
            AccountLoginBean bean = response.body();
            LogTool.log(bean.getData().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
