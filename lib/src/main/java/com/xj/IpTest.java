package com.xj;

/**
 * Created by XingjieZheng
 * on 2017/10/12.
 */

public class IpTest {

    public static void main(String[] args) {
        byte[] b = new byte[]{10, 11, 111, -10};
        int i = byteArrayToInt(b);
        System.out.println(i);
        byte[] newB = intToByteArray(i);
        for (byte b1 : newB) {
            System.out.println(b1);
        }
        System.out.println("---------------------------\n");
        String ips = "113.107.216.119";
        String[] ipArray = ips.split(";");
        for (String ip : ipArray) {
            System.out.println(ip);
        }
    }

    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

}
