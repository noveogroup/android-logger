package com.noveogroup.android.log;

final class Utils {

    private Utils() {
        throw new UnsupportedOperationException();
    }

    private static final String LIBRARY_PACKAGE = "com.noveogroup.android.log";

    /**
     * Returns caller's class name.
     *
     * @return the class name of a caller.
     */
    static String getCallerClassName() {
        boolean packageFound = false;
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (!packageFound) {
                if (stackTraceElement.getClassName().startsWith(LIBRARY_PACKAGE)) {
                    packageFound = true;
                }
            } else {
                if (!stackTraceElement.getClassName().startsWith(LIBRARY_PACKAGE)) {
                    return stackTraceElement.getClassName();
                }
            }
        }
        return null;
    }

}
