package com.xj;

import com.xj.tool.LogTool;

import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by xj
 * on 2016/3/17.
 */
public class OkioTest {

    public static void main(String args[]) throws IOException {
        okioWrite();
        okioRead();

    }

    public static void okioWrite() throws IOException {
        File file = new File("/cache/ioWrite.txt");
        BufferedSink sink = Okio.buffer(Okio.sink(file));
        sink.writeUtf8("hello, beauty!");
        sink.writeUtf8("hello");
        sink.close();
    }

    public static void okioRead() throws IOException {
        File file = new File("/cache/ioWrite.txt");
        BufferedSource source = Okio.buffer(Okio.source(file));
        LogTool.log(source.readUtf8());
        source.close();
    }


}
