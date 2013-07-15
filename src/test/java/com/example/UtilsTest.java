package com.example;

import com.noveogroup.android.log.Utils;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void getCallerTest() {
        Assert.assertEquals(
                "com.example.UtilsTest.getCallerTest(UtilsTest.java:11)",
                Utils.getCaller().toString());
    }

    @Test
    public void getCallerClassNameTest() {
        Assert.assertEquals(
                "com.example.UtilsTest",
                Utils.getCallerClassName());
    }

}
