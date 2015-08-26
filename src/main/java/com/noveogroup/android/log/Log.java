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
 * This class gets logger using {@link LoggerManager#getLogger()} and
 * delegates calls to it.
 */
public final class Log {

    private Log() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the specified log level is enabled or not for this logger.
     *
     * @param level the level.
     * @return Are messages with this level allowed to be logged or not.
     * @see Logger#isEnabled(Logger.Level)
     */
    public static boolean isEnabled(Logger.Level level) {
        return LoggerManager.getLogger().isEnabled(level);
    }

    /**
     * Checks if {@link Logger.Level#VERBOSE VERBOSE} level is enabled or not.
     *
     * @return Are messages with {@link Logger.Level#VERBOSE VERBOSE} level
     * allowed to be logged or not.
     * @see Logger#isVerboseEnabled()
     */
    public static boolean isVerboseEnabled() {
        return LoggerManager.getLogger().isVerboseEnabled();
    }

    /**
     * Checks if {@link Logger.Level#DEBUG DEBUG} level is enabled or not.
     *
     * @return Are messages with {@link Logger.Level#DEBUG DEBUG} level
     * allowed to be logged or not.
     * @see Logger#isDebugEnabled()
     */
    public static boolean isDebugEnabled() {
        return LoggerManager.getLogger().isDebugEnabled();
    }

    /**
     * Checks if {@link Logger.Level#INFO INFO} level is enabled or not.
     *
     * @return Are messages with {@link Logger.Level#INFO INFO} level
     * allowed to be logged or not.
     * @see Logger#isInfoEnabled()
     */
    public static boolean isInfoEnabled() {
        return LoggerManager.getLogger().isInfoEnabled();
    }

    /**
     * Checks if {@link Logger.Level#WARN WARN} level is enabled or not.
     *
     * @return Are messages with {@link Logger.Level#WARN WARN} level
     * allowed to be logged or not.
     * @see Logger#isWarnEnabled()
     */
    public static boolean isWarnEnabled() {
        return LoggerManager.getLogger().isWarnEnabled();
    }

    /**
     * Checks if {@link Logger.Level#ERROR ERROR} level is enabled or not.
     *
     * @return Are messages with {@link Logger.Level#ERROR ERROR} level
     * allowed to be logged or not.
     * @see Logger#isErrorEnabled()
     */
    public static boolean isErrorEnabled() {
        return LoggerManager.getLogger().isErrorEnabled();
    }

    /**
     * Checks if {@link Logger.Level#ASSERT ASSERT} level is enabled or not.
     *
     * @return Are messages with {@link Logger.Level#ASSERT ASSERT} level
     * allowed to be logged or not.
     * @see Logger#isAssertEnabled()
     */
    public static boolean isAssertEnabled() {
        return LoggerManager.getLogger().isAssertEnabled();
    }

    /**
     * Low-level logging call.
     *
     * @param level     a level of this log message
     * @param throwable an additional throwable object.
     * @param message   a message you would like logged.
     */
    public static void print(Logger.Level level, Throwable throwable, String message) {
        LoggerManager.getLogger().print(level, throwable, message);
    }

    /**
     * Low-level logging call.
     *
     * @param level         a level of this log message
     * @param throwable     an additional throwable object.
     * @param messageFormat a message format you would like logged.
     * @param args          arguments for a formatter.
     */
    public static void print(Logger.Level level, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().print(level, throwable, messageFormat, args);
    }

    /**
     * Send a {@link Logger.Level#VERBOSE} log message.
     *
     * @param message   a message you would like logged.
     * @param throwable an additional throwable object.
     */
    public static void v(String message, Throwable throwable) {
        LoggerManager.getLogger().v(message, throwable);
    }

    /**
     * Send a {@link Logger.Level#DEBUG} log message.
     *
     * @param message   a message you would like logged.
     * @param throwable an additional throwable object.
     */
    public static void d(String message, Throwable throwable) {
        LoggerManager.getLogger().d(message, throwable);
    }

    /**
     * Send a {@link Logger.Level#INFO} log message.
     *
     * @param message   a message you would like logged.
     * @param throwable an additional throwable object.
     */
    public static void i(String message, Throwable throwable) {
        LoggerManager.getLogger().i(message, throwable);
    }

    /**
     * Send a {@link Logger.Level#WARN} log message.
     *
     * @param message   a message you would like logged.
     * @param throwable an additional throwable object.
     */
    public static void w(String message, Throwable throwable) {
        LoggerManager.getLogger().w(message, throwable);
    }

    /**
     * Send a {@link Logger.Level#ERROR} log message.
     *
     * @param message   a message you would like logged.
     * @param throwable an additional throwable object.
     */
    public static void e(String message, Throwable throwable) {
        LoggerManager.getLogger().e(message, throwable);
    }

    /**
     * Send a {@link Logger.Level#ASSERT} log message.
     *
     * @param message   a message you would like logged.
     * @param throwable an additional throwable object.
     */
    public static void a(String message, Throwable throwable) {
        LoggerManager.getLogger().a(message, throwable);
    }

    /**
     * Send a {@link Logger.Level#VERBOSE} log message.
     *
     * @param throwable a throwable object to send.
     */
    public static void v(Throwable throwable) {
        LoggerManager.getLogger().v(throwable);
    }

    /**
     * Send a {@link Logger.Level#DEBUG} log message.
     *
     * @param throwable a throwable object to send.
     */
    public static void d(Throwable throwable) {
        LoggerManager.getLogger().d(throwable);
    }

    /**
     * Send a {@link Logger.Level#INFO} log message.
     *
     * @param throwable a throwable object to send.
     */
    public static void i(Throwable throwable) {
        LoggerManager.getLogger().i(throwable);
    }

    /**
     * Send a {@link Logger.Level#WARN} log message.
     *
     * @param throwable a throwable object to send.
     */
    public static void w(Throwable throwable) {
        LoggerManager.getLogger().w(throwable);
    }

    /**
     * Send a {@link Logger.Level#ERROR} log message.
     *
     * @param throwable a throwable object to send.
     */
    public static void e(Throwable throwable) {
        LoggerManager.getLogger().e(throwable);
    }

    /**
     * Send a {@link Logger.Level#ASSERT} log message.
     *
     * @param throwable a throwable object to send.
     */
    public static void a(Throwable throwable) {
        LoggerManager.getLogger().a(throwable);
    }

    /**
     * Send a {@link Logger.Level#VERBOSE} log message.
     *
     * @param throwable     an additional throwable object.
     * @param messageFormat a message format you would like logged.
     * @param args          arguments for a formatter.
     */
    public static void v(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().v(throwable, messageFormat, args);
    }

    /**
     * Send a {@link Logger.Level#DEBUG} log message.
     *
     * @param throwable     an additional throwable object.
     * @param messageFormat a message format you would like logged.
     * @param args          arguments for a formatter.
     */
    public static void d(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().d(throwable, messageFormat, args);
    }

    /**
     * Send a {@link Logger.Level#INFO} log message.
     *
     * @param throwable     an additional throwable object.
     * @param messageFormat a message format you would like logged.
     * @param args          arguments for a formatter.
     */
    public static void i(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().i(throwable, messageFormat, args);
    }

    /**
     * Send a {@link Logger.Level#WARN} log message.
     *
     * @param throwable     an additional throwable object.
     * @param messageFormat a message format you would like logged.
     * @param args          arguments for a formatter.
     */
    public static void w(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().w(throwable, messageFormat, args);
    }

    /**
     * Send a {@link Logger.Level#ERROR} log message.
     *
     * @param throwable     an additional throwable object.
     * @param messageFormat a message format you would like logged.
     * @param args          arguments for a formatter.
     */
    public static void e(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().e(throwable, messageFormat, args);
    }

    /**
     * Send a {@link Logger.Level#ASSERT} log message.
     *
     * @param throwable     an additional throwable object.
     * @param messageFormat a message format you would like logged.
     * @param args          arguments for a formatter.
     */
    public static void a(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().a(throwable, messageFormat, args);
    }

    /**
     * Send a {@link Logger.Level#VERBOSE} log message.
     *
     * @param throwable an additional throwable object.
     * @param message   a message you would like logged.
     */
    public static void v(Throwable throwable, String message) {
        LoggerManager.getLogger().v(throwable, message);
    }

    /**
     * Send a {@link Logger.Level#DEBUG} log message.
     *
     * @param throwable an additional throwable object.
     * @param message   a message you would like logged.
     */
    public static void d(Throwable throwable, String message) {
        LoggerManager.getLogger().d(throwable, message);
    }

    /**
     * Send a {@link Logger.Level#INFO} log message.
     *
     * @param throwable an additional throwable object.
     * @param message   a message you would like logged.
     */
    public static void i(Throwable throwable, String message) {
        LoggerManager.getLogger().i(throwable, message);
    }

    /**
     * Send a {@link Logger.Level#WARN} log message.
     *
     * @param throwable an additional throwable object.
     * @param message   a message you would like logged.
     */
    public static void w(Throwable throwable, String message) {
        LoggerManager.getLogger().w(throwable, message);
    }

    /**
     * Send a {@link Logger.Level#ERROR} log message.
     *
     * @param throwable an additional throwable object.
     * @param message   a message you would like logged.
     */
    public static void e(Throwable throwable, String message) {
        LoggerManager.getLogger().e(throwable, message);
    }

    /**
     * Send a {@link Logger.Level#ASSERT} log message.
     *
     * @param throwable an additional throwable object.
     * @param message   a message you would like logged.
     */
    public static void a(Throwable throwable, String message) {
        LoggerManager.getLogger().a(throwable, message);
    }

    /**
     * Send a {@link Logger.Level#VERBOSE} log message.
     *
     * @param messageFormat a message format you would like logged.
     * @param args          arguments for a formatter.
     */
    public static void v(String messageFormat, Object... args) {
        LoggerManager.getLogger().v(messageFormat, args);
    }

    /**
     * Send a {@link Logger.Level#DEBUG} log message.
     *
     * @param messageFormat a message format you would like logged.
     * @param args          arguments for a formatter.
     */
    public static void d(String messageFormat, Object... args) {
        LoggerManager.getLogger().d(messageFormat, args);
    }

    /**
     * Send a {@link Logger.Level#INFO} log message.
     *
     * @param messageFormat a message format you would like logged.
     * @param args          arguments for a formatter.
     */
    public static void i(String messageFormat, Object... args) {
        LoggerManager.getLogger().i(messageFormat, args);
    }

    /**
     * Send a {@link Logger.Level#WARN} log message.
     *
     * @param messageFormat a message format you would like logged.
     * @param args          arguments for a formatter.
     */
    public static void w(String messageFormat, Object... args) {
        LoggerManager.getLogger().w(messageFormat, args);
    }

    /**
     * Send a {@link Logger.Level#ERROR} log message.
     *
     * @param messageFormat a message format you would like logged.
     * @param args          arguments for a formatter.
     */
    public static void e(String messageFormat, Object... args) {
        LoggerManager.getLogger().e(messageFormat, args);
    }

    /**
     * Send a {@link Logger.Level#ASSERT} log message.
     *
     * @param messageFormat a message format you would like logged.
     * @param args          arguments for a formatter.
     */
    public static void a(String messageFormat, Object... args) {
        LoggerManager.getLogger().a(messageFormat, args);
    }

    /**
     * Send a {@link Logger.Level#VERBOSE} log message.
     *
     * @param message a message you would like logged.
     */
    public static void v(String message) {
        LoggerManager.getLogger().v(message);
    }

    /**
     * Send a {@link Logger.Level#DEBUG} log message.
     *
     * @param message a message you would like logged.
     */
    public static void d(String message) {
        LoggerManager.getLogger().d(message);
    }

    /**
     * Send a {@link Logger.Level#INFO} log message.
     *
     * @param message a message you would like logged.
     */
    public static void i(String message) {
        LoggerManager.getLogger().i(message);
    }

    /**
     * Send a {@link Logger.Level#WARN} log message.
     *
     * @param message a message you would like logged.
     */
    public static void w(String message) {
        LoggerManager.getLogger().w(message);
    }

    /**
     * Send a {@link Logger.Level#ERROR} log message.
     *
     * @param message a message you would like logged.
     */
    public static void e(String message) {
        LoggerManager.getLogger().e(message);
    }

    /**
     * Send a {@link Logger.Level#ASSERT} log message.
     *
     * @param message a message you would like logged.
     */
    public static void a(String message) {
        LoggerManager.getLogger().a(message);
    }

}
