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

import java.io.PrintWriter;
import java.io.StringWriter;

public class _Log_ {

    public static boolean isLoggable(String tag, int level) {
        return level >= Log.DEBUG;
    }

    public static String getStackTraceString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    public static void println(int priority, String tag, String msg) {
        String prefix;
        switch (priority) {
            case Log.VERBOSE:
                prefix = String.format("V[%s] ", tag);
                break;
            case Log.DEBUG:
                prefix = String.format("D[%s] ", tag);
                break;
            case Log.INFO:
                prefix = String.format("I[%s] ", tag);
                break;
            case Log.WARN:
                prefix = String.format("W[%s] ", tag);
                break;
            case Log.ERROR:
                prefix = String.format("E[%s] ", tag);
                break;
            case Log.ASSERT:
                prefix = String.format("A[%s] ", tag);
                break;
            default:
                prefix = String.format(" [%s] ", tag);
        }
        System.out.println(prefix + msg.replaceAll("\n", '\n' + prefix));
    }

}
