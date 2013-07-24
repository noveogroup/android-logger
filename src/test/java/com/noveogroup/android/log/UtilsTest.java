package com.noveogroup.android.log;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void shortenClassNameTest() {
        Assert.assertEquals(null, Utils.shortenClassName(null, 0, 10));

        String className = "com.example.android.MainActivity";

        Assert.assertEquals("com.example.android.MainActivity",
                Utils.shortenClassName(className, 0, 0));
        Assert.assertEquals("MainActivity",
                Utils.shortenClassName(className, -3, 0));
        Assert.assertEquals("com.example.android",
                Utils.shortenClassName(className, 3, 0));
        Assert.assertEquals("example.android.MainActivity",
                Utils.shortenClassName(className, -1, 0));
        Assert.assertEquals("com.example.android.MainActivity",
                Utils.shortenClassName(className, 7, 0));
        Assert.assertEquals("MainActivity",
                Utils.shortenClassName(className, -7, 0));
        Assert.assertEquals("com.example.android.MainActivity",
                Utils.shortenClassName(className, Integer.MAX_VALUE, 0));
//        todo uncomment and fix this test
//        Assert.assertEquals("MainActivity",
//                Utils.shortenClassName(className, Integer.MIN_VALUE, 0));

        Assert.assertEquals("com.*",
                Utils.shortenClassName(className, 0, 1));
        Assert.assertEquals("*.MainActivity",
                Utils.shortenClassName(className, 0, -1));
        Assert.assertEquals("com.example.android.*",
                Utils.shortenClassName(className, 0, 32));
        Assert.assertEquals("com.example.android.MainActivity",
                Utils.shortenClassName(className, 0, 33));
        Assert.assertEquals("*.android.MainActivity",
                Utils.shortenClassName(className, 0, -25));
        Assert.assertEquals("com.example.*",
                Utils.shortenClassName(className, 0, 15));
        Assert.assertEquals("com.example.android.MainActivity",
                Utils.shortenClassName(className, 0, 40));
        Assert.assertEquals("com.example.android.MainActivity",
                Utils.shortenClassName(className, 0, -40));
        Assert.assertEquals("com.example.android.MainActivity",
                Utils.shortenClassName(className, 0, Integer.MAX_VALUE));
//        todo uncomment and fix this test
//        Assert.assertEquals("*.MainActivity",
//                Utils.shortenClassName(className, 0, Integer.MIN_VALUE));

        Assert.assertEquals("*.example.android",
                Utils.shortenClassName(className, 3, -18));
        Assert.assertEquals("MainActivity$SubClass",
                Utils.shortenClassName("com.example.android.MainActivity$SubClass", -3, -10));
        Assert.assertEquals("com.example.android.MainActivity$SubClass",
                Utils.shortenClassName("com.example.android.MainActivity$SubClass", Integer.MAX_VALUE, Integer.MAX_VALUE));

        String loggerName = "com...Logger";
        Assert.assertEquals("*.Logger", Utils.shortenClassName(loggerName, 0, -1));
        Assert.assertEquals("com...Logger", Utils.shortenClassName(loggerName, 0, 0));
        Assert.assertEquals("*..Logger", Utils.shortenClassName(loggerName, 0, -9));
        Assert.assertEquals("*...Logger", Utils.shortenClassName(loggerName, 0, -10));
        Assert.assertEquals("com...Logger", Utils.shortenClassName(loggerName, 0, -12));
        Assert.assertEquals("com...Logger", Utils.shortenClassName(loggerName, 0, Integer.MAX_VALUE));
    }

    @Test
    public void shortenTest() {

        Assert.assertEquals(null, Utils.shorten(null, 0, 0));
        Assert.assertEquals("          ", Utils.shorten("", 10, 10));

        Assert.assertEquals("  text",
                Utils.shorten("text", 6, 0));
        Assert.assertEquals("text  ",
                Utils.shorten("text", -6, 0));
        Assert.assertEquals("tex",
                Utils.shorten("text", 0, 3));
        Assert.assertEquals("ext",
                Utils.shorten("text", 0, -3));

        Assert.assertEquals("   nov",
                Utils.shorten("noveogroup", 6, 3));
        Assert.assertEquals("   oup",
                Utils.shorten("noveogroup", 6, -3));
        Assert.assertEquals("noveogroup",
                Utils.shorten("noveogroup", 6, 0));
        Assert.assertEquals("noveogroup",
                Utils.shorten("noveogroup", 0, 0));
        Assert.assertEquals("noveogroup",
                Utils.shorten("noveogroup", 0, Integer.MAX_VALUE));
    }
}