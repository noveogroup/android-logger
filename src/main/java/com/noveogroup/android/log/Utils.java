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
    public static String getCallerClassName() {
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

    /**
     * Shortens class name till the specified length.
     * <p/>
     * Note that only packages can be shortened so this method returns at least simple class name.
     *
     * @param className the class name.
     * @param maxLength the desired maximum length of result.
     * @return the shortened class name.
     */
    public static String shortenClassName(String className, int maxLength) {
        if (className == null) return null;
        if (maxLength > className.length()) return className;

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
    }

}
