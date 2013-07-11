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

/**
 * This class simplifies usage of Android Logger for debugging purposes.
 * <p/>
 * Any call like {@code LOG.someMethod(arguments)} is equal to:
 * <p/>
 * <code>
 * LoggerManager.getLogger().someMethod(arguments);
 * </code>
 */
public final class LOG {

    private LOG() {
        throw new UnsupportedOperationException();
    }

    public static boolean isEnabled(Logger.Level level) {
        return LoggerManager.getLogger().isEnabled(level);
    }

    public static void print(Logger.Level level, String message, Throwable throwable) {
        LoggerManager.getLogger().print(level, message, throwable);
    }

    public static void print(Logger.Level level, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().print(level, throwable, messageFormat, args);
    }

    public static void v(String message, Throwable throwable) {
        LoggerManager.getLogger().v(message, throwable);
    }

    public static void d(String message, Throwable throwable) {
        LoggerManager.getLogger().d(message, throwable);
    }

    public static void i(String message, Throwable throwable) {
        LoggerManager.getLogger().i(message, throwable);
    }

    public static void w(String message, Throwable throwable) {
        LoggerManager.getLogger().w(message, throwable);
    }

    public static void e(String message, Throwable throwable) {
        LoggerManager.getLogger().e(message, throwable);
    }

    public static void a(String message, Throwable throwable) {
        LoggerManager.getLogger().a(message, throwable);
    }

    public static void v(Throwable throwable) {
        LoggerManager.getLogger().v(throwable);
    }

    public static void d(Throwable throwable) {
        LoggerManager.getLogger().d(throwable);
    }

    public static void i(Throwable throwable) {
        LoggerManager.getLogger().i(throwable);
    }

    public static void w(Throwable throwable) {
        LoggerManager.getLogger().w(throwable);
    }

    public static void e(Throwable throwable) {
        LoggerManager.getLogger().e(throwable);
    }

    public static void a(Throwable throwable) {
        LoggerManager.getLogger().a(throwable);
    }

    public static void v(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().v(throwable, messageFormat, args);
    }

    public static void d(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().d(throwable, messageFormat, args);
    }

    public static void i(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().i(throwable, messageFormat, args);
    }

    public static void w(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().w(throwable, messageFormat, args);
    }

    public static void e(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().e(throwable, messageFormat, args);
    }

    public static void a(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().a(throwable, messageFormat, args);
    }

    public static void v(String messageFormat, Object... args) {
        LoggerManager.getLogger().v(messageFormat, args);
    }

    public static void d(String messageFormat, Object... args) {
        LoggerManager.getLogger().d(messageFormat, args);
    }

    public static void i(String messageFormat, Object... args) {
        LoggerManager.getLogger().i(messageFormat, args);
    }

    public static void w(String messageFormat, Object... args) {
        LoggerManager.getLogger().w(messageFormat, args);
    }

    public static void e(String messageFormat, Object... args) {
        LoggerManager.getLogger().e(messageFormat, args);
    }

    public static void a(String messageFormat, Object... args) {
        LoggerManager.getLogger().a(messageFormat, args);
    }

}
