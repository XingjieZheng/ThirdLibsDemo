package com.xj.reflect;

import com.xj.tool.LogTool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by XingjieZheng
 * on 2017/3/3.
 */

public class ReflectTest {

    @Id(100)
    private int id;

    public void logId() {
        LogTool.log("id:" + id);
    }

    private void logMessage(String message) {
        LogTool.log("message:" + message);
    }

    public static void main(String[] args) {
        Class mClass = ReflectTest.class;
        Class<ReflectTest> clazz = ReflectTest.class;
//        Package mPackage = mClass.getPackage();
//        if (mPackage != null) {
//            LogTool.log(mPackage.getName());
//        }
//
//        Method[] methods = mClass.getMethods();
//        if (methods != null) {
//            for (Method method : methods) {
//                LogTool.log(method.getName());
//            }
//        }
//
//        Field[] fields = mClass.getFields();
//        if (fields != null) {
//            for (Field field : fields) {
//                LogTool.log(field.getName());
//            }
//        }

        ReflectTest reflectTest = null;
        try {
            reflectTest = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (reflectTest != null) {
            reflectTest.logId();
        }

        try {
            Method method = clazz.getDeclaredMethod("logMessage", String.class);
            if (method != null && reflectTest != null) {
                method.setAccessible(true);
                method.invoke(reflectTest, "reflect invoke logMessage()");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
