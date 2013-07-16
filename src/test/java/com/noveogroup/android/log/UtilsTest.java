package com.noveogroup.android.log;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void shortenClassNameTest() {
        Assert.assertEquals(null, Utils.shortenClassName(null, 0, 10));

        String className = "com.noveogroup.SomeClass";
        Assert.assertEquals("*.SomeClass", Utils.shortenClassName(className, 0, -1));
        Assert.assertEquals("com.noveogroup.SomeClass", Utils.shortenClassName(className, 0, 0));
        Assert.assertEquals("*.SomeClass", Utils.shortenClassName(className, 0, -11));
        Assert.assertEquals("*.SomeClass", Utils.shortenClassName(className, 0, -21));
        Assert.assertEquals("*.noveogroup.SomeClass", Utils.shortenClassName(className, 0, -22));
        Assert.assertEquals("*.noveogroup.SomeClass", Utils.shortenClassName(className, 0, -23));
        Assert.assertEquals("com.noveogroup.SomeClass", Utils.shortenClassName(className, 0, -24));
        Assert.assertEquals("com.noveogroup.SomeClass", Utils.shortenClassName(className, 0, Integer.MAX_VALUE));

        String loggerName = "com...Logger";
        Assert.assertEquals("*.Logger", Utils.shortenClassName(loggerName, 0, -1));
        Assert.assertEquals("com...Logger", Utils.shortenClassName(loggerName, 0, 0));
        Assert.assertEquals("*..Logger", Utils.shortenClassName(loggerName, 0, -9));
        Assert.assertEquals("*...Logger", Utils.shortenClassName(loggerName, 0, -10));
        Assert.assertEquals("com...Logger", Utils.shortenClassName(loggerName, 0, -12));
        Assert.assertEquals("com...Logger", Utils.shortenClassName(loggerName, 0, Integer.MAX_VALUE));
    }

}
