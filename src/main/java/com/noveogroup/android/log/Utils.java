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
     * Shorten string
     *
     * @param string modified string.
     * @param count the desired minimum length of result.
     * @param length the desired maximum of string length.
     * <p/>
     * <table border=1>
     * <tr>
     * <th>Example</th>
     * <th>Result</th>
     * </tr>
     * <tr> <td>%6(text)</td>   <td><pre>'  text'</pre></td> </tr>
     * <tr> <td>%-6(text)</td>  <td><pre>'text  '</pre></td> </tr>
     * <tr> <td>%.3(text)</td>  <td><pre>'tex'</pre></td>    </tr>
     * <tr> <td>%.-3(text)</td> <td><pre>'ext'</pre></td>    </tr>
     * </table>
     */
    public static String shorten(String string, int count, int length) {

        if (string == null) return null;

        String resultString = string;
        if (Math.abs(length) < resultString.length()) {
            if (length > 0)
                resultString = string.substring(0, length);
            if (length < 0)
                resultString = string.substring(string.length() + length, string.length());
        }

        if (Math.abs(count) > resultString.length()) {

            return String.format("%" + count + "s", resultString);
        }

        return resultString;
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
    // todo optimize it
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
                    break;
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

    // todo optimize it
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

}
