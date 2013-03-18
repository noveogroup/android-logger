package com.noveogroup.android.log;

/**
 * This class simplifies usage of Android Logger for debugging purposes.
 * <p/>
 * Any call like {@code LOG.someMethod(arguments)} is equal to:
 * <p/>
 * <code>
 * LoggerManager.getLogger().someMethod(arguments);
 * </code>
 */
public final class LOG {

    private LOG() {
        throw new UnsupportedOperationException();
    }

    public static boolean isEnabled(Logger.Level level) {
        return LoggerManager.getLogger().isEnabled(level);
    }

    public static void print(Logger.Level level, String message, Throwable throwable) {
        LoggerManager.getLogger().print(level, message, throwable);
    }

    public static void print(Logger.Level level, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().print(level, throwable, messageFormat, args);
    }

    public static void v(String message, Throwable throwable) {
        LoggerManager.getLogger().v(message, throwable);
    }

    public static void d(String message, Throwable throwable) {
        LoggerManager.getLogger().d(message, throwable);
    }

    public static void i(String message, Throwable throwable) {
        LoggerManager.getLogger().i(message, throwable);
    }

    public static void w(String message, Throwable throwable) {
        LoggerManager.getLogger().w(message, throwable);
    }

    public static void e(String message, Throwable throwable) {
        LoggerManager.getLogger().e(message, throwable);
    }

    public static void a(String message, Throwable throwable) {
        LoggerManager.getLogger().a(message, throwable);
    }

    public static void v(Throwable throwable) {
        LoggerManager.getLogger().v(throwable);
    }

    public static void d(Throwable throwable) {
        LoggerManager.getLogger().d(throwable);
    }

    public static void i(Throwable throwable) {
        LoggerManager.getLogger().i(throwable);
    }

    public static void w(Throwable throwable) {
        LoggerManager.getLogger().w(throwable);
    }

    public static void e(Throwable throwable) {
        LoggerManager.getLogger().e(throwable);
    }

    public static void a(Throwable throwable) {
        LoggerManager.getLogger().a(throwable);
    }

    public static void v(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().v(throwable, messageFormat, args);
    }

    public static void d(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().d(throwable, messageFormat, args);
    }

    public static void i(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().i(throwable, messageFormat, args);
    }

    public static void w(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().w(throwable, messageFormat, args);
    }

    public static void e(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().e(throwable, messageFormat, args);
    }

    public static void a(Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().a(throwable, messageFormat, args);
    }

    public static void v(String messageFormat, Object... args) {
        LoggerManager.getLogger().v(messageFormat, args);
    }

    public static void d(String messageFormat, Object... args) {
        LoggerManager.getLogger().d(messageFormat, args);
    }

    public static void i(String messageFormat, Object... args) {
        LoggerManager.getLogger().i(messageFormat, args);
    }

    public static void w(String messageFormat, Object... args) {
        LoggerManager.getLogger().w(messageFormat, args);
    }

    public static void e(String messageFormat, Object... args) {
        LoggerManager.getLogger().e(messageFormat, args);
    }

    public static void a(String messageFormat, Object... args) {
        LoggerManager.getLogger().a(messageFormat, args);
    }

}
