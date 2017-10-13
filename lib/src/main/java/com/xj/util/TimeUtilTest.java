package com.xj.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XingjieZheng
 * on 2016/11/15.
 */

public class TimeUtilTest {

    public static void main(String args[]) {
//        System.out.println(TimeUtil.getDateTimeString(System.currentTimeMillis(), "yyyy年MM月dd日"));


//        Entity entity1 = new Entity("1name");
//        Entity entity2 = new Entity("2name");
//        Map<String, Entity> map = new HashMap<>();
//        map.put("1", entity1);
//        map.put("2", entity2);
//        map.put("1", entity2);
//        map.remove("2");
//        System.out.println(map.get("1").name + " " + map.get("2"));


//        GZMemberType type = null;
//        System.out.println("" + (GZMemberType.ChatRoomCreator != type));


//        Map<String, Integer> items = new HashMap<>();
//        items.put("11", 10);
//        System.out.println(items.get("11") + " " + items.get("1"));
//        if (items.get("1") == null) {
//            System.out.println("item 1 is null");
//        } else {
//            System.out.println("item 1 is not null");
//        }
//        items.put("11", 101);
//        System.out.println(items.get("11") + " " + items.get("1"));

        Son son = new Son();
        System.out.println((son instanceof Father) + " " + (son instanceof Son));


    }

    static class Entity {
        public Entity(String name) {
            this.name = name;
        }

        public String name;
    }

    static enum GZMemberType {
        GroupZoneOwner,
        ChatRoomCreator,
        Normal

    }

    static class Son extends Father {
    }

    static class Father {
    }

}
