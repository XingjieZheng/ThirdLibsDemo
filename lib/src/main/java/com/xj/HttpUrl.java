package com.xj;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by XingjieZheng
 * on 2017/7/14.
 */

public class HttpUrl {

    public static void main(String[] args) {
        exe();
    }

    public static void exe() {
        URL url = null;
        try {
            url = new URL("https://mobileapi.zhenai.com/login/login.do");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection urlConnection = null;
        try {
            urlConnection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream in = null;
        try {
            in = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(String.valueOf(readStream(in)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }
}
