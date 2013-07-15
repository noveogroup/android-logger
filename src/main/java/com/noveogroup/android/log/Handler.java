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

import java.util.Formatter;

/**
 * The log message handler.
 */
public interface Handler {

    /**
     * Checks if the specified log level is enabled or not for this handler.
     *
     * @param level the level.
     * @return Are messages with this level allowed to be logged or not.
     */
    public boolean isEnabled(Logger.Level level);

    /**
     * Prints a log message.
     * <p/>
     * This method should automatically check using {@link #isEnabled(Logger.Level)} method
     * if the message is allowed to be logged or not.
     * <p/>
     * The format string of the log message should be formatted
     * according to rules of the standard format string described
     * in JavaDoc of {@link Formatter}.
     * Implementations can use {@link Formatter#format(String, Object...)}
     * to prepare the log message from format string and array of arguments.
     *
     * @param loggerName    a name of a logger that user used to log message.
     * @param level         a level of the log message
     * @param throwable     a throwable object or {@code null}.
     * @param messageFormat a format string of the log message. Can be {@code null}.
     * @param args          an array of arguments. Can be {@code null}
     *                      which is considered as an empty array.
     * @throws IllegalArgumentException if no format string is specified but arguments are presented.
     */
    public void print(String loggerName, Logger.Level level,
                      Throwable throwable, String messageFormat, Object... args) throws IllegalArgumentException;

}
