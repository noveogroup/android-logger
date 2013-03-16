package com.noveogroup.android.log;

/**
 * Abstract implementation of {@link Logger} interface.
 */
public abstract class AbstractLogger implements Logger {

    @Override
    public void v(String message, Throwable throwable) {
        print(Logger.Level.VERBOSE, message, throwable);
    }

    @Override
    public void d(String message, Throwable throwable) {
        print(Logger.Level.DEBUG, message, throwable);
    }

    @Override
    public void i(String message, Throwable throwable) {
        print(Logger.Level.INFO, message, throwable);
    }

    @Override
    public void w(String message, Throwable throwable) {
        print(Logger.Level.WARN, message, throwable);
    }

    @Override
    public void e(String message, Throwable throwable) {
        print(Logger.Level.ERROR, message, throwable);
    }

    @Override
    public void a(String message, Throwable throwable) {
        print(Logger.Level.ASSERT, message, throwable);
    }

    @Override
    public void v(Throwable throwable) {
        print(Logger.Level.VERBOSE, null, throwable);
    }

    @Override
    public void d(Throwable throwable) {
        print(Logger.Level.DEBUG, null, throwable);
    }

    @Override
    public void i(Throwable throwable) {
        print(Logger.Level.INFO, null, throwable);
    }

    @Override
    public void w(Throwable throwable) {
        print(Logger.Level.WARN, null, throwable);
    }

    @Override
    public void e(Throwable throwable) {
        print(Logger.Level.ERROR, null, throwable);
    }

    @Override
    public void a(Throwable throwable) {
        print(Logger.Level.ASSERT, null, throwable);
    }

    @Override
    public void v(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.VERBOSE, throwable, messageFormat, args);
    }

    @Override
    public void d(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.DEBUG, throwable, messageFormat, args);
    }

    @Override
    public void i(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.INFO, throwable, messageFormat, args);
    }

    @Override
    public void w(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.WARN, throwable, messageFormat, args);
    }

    @Override
    public void e(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.ERROR, throwable, messageFormat, args);
    }

    @Override
    public void a(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.ASSERT, throwable, messageFormat, args);
    }

    @Override
    public void v(String messageFormat, Object... args) {
        print(Logger.Level.VERBOSE, null, messageFormat, args);
    }

    @Override
    public void d(String messageFormat, Object... args) {
        print(Logger.Level.DEBUG, null, messageFormat, args);
    }

    @Override
    public void i(String messageFormat, Object... args) {
        print(Logger.Level.INFO, null, messageFormat, args);
    }

    @Override
    public void w(String messageFormat, Object... args) {
        print(Logger.Level.WARN, null, messageFormat, args);
    }

    @Override
    public void e(String messageFormat, Object... args) {
        print(Logger.Level.ERROR, null, messageFormat, args);
    }

    @Override
    public void a(String messageFormat, Object... args) {
        print(Logger.Level.ASSERT, null, messageFormat, args);
    }

}
