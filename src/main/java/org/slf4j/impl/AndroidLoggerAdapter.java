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

package org.slf4j.impl;

import com.noveogroup.android.log.Logger;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

/**
 * A wrapper over {@link com.noveogroup.android.log.Logger com.noveogroup.android.log.Logger}
 * in conforming to the {@link org.slf4j.Logger org.slf4j.Logger} interface.
 */
public class AndroidLoggerAdapter extends MarkerIgnoringBase {

    private final Logger logger;

    /**
     * Creates new instance of {@link AndroidLoggerAdapter}.
     *
     * @param logger the underlying logger.
     */
    public AndroidLoggerAdapter(Logger logger) {
        this.logger = logger;
    }

    private boolean isEnabled(Logger.Level level) {
        return logger.isEnabled(level);
    }

    private void log(Logger.Level level, String msg) {
        logger.print(level, msg, null);
    }

    private void log(Logger.Level level, String format, Object arg) {
        logger.print(level, MessageFormatter.format(format, arg).getMessage(), null);
    }

    private void log(Logger.Level level, String format, Object arg1, Object arg2) {
        logger.print(level, MessageFormatter.format(format, arg1, arg2).getMessage(), null);
    }

    private void log(Logger.Level level, String format, Object... arguments) {
        logger.print(level, MessageFormatter.arrayFormat(format, arguments).getMessage(), null);
    }

    private void log(Logger.Level level, String msg, Throwable t) {
        logger.print(level, msg, t);
    }

    @Override
    public boolean isTraceEnabled() {
        return isEnabled(Logger.Level.VERBOSE);
    }

    @Override
    public void trace(String msg) {
        log(Logger.Level.VERBOSE, msg);
    }

    @Override
    public void trace(String format, Object arg) {
        log(Logger.Level.VERBOSE, format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        log(Logger.Level.VERBOSE, format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object... arguments) {
        log(Logger.Level.VERBOSE, format, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        log(Logger.Level.VERBOSE, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return isEnabled(Logger.Level.DEBUG);
    }

    @Override
    public void debug(String msg) {
        log(Logger.Level.DEBUG, msg);
    }

    @Override
    public void debug(String format, Object arg) {
        log(Logger.Level.DEBUG, format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        log(Logger.Level.DEBUG, format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        log(Logger.Level.DEBUG, format, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        log(Logger.Level.DEBUG, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return isEnabled(Logger.Level.INFO);
    }

    @Override
    public void info(String msg) {
        log(Logger.Level.INFO, msg);
    }

    @Override
    public void info(String format, Object arg) {
        log(Logger.Level.INFO, format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        log(Logger.Level.INFO, format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        log(Logger.Level.INFO, format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        log(Logger.Level.INFO, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return isEnabled(Logger.Level.WARN);
    }

    @Override
    public void warn(String msg) {
        log(Logger.Level.WARN, msg);
    }

    @Override
    public void warn(String format, Object arg) {
        log(Logger.Level.WARN, format, arg);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        log(Logger.Level.WARN, format, arg1, arg2);
    }

    @Override
    public void warn(String format, Object... arguments) {
        log(Logger.Level.WARN, format, arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        log(Logger.Level.WARN, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return isEnabled(Logger.Level.ERROR);
    }

    @Override
    public void error(String msg) {
        log(Logger.Level.ERROR, msg);
    }

    @Override
    public void error(String format, Object arg) {
        log(Logger.Level.ERROR, format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        log(Logger.Level.ERROR, format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        log(Logger.Level.ERROR, format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        log(Logger.Level.ERROR, msg, t);
    }

}
