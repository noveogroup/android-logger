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
                && level != null && _Log_.isLoggable(tag, level.intValue());
    }

    @Override
    public void print(Level level, String message, Throwable throwable) {
        if (isEnabled(level)) {
            if (throwable != null) {
                message = message + '\n' + _Log_.getStackTraceString(throwable);
            }

            _Log_.println(level.intValue(), tag, message);
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
                    message = _Log_.getStackTraceString(throwable);
                }
            } else {
                if (throwable == null) {
                    message = String.format(messageFormat, args);
                } else {
                    message = String.format(messageFormat, args) + '\n' + _Log_.getStackTraceString(throwable);
                }
            }

            _Log_.println(level.intValue(), tag, message);
        }
    }

}
