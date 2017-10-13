package com.xj.https;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * Created by XingjieZheng
 * on 2017/6/28.
 */

public class HttpsTest {

    public static void main(String[] args) {

        CertificateFactory cf = null;
        FileInputStream in = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            in = new FileInputStream("D:\\data\\MyCode\\ThirdLibsDemo\\lib\\src\\main\\java\\com\\xj\\zastatic.com.crt");
            Certificate c = cf.generateCertificate(in);

            String s = c.toString();
//            System.out.println(s);

//            //  提取CA证书的编码
//            byte[] encod1 = c.getEncoded();
//            //    用该编码创建X509CertImpl类型对象
//            X509CertImpl cimp1 = new X509CertImpl(encod1);
//            //获取X509CertInfo对象
//            X509CertInfo cinfo1 = (X509CertInfo) cimp1.get(X509CertImpl.NAME + "." + X509CertImpl.INFO);
//            try {
//                //获取X509Name类型的签发者信息
//                X500Name issuer = (X500Name) cinfo1.get(X509CertInfo.SUBJECT + "." + CertificateIssuerName.DN_NAME);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            X509Certificate t = null;
            if (c instanceof X509Certificate) {
                t = (X509Certificate) c;
            }
            if (t == null) {
                return;
            }
            Date TimeNow = new Date();
            //（c）检验有效性
            try {
                t.checkValidity(TimeNow);
                System.out.println("OK");
            } catch (CertificateExpiredException e) { //过期
                System.out.println("Expired");
                System.out.println(e.getMessage());
            } catch (CertificateNotYetValidException e) { //尚未生效
                System.out.println("Too early");
                System.out.println(e.getMessage());
            }


        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
