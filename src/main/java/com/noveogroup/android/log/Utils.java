package com.noveogroup.android.log;

final class Utils {

    private Utils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns caller's {@link StackTraceElement}.
     *
     * @param aClass a class used as starting point to find a caller.
     * @return the caller stack trace element.
     */
    public static StackTraceElement getCaller(Class<?> aClass) {
        String className = aClass.getName();

        boolean packageFound = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (!packageFound) {
                if (stackTraceElement.getClassName().equals(className)) {
                    packageFound = true;
                }
            } else {
                if (!stackTraceElement.getClassName().equals(className)) {
                    return stackTraceElement;
                }
            }
        }
        return stackTrace[stackTrace.length - 1];
    }

    /**
     * Returns caller's class name.
     *
     * @param aClass a class used as starting point to find a caller.
     * @return the class name of a caller.
     */
    public static String getCallerClassName(Class<?> aClass) {
        return getCaller(aClass).getClassName();
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
