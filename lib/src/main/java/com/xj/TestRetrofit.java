package com.xj;

import com.xj.service.ApiService;
import com.xj.tool.LogTool;

import retrofit2.Retrofit;

/**
 * Created by xj
 * on 2016/3/12.
 */
public class TestRetrofit {

    public static void main(String args[]) {
        testRetrofit();
    }

    public static void testRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.10.11.250:8080")
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        String response = apiService.login("13713709078", "111111");
        LogTool.log(response);
    }


}
