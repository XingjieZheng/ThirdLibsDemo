package com.xj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XingjieZheng
 * on 2016/11/9.
 */

public class TestList {

    public static void main(String args[]) {

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("2");
        list.add("5");
        list.add("2");
        List<String> list2 = new ArrayList<>();
        list2.add("2");
        list2.add("4");

        int size = list.size();
        for (String temp : list2) {
            for (int i = 0; i < size; i++) {
                System.out.println("before " + list.get(i));
                if (list.get(i).equals(temp)) {
                    list.remove(i);
                    i--;
                    size--;
                }
            }
        }
        for (String temp : list) {
            System.out.println("after " + temp);
        }


    }
}
