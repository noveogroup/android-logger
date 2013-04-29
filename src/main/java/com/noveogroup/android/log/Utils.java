package com.noveogroup.android.log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Utils {

    private Utils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns caller's {@link StackTraceElement}.
     *
     * @param aClass a class used as starting point to find a caller.
     * @return the caller stack trace element.
     */
    public static StackTraceElement getCaller(Class<?> aClass) {
        String className = aClass.getName();

        boolean packageFound = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (!packageFound) {
                if (stackTraceElement.getClassName().equals(className)) {
                    packageFound = true;
                }
            } else {
                if (!stackTraceElement.getClassName().equals(className)) {
                    return stackTraceElement;
                }
            }
        }
        return stackTrace[stackTrace.length - 1];
    }

    /**
     * Returns caller's class name.
     *
     * @param aClass a class used as starting point to find a caller.
     * @return the class name of a caller.
     */
    public static String getCallerClassName(Class<?> aClass) {
        return getCaller(aClass).getClassName();
    }

    /**
     * Shortens class name till the specified length.
     * <p/>
     * Note that only packages can be shortened so this method returns at least simple class name.
     *
     * @param className the class name.
     * @param maxLength the desired maximum length of result.
     * @return the shortened class name.
     */
    public static String shortenClassName(String className, int maxLength) {
        if (className == null) return null;
        if (maxLength > className.length()) return className;

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
    }

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
    // todo JavaDoc
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
                        caller = getCaller(calledClass);
                    }
                    String fileName = caller.getFileName();
                    args.add(fileName == null ? fileName : "<Unknown Source>");

                } else if ("C".equals(conversion)) { // a class name where the logging request was issued
                    replacement = "%" + modifier + "s";
                    if (caller == null) {
                        caller = getCaller(calledClass);
                    }
                    if (options != null) {
                        args.add(shortenClassName(caller.getClassName(), Integer.valueOf(options)));
                    } else {
                        args.add(shortenClassName(caller.getClassName(), 0));
                    }

                } else if ("M".equals(conversion)) { // a method name where the logging request was issued
                    replacement = "%" + modifier + "s";
                    if (caller == null) {
                        caller = getCaller(calledClass);
                    }
                    args.add(caller.getMethodName());

                } else if ("L".equals(conversion)) { // a line where the logging request was issued
                    replacement = "%" + modifier + "s";
                    if (caller == null) {
                        caller = getCaller(calledClass);
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

}
