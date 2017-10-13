package com.xj.rxdemo;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xj
 * on 2016/3/14.
 */
public class SSL {


    public static void main(String args[]) {

        runOkHttp("https://mobileapi.zhenai.com/login/login.do");
//        runOkHttp("https://api.zhenai.com/login/userLogin.do");
//        runOkHttp("https://www.baidu.com");
//        runOkHttp("https://github.com");
    }

    private static void runOkHttp(String url) {

//        File file = new File("/cache/");
//        int cacheSize = 2 << 19;
//        LogTool.log(file.getAbsolutePath() + " " + cacheSize);
//        Cache cache = new Cache(file, cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()/*.cache(cache)*/
//                .sslSocketFactory(getSslSocket().getSocketFactory())
                .build();


//        RequestBody requestBody = new FormBody.Builder()
//                .add("mobile", "13713709078")
//                .add("password", "844733477")
//                .build();
        Request request = new Request.Builder()
                .url(url)
//                .url("https://mobileapi.zhenai.com/login/login.do")
//                .url("https://kyfw.12306.cn/otn/regist/init")
//                .url("https://api.sayloveapp.com/login/login.do")
//                .addHeader("Cache-Control", "max-age=30")
//                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            Log.i("SSL", "header===================================");
            Log.i("SSL", response.headers().toString());
            Log.i("SSL", "header===================================");
            Log.i("SSL", response.body().string());
//            LogTool.log("networkResponse " + response.networkResponse());
//            LogTool.log("cacheResponse " + response.cacheResponse());
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

    private static final String CLIENT_TRUST_PASSWORD = "changeit";//信任证书密码，该证书默认密码是changeit
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_TRUST_MANAGER = "X509";
    private static final String CLIENT_TRUST_KEYSTORE = "BKS";

    public static String PUB_KEY = "";

    public static SSLContext getSslSocket() {
        SSLContext sslContext = null;
        try {
            TrustManager[] trustManagers = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            if (chain == null) {
                                return;
                            }
                            if (chain.length <= 0) {
                                throw new IllegalArgumentException("checkServerTrusted: X509Certificate is empty");
                            }
                            for (X509Certificate temp : chain) {
                                System.out.println("----------------start---------------------");
                                System.out.println(temp.toString());
                                System.out.println("------------------end-------------------");
                            }

                            if (!(null != authType && authType.equalsIgnoreCase("ECDHE_RSA"))) {
                                throw new CertificateException("checkServerTrusted: AuthType is not RSA");
                            }
                            Certificate certificate = getCertificate();
                            Date dateNow = new Date();
                            try {
                                chain[0].checkValidity(dateNow);
                                System.out.println("OK");
                            } catch (CertificateExpiredException e) { //过期
                                System.out.println("Expired");
                                System.out.println(e.getMessage());
                            } catch (CertificateNotYetValidException e) { //尚未生效
                                System.out.println("Too early");
                                System.out.println(e.getMessage());
                            }
                            if (certificate != null) {
                                try {
                                    chain[0].verify(certificate.getPublicKey());
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                } catch (InvalidKeyException e) {
                                    e.printStackTrace();
                                } catch (NoSuchProviderException e) {
                                    e.printStackTrace();
                                } catch (SignatureException e) {
                                    e.printStackTrace();
                                }
                            }

//                            if (!expected) {
//                                throw new CertificateException("checkServerTrusted: Expected public key: "
//                                        + PUB_KEY + ", got public key:" + encoded);
//                            }
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };
            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
            sslContext.init(null, trustManagers, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext;

    }

    private static Certificate getCertificate() {
        String text = "-----BEGIN CERTIFICATE-----\n" +
                "MIIGJjCCBQ6gAwIBAgIQdMoCV+hDkRaIZiddsvzFLzANBgkqhkiG9w0BAQsFADBE\n" +
                "MQswCQYDVQQGEwJVUzEWMBQGA1UEChMNR2VvVHJ1c3QgSW5jLjEdMBsGA1UEAxMU\n" +
                "R2VvVHJ1c3QgU1NMIENBIC0gRzMwHhcNMTYxMjIxMDAwMDAwWhcNMTcxMjIxMjM1\n" +
                "OTU5WjCBoDELMAkGA1UEBhMCQ04xEjAQBgNVBAgMCeW5v+S4nOecgTESMBAGA1UE\n" +
                "BwwJ5rex5Zyz5biCMTMwMQYDVQQKDCrmt7HlnLPluILnj43niLHnvZHkv6Hmga/m\n" +
                "ioDmnK/mnInpmZDlhazlj7gxGzAZBgNVBAsMEuS6kuiBlOe9keS6i+S4mumDqDEX\n" +
                "MBUGA1UEAwwOKi56YXN0YXRpYy5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAw\n" +
                "ggEKAoIBAQDzpX4RQQl5qagt4t34OWRUB8RMQx+Cq/rOCjrgwaziGo82E8nMOUYf\n" +
                "bkEPHfG5SYmDJhOVNrxNosUtIvSslj9lJXJ6/i4mPClMY3FF1mP6kGcx4qcC0HQI\n" +
                "WZKuyGGTs3Eu0q7aAOP5F8A0V5fV9jPkOyFRqNtn2X/a7YY7qPWtHDp/B14/bDZk\n" +
                "nvQslZciTMqF+nKxqkqJhpoyliMPBYRzvUMILR+hDalpUNqezGMdSj0ka+1d05+v\n" +
                "UVHtywAcorzCGIXPWt767dQU0OOcXhbQ7dLnJ6GfHs/P36JRQ3lNilB0x4X13T3R\n" +
                "AAKMBuyNSVP9ZEJMFcCoRQLYAlXD1VYfAgMBAAGjggK1MIICsTAnBgNVHREEIDAe\n" +
                "gg4qLnphc3RhdGljLmNvbYIMemFzdGF0aWMuY29tMAkGA1UdEwQCMAAwDgYDVR0P\n" +
                "AQH/BAQDAgWgMCsGA1UdHwQkMCIwIKAeoByGGmh0dHA6Ly9nbi5zeW1jYi5jb20v\n" +
                "Z24uY3JsMIGdBgNVHSAEgZUwgZIwgY8GBmeBDAECAjCBhDA/BggrBgEFBQcCARYz\n" +
                "aHR0cHM6Ly93d3cuZ2VvdHJ1c3QuY29tL3Jlc291cmNlcy9yZXBvc2l0b3J5L2xl\n" +
                "Z2FsMEEGCCsGAQUFBwICMDUMM2h0dHBzOi8vd3d3Lmdlb3RydXN0LmNvbS9yZXNv\n" +
                "dXJjZXMvcmVwb3NpdG9yeS9sZWdhbDAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYB\n" +
                "BQUHAwIwHwYDVR0jBBgwFoAU0m/3lvSFP3I8MH0j2oV4m6N8WnwwVwYIKwYBBQUH\n" +
                "AQEESzBJMB8GCCsGAQUFBzABhhNodHRwOi8vZ24uc3ltY2QuY29tMCYGCCsGAQUF\n" +
                "BzAChhpodHRwOi8vZ24uc3ltY2IuY29tL2duLmNydDCCAQMGCisGAQQB1nkCBAIE\n" +
                "gfQEgfEA7wB2AN3rHSt6DU+mIIuBrYFocH4ujp0B1VyIjT0RxM227L7MAAABWR/b\n" +
                "o98AAAQDAEcwRQIhALHGNDJTR0ooIltcUeQk1kLbMCH6QmQiI0rVmuLfUoVrAiBW\n" +
                "NSLjsPnbrLVJtJCoxyo1DRSyDTmKKFRZiKEOP42XEwB1AO5Lvbd1zmC64UJpH6vh\n" +
                "nmajD35fsHLYgwDEe4l6qP3LAAABWR/bpDQAAAQDAEYwRAIgQUYMkhzmmK8itX9A\n" +
                "imeZnD7igx/cUX2XQvdGuRGQBm0CICUKa7FKvX72USO3kxP/d8oZOlwlTWq3sYNB\n" +
                "PYric3I7MA0GCSqGSIb3DQEBCwUAA4IBAQDegYdxMKxFAV38VgmkvuKWwraJv/rQ\n" +
                "tazlV+nD8Z4cPoMqVqajRsu7mKtmdTlritWWabbHOHW+WuFZtCTkhA1bA+jXDZ2N\n" +
                "T/moYRZTxaiWHIldTQJkf+NkhkbX5whspoAOLzBZfU5yL3pE6Qfsr0CAFXeBPIiV\n" +
                "89HT8PUWevxhtuGdDDPqvGRh/Z+jselDN2j8GeWrzbpF1nKKyRnnnywLxa882rmz\n" +
                "ULvQgkQdCQokon9WjDG3kqL5hKIW/faF9n+w3ymrex6qdIRy9ZsXBi+mx1wzP6bS\n" +
                "xnoFefQ4YZ1flrSL/WTscYdhQUHjDmCTWB5UY9kFz5E9pTZxlGQOdmY+\n" +
                "-----END CERTIFICATE-----";

        InputStream inputStream = new ByteArrayInputStream(text.getBytes());
        Certificate certificate = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            certificate = cf.generateCertificate(inputStream);
        } catch (CertificateException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return certificate;
    }
}
