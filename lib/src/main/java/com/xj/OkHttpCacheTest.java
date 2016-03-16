package com.xj;

import java.io.File;
import java.io.IOException;
import java.net.CacheResponse;
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xj
 * on 2016/3/15.
 */
public class OkHttpCacheTest {

    private static final Logger logger = Logger.getLogger(OkHttpCacheTest.class.getName());
    private final OkHttpClient client;

    public OkHttpCacheTest(File cacheDirectory) throws Exception {
        logger.info(String.format("Cache file path %s", cacheDirectory.getAbsoluteFile()));
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(cacheDirectory, cacheSize);
        client = new OkHttpClient.Builder().cache(cache).build();
    }

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();
        Response response1 = client.newCall(request).execute();
        if (!response1.isSuccessful()) throw new IOException("Unexpected code " + response1);
        String response1Body = response1.body().string();
        System.out.println("Response 1 response: " + response1);
        System.out.println("Response 1 cache response: " + response1.cacheResponse());
        System.out.println("Response 1 network response: " + response1.networkResponse());
//        Response response2 = client.newCall(request).execute();
//        if (!response2.isSuccessful()) throw new IOException("Unexpected code " + response2);
//        String response2Body = response2.body().string();
//        System.out.println("Response 2 response: " + response2);
//        System.out.println("Response 2 cache response: " + response2.cacheResponse());
//        System.out.println("Response 2 network response: " + response2.networkResponse());
//        System.out.println("Response 2 equals Response 1? " + response1Body.equals(response2Body));
//
//
//        System.out.println("Response 1 response: " + response1.headers().toString() + " \n" + response1Body);
    }

    public static void main(String... args) throws Exception {
        new OkHttpCacheTest(new File("CacheResponse.tmp")).run();
    }
}
