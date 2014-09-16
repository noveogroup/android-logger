package com.example;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.Pattern;
import com.noveogroup.android.log.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PatternTest {

    StackTraceElement caller = Utils.getCaller();
    Logger.Level level = Logger.Level.DEBUG;
    String loggerName = "com.noveo.android";

    Pattern spacePattern = new Pattern.PlainPattern(0, 0, " ");
    Pattern colonPattern = new Pattern.PlainPattern(0, 0, ":");
    Pattern tabPattern = new Pattern.PlainPattern(0, 0, "\n");
    Pattern levelPattern = new Pattern.LevelPattern(1, 1);
    Pattern dataPattern = new Pattern.DatePattern(0, 0, "HH:mm:ss");
    Pattern loggerPattern = new Pattern.LoggerPattern(0, 0, 30, 0);
    Pattern callerPattern = new Pattern.CallerPattern(0, 0, 30, 0);
    Pattern sourcePattern = new Pattern.SourcePattern(0, 0);

    Pattern.ConcatenatePattern concatenatePattern = new Pattern.ConcatenatePattern(0, 0, new ArrayList<Pattern>());
    Pattern.ConcatenatePattern concatenatePatternChild = new Pattern.ConcatenatePattern(60, 0, new ArrayList<Pattern>());


    @Test
    public void patternTests() {
        Assert.assertEquals("D",
                levelPattern.apply(caller, loggerName, level));

        Assert.assertEquals(new SimpleDateFormat("HH:mm:ss").format(new Date()),
                dataPattern.apply(caller, loggerName, level));

        Assert.assertEquals(":",
                colonPattern.apply(caller, loggerName, level));

        Assert.assertEquals("\n",
                tabPattern.apply(caller, loggerName, level));

        Assert.assertEquals("com.example.PatternTest#<init>:15",
                callerPattern.apply(caller, loggerName, level));

        Assert.assertEquals("com.noveo.android",
                loggerPattern.apply(caller, loggerName, level));


        concatenatePattern.addPattern(dataPattern);
        concatenatePattern.addPattern(spacePattern);
        concatenatePattern.addPattern(levelPattern);
        concatenatePattern.addPattern(spacePattern);

        concatenatePatternChild.addPattern(loggerPattern);
        concatenatePatternChild.addPattern(spacePattern);
        concatenatePatternChild.addPattern(callerPattern);
        concatenatePatternChild.addPattern(sourcePattern);

        concatenatePattern.addPattern(concatenatePatternChild);
        concatenatePattern.addPattern(colonPattern);
        concatenatePattern.addPattern(tabPattern);

        Assert.assertEquals("HH:mm:ss D com.noveo.android com.example.PatternTest#<init>:15(PatternTest.java:15):\n".substring(8),
                concatenatePattern.apply(caller, loggerName, level).substring(8));
    }

    @Test
    public void compileTest() {

        Pattern.Compiler compiler = new Pattern.Compiler();

        Assert.assertEquals("%e", compiler.compile("%%e").apply(caller, loggerName, level));
        Assert.assertEquals("abc\nde", compiler.compile("abc%nde").apply(caller, loggerName, level));
        Assert.assertEquals("%de", compiler.compile("%%de").apply(caller, loggerName, level));
        Assert.assertEquals("%\nde", compiler.compile("%%%nde").apply(caller, loggerName, level));
        Assert.assertEquals("%%%", compiler.compile("%%%%%%").apply(caller, loggerName, level));

        Assert.assertEquals("D", compiler.compile("%1.1level").apply(caller, loggerName, level));
        Assert.assertEquals("abc%de\nfD", compiler.compile("abc%%de%nf%1.1level").apply(caller, loggerName, level));

        Assert.assertEquals("com.noveo.android", compiler.compile("%logger").apply(caller, loggerName, level));
        Assert.assertEquals("com.noveo.android&main", compiler.compile("%logger&main").apply(caller, loggerName, level));
        Assert.assertEquals("abc%de\nfc", compiler.compile("abc%%de%nf%1.1logger{1}").apply(caller, loggerName, level));

        Assert.assertEquals("%\nde%", compiler.compile("%%%nde%%").apply(caller, loggerName, level));

        compiler.compile("%d").apply(caller, loggerName, level);
        Assert.assertNull(compiler.compile(null));


        Assert.assertEquals("D", compiler.compile("%1.1p").apply(caller, loggerName, level));

        Assert.assertEquals("com.example%com.noveo%", compiler.compile("%caller{2}%%%c{2}%%").apply(caller, loggerName, level));

        Assert.assertEquals("PatternTest#<init>:15", compiler.compile("%caller{-2}").apply(caller, loggerName, level));
        Assert.assertEquals("com.example", compiler.compile("%caller{+2}").apply(caller, loggerName, level));
        Assert.assertEquals("*.PatternTest#<init>:15", compiler.compile("%caller{.-20}").apply(caller, loggerName, level));
        Assert.assertEquals("com.example.*", compiler.compile("%caller{.+20}").apply(caller, loggerName, level));
        Assert.assertEquals("PatternTest#<init>:15", compiler.compile("%caller{-2.-20}").apply(caller, loggerName, level));
        Assert.assertEquals("com.example", compiler.compile("%caller{+2.+20}").apply(caller, loggerName, level));

        Assert.assertEquals("(PatternTest.java:15)", compiler.compile("%source").apply(caller, loggerName, level));
        Assert.assertEquals("(PatternTest.java:15)", compiler.compile("%s").apply(caller, loggerName, level));

        Assert.assertEquals(
                "HH:mm:ss DEBUG                      com.noveo.android PatternTest#<init>:15:\n".substring(8),
                compiler.compile("%d{HH:mm:ss} %5level %60(%logger{30.30} %caller{-2.20}):%n").apply(caller, loggerName, level).substring(8));
    }

}

