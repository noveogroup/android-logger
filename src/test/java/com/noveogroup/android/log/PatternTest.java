package com.noveogroup.android.log;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;

public class PatternTest {

    // %d{HH:mm:ss} %5level %60(%logger{30,30} %caller{30,30}):%n

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

    ArrayList<Pattern> patterns = new ArrayList<Pattern>();
    Pattern concatenatePattern = new Pattern.ConcatenatePattern(0, 0, patterns);

    ArrayList<Pattern> patternsChild = new ArrayList<Pattern>();
    Pattern concatenatePatternChild = new Pattern.ConcatenatePattern(60, 0, patternsChild);


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

        Assert.assertEquals("sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)",
                callerPattern.apply(caller, loggerName, level));

        Assert.assertEquals("com.noveo.android",
                loggerPattern.apply(caller, loggerName, level));


        patterns.add(dataPattern);
        patterns.add(spacePattern);
        patterns.add(levelPattern);
        patterns.add(spacePattern);

        patternsChild.add(loggerPattern);
        patternsChild.add(spacePattern);
        patternsChild.add(callerPattern);

        patterns.add(concatenatePatternChild);
        patterns.add(colonPattern);
        patterns.add(tabPattern);

      /*  Assert.assertEquals("log",
                concatenatePattern.apply(caller, loggerName, level));*/
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


        /*Matcher matcher = java.util.regex.Pattern.compile("^%(\\d+)?(\\.(\\d+))?logger(\\{(\\d+)?(\\.(\\d+))?\\})?").matcher("a%12logger{32.466}");

        Assert.assertEquals(true, matcher.find());

        int count = Integer.parseInt(matcher.group(1));
        int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
        int countLogger = Integer.parseInt(matcher.group(5));
        int lengthLogger = Integer.parseInt(matcher.group(7));

        Assert.assertEquals(12, count);
        Assert.assertEquals(0, length);
        Assert.assertEquals(32, countLogger);
        Assert.assertEquals(466, lengthLogger);*/

        Matcher matcher = java.util.regex.Pattern.compile("date(\\{(.*?)\\})?").matcher("%date{HH:mm:ss}");
        Assert.assertEquals(true, matcher.find());

        String dateFormat = matcher.group(2);

        Assert.assertEquals("HH:mm:ss", dateFormat);


    }

}

