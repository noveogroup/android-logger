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
