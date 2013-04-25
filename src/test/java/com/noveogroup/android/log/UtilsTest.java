package com.noveogroup.android.log;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void shortenClassNameTest() {
        Assert.assertEquals(null, Utils.shortenClassName(null, 10));

        String className = "com.noveogroup.SomeClass";
        Assert.assertEquals("*.SomeClass", Utils.shortenClassName(className, -1));
        Assert.assertEquals("*.SomeClass", Utils.shortenClassName(className, 0));
        Assert.assertEquals("*.SomeClass", Utils.shortenClassName(className, 11));
        Assert.assertEquals("*.SomeClass", Utils.shortenClassName(className, 21));
        Assert.assertEquals("*.noveogroup.SomeClass", Utils.shortenClassName(className, 22));
        Assert.assertEquals("*.noveogroup.SomeClass", Utils.shortenClassName(className, 23));
        Assert.assertEquals("com.noveogroup.SomeClass", Utils.shortenClassName(className, 24));
        Assert.assertEquals("com.noveogroup.SomeClass", Utils.shortenClassName(className, Integer.MAX_VALUE));

        String loggerName = "com...Logger";
        Assert.assertEquals("*.Logger", Utils.shortenClassName(loggerName, -1));
        Assert.assertEquals("*.Logger", Utils.shortenClassName(loggerName, 0));
        Assert.assertEquals("*..Logger", Utils.shortenClassName(loggerName, 9));
        Assert.assertEquals("*...Logger", Utils.shortenClassName(loggerName, 10));
        Assert.assertEquals("com...Logger", Utils.shortenClassName(loggerName, 12));
        Assert.assertEquals("com...Logger", Utils.shortenClassName(loggerName, Integer.MAX_VALUE));
    }

}
