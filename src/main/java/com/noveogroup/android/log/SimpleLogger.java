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
 * Simple implementation of {@link Logger} that prints all messages
 * using {@link Handler} interface.
 */
public class SimpleLogger extends AbstractLogger {

    private final Handler handler;

    /**
     * Creates new {@link SimpleLogger} instance.
     *
     * @param name    the name of the logger.
     * @param handler the handler to log messages.
     */
    public SimpleLogger(String name, Handler handler) {
        super(name);
        this.handler = handler;
    }

    @Override
    public boolean isEnabled(Level level) {
        return handler != null && handler.isEnabled(level);
    }

    @Override
    public void print(Level level, String message, Throwable throwable) {
        if (handler != null) {
            handler.print(getName(), level, throwable, message);
        }
    }

    @Override
    public void print(Level level, Throwable throwable, String messageFormat, Object... args) {
        if (handler != null) {
            handler.print(getName(), level, throwable, messageFormat, args);
        }
    }

}
