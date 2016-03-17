package com.xj;

import com.xj.tool.LogTool;

import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by XingjieZheng
 * on 2016/3/17.
 */
public class OkioSample {

    public static void main(String args[]) {

        writeFile();
        readFile();

    }

    public static boolean readFile() {
        try {
            File file = new File("F:\\write.txt");
            BufferedSource source = Okio.buffer(Okio.source(file));
            LogTool.log(source.readUtf8());
            source.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeFile() {
        try {
            File file = new File("F:\\write.txt");
            BufferedSink sink = Okio.buffer(Okio.sink(file));
            sink.writeUtf8("Hello, beauty!");
            sink.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
