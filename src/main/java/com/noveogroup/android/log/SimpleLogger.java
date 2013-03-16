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

import android.util.Log;

/**
 * Simple implementation of {@link Logger} that prints all messages
 * using {@link Log} with the specified tag.
 * <p/>
 * This logger can be configured with logging level parameter which will be
 * the minimal level of log messages printed by this logger instance.
 * The logging level can be {@code null} which means no messages should be
 * printed using this logger.
 * <p/>
 * <b>Attention</b>: Android may set its own requirement for logging level
 * using {@link Log#isLoggable(String, int)} method. This logger takes it
 * into account in {@link #isEnabled(Level)}.
 */
public class SimpleLogger extends AbstractLogger {

    private final String tag;
    private final Level level;

    /**
     * Creates new {@link SimpleLogger} instance.
     *
     * @param tag   the tag.
     * @param level the logging level or {@code null} if no messages should be printed.
     */
    public SimpleLogger(String tag, Level level) {
        this.tag = tag;
        this.level = level;
    }

    /**
     * Returns the tag.
     *
     * @return the tag.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Returns the level.
     *
     * @return the level.
     */
    public Level getLevel() {
        return level;
    }

    @Override
    public boolean isEnabled(Level level) {
        return this.level != null && this.level.includes(level)
                && level != null && Log.isLoggable(tag, level.intValue());
    }

    @Override
    public void print(Level level, String message, Throwable throwable) {
        if (isEnabled(level)) {
            if (throwable != null) {
                message = message + '\n' + Log.getStackTraceString(throwable);
            }

            Log.println(level.intValue(), tag, message);
        }
    }

    @Override
    public void print(Level level, Throwable throwable, String messageFormat, Object... args) {
        if (isEnabled(level)) {
            String message;

            if (messageFormat == null) {
                if (args != null && args.length > 0) {
                    throw new IllegalArgumentException("message format is not set but arguments are presented");
                }

                if (throwable == null) {
                    message = "";
                } else {
                    message = Log.getStackTraceString(throwable);
                }
            } else {
                if (throwable == null) {
                    message = String.format(messageFormat, args);
                } else {
                    message = String.format(messageFormat, args) + '\n' + Log.getStackTraceString(throwable);
                }
            }

            Log.println(level.intValue(), tag, message);
        }
    }

}
