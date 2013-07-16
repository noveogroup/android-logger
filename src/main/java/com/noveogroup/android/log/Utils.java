/*
 * Copyright (c) 2013 Noveo Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Except as contained in this notice, the name(s) of the above copyright holders
 * shall not be used in advertising or otherwise to promote the sale, use or
 * other dealings in this Software without prior written authorization.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.noveogroup.android.log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {

    private Utils() {
        throw new UnsupportedOperationException();
    }

    private static final String PACKAGE_NAME = Logger.class.getPackage().getName();

    private static final class CallerResolver extends SecurityManager {
        public Class<?> getCaller() {
            Class[] classContext = getClassContext();
            // sometimes class context is null (usually on new Android devices)
            if (classContext == null || classContext.length <= 0) {
                return null; // if class context is null or empty
            }

            boolean packageFound = false;
            for (Class aClass : classContext) {
                if (!packageFound) {
                    if (aClass.getPackage().getName().startsWith(PACKAGE_NAME)) {
                        packageFound = true;
                    }
                } else {
                    if (!aClass.getPackage().getName().startsWith(PACKAGE_NAME)) {
                        return aClass;
                    }
                }
            }
            return classContext[classContext.length - 1];
        }
    }

    private static final CallerResolver CALLER_RESOLVER = new CallerResolver();

    private static StackTraceElement getCallerStackTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null || stackTrace.length <= 0) {
            return null; // if stack trace is null or empty
        }

        boolean packageFound = false;
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (!packageFound) {
                if (stackTraceElement.getClassName().startsWith(PACKAGE_NAME)) {
                    packageFound = true;
                }
            } else {
                if (!stackTraceElement.getClassName().startsWith(PACKAGE_NAME)) {
                    return stackTraceElement;
                }
            }
        }
        return stackTrace[stackTrace.length - 1];
    }

    /**
     * Returns a name of a class that calls logging methods.
     * <p/>
     * Can be much faster than {@link #getCaller()} because
     * this method tries to use {@link SecurityManager} to get
     * caller context.
     *
     * @return the caller's name.
     */
    public static String getCallerClassName() {
        Class<?> caller = CALLER_RESOLVER.getCaller();
        if (caller == null) {
            StackTraceElement callerStackTrace = getCallerStackTrace();
            return callerStackTrace == null ? null : callerStackTrace.getClassName();
        } else {
            return caller.getName();
        }
    }

    /**
     * Returns stack trace element corresponding to a class that calls
     * logging methods.
     * <p/>
     * This method compares names of the packages of stack trace elements
     * with the package of this library to find information about caller.
     *
     * @return the caller stack trace element.
     */
    public static StackTraceElement getCaller() {
        return getCallerStackTrace();
    }

    /**
     * Shortens class name till the specified length.
     * <p/>
     * Note that only packages can be shortened so this method returns at least simple class name.
     *
     * @param className the class name.
     * @param maxLength the desired maximum length of result.
     * @param count the desired maximum count of packages
     * @return the shortened class name.
     */
    public static String shortenClassName(String className, int count, int maxLength) {

        className = shortenPackagesName(className, count);

        if (className == null) return null;
        if (maxLength == 0) return className;
        if (maxLength > className.length()) return className;

        if (maxLength < 0) {
            maxLength = - maxLength;
            StringBuilder builder = new StringBuilder();
            for (int index = className.length() - 1; index > 0; ) {
                int i = className.lastIndexOf('.', index);

                if (i == -1) {
                    if (builder.length() > 0
                            && builder.length() + index + 1 > maxLength) {
                        builder.insert(0, '*');
                        break;
                    }

                    builder.insert(0, className.substring(0, index + 1));
                } else {
                    if (builder.length() > 0
                            && builder.length() + (index + 1 - i) + 1 > maxLength) {
                        builder.insert(0, '*');
                        break;
                    }

                    builder.insert(0, className.substring(i, index + 1));
                }

                index = i - 1;
            }
            return builder.toString();

        } else {
            StringBuilder builder = new StringBuilder();
            for (int index = 0; index < className.length(); ) {
                int i = className.indexOf('.', index);

                if (i == -1) {
                    if (builder.length() > 0) {
                        builder.insert(builder.length(), '*');
                        break;
                    }

                    builder.insert(builder.length(), className.substring(index, className.length()));
                } else {
                    if (builder.length() > 0
                            && i + 1 > maxLength) {
                        builder.insert(builder.length(), '*');
                        break;
                    }

                    builder.insert(builder.length(), className.substring(index, i + 1));
                }

                index = i + 1;
            }

            return builder.toString();
        }
    }



    private static String shortenPackagesName(String className, int count) {
        if (className == null) return null;
        if (count == 0) return className;

        StringBuilder builder = new StringBuilder();
        if (count > 0) {
            int points = 1;
            for (int index = 0; index < className.length(); ) {
                int i = className.indexOf('.', index);

                if (i == -1) {
                    builder.insert(builder.length(), className.substring(index, className.length()));
                    break;

                } else {
                    if (points == count) {
                        builder.insert(builder.length(), className.substring(index, i));
                        break;
                    }
                    builder.insert(builder.length(), className.substring(index, i + 1));
                }
                index = i + 1;
                points++;
            }
        } else if (count < 0) {
            String exceptString = shortenPackagesName(className, -count);
            if (className.equals(exceptString)) {
                int from = className.lastIndexOf('.') + 1;
                int to = className.length();
                builder.insert(builder.length(), className.substring(from, to));
            } else
                return className.replaceFirst(exceptString + '.', "");
        }
        return builder.toString();
    }


    // todo move to PatternHandler
    private static final Pattern FORMAT_ARG = Pattern.compile("(%%|%(([-\\+]?\\d+)?(\\.[-\\+]?\\d+)?)(\\w)(\\{.*?\\})?)");

    //    %F              - a file name where the logging request was issued
    //    %C{length}      - a class name where the logging request was issued
    //    %M              - a method name where the logging request was issued
    //    %L              - a line where the logging request was issued
    //    %c{length}      - a name of a logger
    //    %d{date format} - date/time of a message. default date format is "yyyy-MM-dd HH:mm:ss.SSS"
    //    %m              - a message text
    //    %n              - a new line character
    //    %p              - a logging level
    //    %e{length}      - an exception stack trace. length can be any number
    //    %t              - a tag
    // todo delete after PatternHandler is implemented
    public static String format(String loggerFormat,
                                Class<?> calledClass,
                                String logger, String tag, Logger.Level level, String message, Throwable throwable) {
        List<String> args = new ArrayList<String>();

        StackTraceElement caller = null;

        StringBuilder builder = new StringBuilder();

        int index = 0;
        while (true) {
            Matcher matcher = FORMAT_ARG.matcher(loggerFormat);
            if (!matcher.find(index)) {
                break;
            }

            if ("%%".equals(matcher.group())) {
                builder.append(loggerFormat.substring(index, matcher.end()));
                index = matcher.end();
            } else {
                String modifier = matcher.group(2);
                String conversion = matcher.group(5);
                String options = matcher.group(6);
                if (options != null) {
                    options = options.substring(1, options.length() - 1);
                }

                String replacement;
                if ("n".equals(conversion)) { // new line
                    replacement = "%n";

                } else if ("F".equals(conversion)) { // a file name where the logging request was issued
                    replacement = "%" + modifier + "s";
                    if (caller == null) {
                        caller = getCaller();
                    }
                    String fileName = caller.getFileName();
                    args.add(fileName == null ? fileName : "<Unknown Source>");

                } else if ("C".equals(conversion)) { // a class name where the logging request was issued
                    replacement = "%" + modifier + "s";
                    if (caller == null) {
                        caller = getCaller();
                    }
                    if (options != null) {
                        args.add(shortenClassName(caller.getClassName(), Integer.valueOf(options)));
                    } else {
                        args.add(shortenClassName(caller.getClassName(), 0));
                    }

                } else if ("M".equals(conversion)) { // a method name where the logging request was issued
                    replacement = "%" + modifier + "s";
                    if (caller == null) {
                        caller = getCaller();
                    }
                    args.add(caller.getMethodName());

                } else if ("L".equals(conversion)) { // a line where the logging request was issued
                    replacement = "%" + modifier + "s";
                    if (caller == null) {
                        caller = getCaller();
                    }
                    args.add(String.valueOf(caller.getLineNumber()));

                } else {
                    replacement = matcher.group();
                }

                builder.append(loggerFormat.substring(index, matcher.start()));
                builder.append(replacement);
                index = matcher.end();
            }
        }
        builder.append(loggerFormat.substring(index));

        return String.format(builder.toString(), args.toArray());
    }

    // todo delete after PatternHandler is implemented
    private static enum FormatConversion {

        PERCENT('%', null),
        NEWLINE('n', null),
        DATE('d', "date"),
        LOGGER('c', "logger"),
        TAG('t', "tag"),
        LEVEL('p', "level"),
        MESSAGE('m', "message"),
        CALLER('C', "caller"),
        TRACE('T', "trace");

        public static final Pattern REGEXP;
        public static final int MODIFIER_GROUP = 1;
        public static final int CONVERSION_GROUP = 4;
        public static final int OPTIONS_GROUP = 5;

        static {
            StringBuilder builder = new StringBuilder();
            builder.append("%((-?\\d+)?(\\.[-\\+]?\\d+)?)");
            builder.append("(");
            for (FormatConversion formatConversion : values()) {
                if (formatConversion.longConversion != null) {
                    builder.append(formatConversion.longConversion).append("|");
                }
            }
            builder.append("\\w)");
            builder.append("(\\{.*?\\})?");

            REGEXP = Pattern.compile(builder.toString());
        }

        private final char shortConversion;
        private final String longConversion;

        private FormatConversion(char shortConversion, String longConversion) {
            this.shortConversion = shortConversion;
            this.longConversion = longConversion;
        }

        public boolean checkConversion(String conversion) {
            return (longConversion != null && longConversion.equals(conversion))
                    || (conversion.length() == 1 && shortConversion == conversion.charAt(0));
        }

        public String format(String options, Class<?> calledClass,
                             String logger, String tag, Logger.Level level, String message) {
            return "<" + shortConversion + ">";
        }

    }

    public static String format2(String loggerFormat,
                                 Class<?> calledClass,
                                 String logger, String tag, Logger.Level level, String message) {
        StringBuilder builder = new StringBuilder();

        int index = 0;
        while (true) {
            Matcher matcher = FormatConversion.REGEXP.matcher(loggerFormat);
            if (!matcher.find(index)) {
                break;
            }

            String modifier = matcher.group(FormatConversion.MODIFIER_GROUP);
            String conversion = matcher.group(FormatConversion.CONVERSION_GROUP);
            String options = matcher.group(FormatConversion.OPTIONS_GROUP);
            if (options != null) {
                options = options.substring(1, options.length() - 1);
            }

            String replacement = ""; // todo call FormatConversion.format()

            builder.append(loggerFormat.substring(index, matcher.start()));
            builder.append(String.format("%" + modifier + "s", replacement));
            index = matcher.end();
        }
        builder.append(loggerFormat.substring(index));

        return builder.toString();
    }

}
