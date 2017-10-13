package com.xj;

/**
 * Created by XingjieZheng
 * on 2016/12/14.
 */

public class TestOrder {

    static {
        print("static method");
    }

    public TestOrder() {
        print("TestOrder");
    }


    public static void main(String args[]) {
//        new TestOrder();

//        String showContent = null;
//        for (int i = 0; i < 5; i++) {
//            if (i == 3) {
//                continue;
//            }
//            showContent = i + "";
//            print(i + "");
//        }
//
//        if (showContent != null) {
//            print(showContent);
//        }

        String idCardNumber = "44111224x";
        if (idCardNumber.charAt(idCardNumber.length() - 1) == 'x') {
//            idCardNumber.replace("x", "X");
            idCardNumber = idCardNumber.toUpperCase();
        }
        System.out.println(idCardNumber);

    }


    private static void print(String text) {
        System.out.println(text);
    }
}
