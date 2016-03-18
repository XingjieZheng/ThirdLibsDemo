package tests;

import com.xj.OkioSample;

import org.junit.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by XingjieZheng
 * on 2016/3/18.
 */
public class OkioSampleTest {

    @Before
    public void setUp() throws Exception {

    }

    @org.junit.Test
    public void testReadFile() throws Exception {
        assertTrue(OkioSample.readFile());
    }

    @Test
    public void testWriteFile() throws Exception {
        assertTrue(OkioSample.writeFile());
    }
}