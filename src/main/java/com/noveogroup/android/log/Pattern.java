package com.noveogroup.android.log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

/**
 * This class responsible for formatting messages for {@link PatternHandler}.
 */
public abstract class Pattern {

    public static class PlainPattern extends Pattern {

        private final String string;

        public PlainPattern(int count, int length, String string) {
            super(count, length);
            this.string = string;
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level) {
            return string;
        }

    }

    public static class DatePattern extends Pattern {

        private final SimpleDateFormat dateFormat;

        public DatePattern(int count, int length, String dateFormat) {
            super(count, length);
            if (dateFormat != null) {
                this.dateFormat = new SimpleDateFormat(dateFormat);
            } else {
                this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            }

        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level) {
            return dateFormat.format(new Date());
        }

    }

    public static class LevelPattern extends Pattern {

        public LevelPattern(int count, int length) {
            super(count, length);
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level) {
            return level.toString();
        }
    }


    public static class LoggerPattern extends Pattern {

        private int loggerCount;
        private int loggerLength;

        public LoggerPattern(int count, int length, int loggerCount, int loggerLength) {
            super(count, length);
            this.loggerCount = loggerCount;
            this.loggerLength = loggerLength;
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level) {
            return Utils.shortenClassName(loggerName, loggerCount, loggerLength);
        }
    }

    public static class CallerPattern extends Pattern {

        private int callerCount;
        private int callerLength;

        public CallerPattern(int count, int length, int callerCount, int callerLength) {
            super(count, length);
            this.callerCount = callerCount;
            this.callerLength = callerLength;
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level) {
            if (caller == null) {
                throw new IllegalArgumentException("Caller not found");
            } else return Utils.shortenClassName(caller.toString(), callerCount, callerLength);
        }

        @Override
        protected boolean isCallerNeeded() {
            return true;
        }
    }

    public static class ConcatenatePattern extends Pattern {

        private final List<Pattern> patternList;

        public ConcatenatePattern(int count, int length, List<Pattern> patternList) {
            super(count, length);
            this.patternList = new ArrayList<Pattern>(patternList);
        }

        public void addPattern(Pattern pattern) {
            patternList.add(pattern);
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level) {
            StringBuilder builder = new StringBuilder();
            for (Pattern pattern : patternList) {
                builder.append(pattern.apply(caller, loggerName, level));
            }
            return builder.toString();
        }

        @Override
        protected boolean isCallerNeeded() {
            for (Pattern pattern : patternList) {
                if (pattern.isCallerNeeded()) {
                    return true;
                }
            }
            return false;
        }
    }

    private final int count;
    private final int length;

    private Pattern(int count, int length) {
        this.count = count;
        this.length = length;
    }

    public final String apply(StackTraceElement caller, String loggerName, Logger.Level level) {
        String string = doApply(caller, loggerName, level);
        return Utils.shorten(string, count, length);
    }

    protected abstract String doApply(StackTraceElement caller, String loggerName, Logger.Level level);

    protected boolean isCallerNeeded() {
        return false;
    }

    public static Pattern compile(String pattern) {
        try {
            return pattern == null ? null : new Compiler().compile(pattern);
        } catch (Exception e) {
            LoggerManager.getLogger(Logger.ROOT_LOGGER_NAME)
                    .e(e, "cannot parse pattern: '%s'", pattern);
            return new PlainPattern(0, 0, pattern);
        }

    }

    public static class Compiler {

        private String patternString;
        private int position;
        private List<ConcatenatePattern> queue;

        private final java.util.regex.Pattern PERCENT_PATTERN =
                java.util.regex.Pattern.compile("%%");
        private final java.util.regex.Pattern NEWLINE_PATTERN =
                java.util.regex.Pattern.compile("%n");
        private final java.util.regex.Pattern LEVEL_PATTERN =
                java.util.regex.Pattern.compile("%(\\d+)?(\\.(\\d+))?level");
        private final java.util.regex.Pattern LOGGER_PATTERN =
                java.util.regex.Pattern.compile("%(\\d+)?(\\.(\\d+))?logger(\\{(\\d+)?(\\.(\\d+))?\\})?");
        private final java.util.regex.Pattern CALLER_PATTERN =
                java.util.regex.Pattern.compile("%(\\d+)?(\\.(\\d+))?caller(\\{(\\d+)?(\\.(\\d+))?\\})?");
        private final java.util.regex.Pattern DATE_PATTERN =
                java.util.regex.Pattern.compile("%date(\\{(.*?)\\})?");
        private final java.util.regex.Pattern CONCATENATE_PATTERN =
                java.util.regex.Pattern.compile("%(\\d+)?(\\.(\\d+))?\\(");
        private final java.util.regex.Pattern DATE_PATTERN_SHORT =
                java.util.regex.Pattern.compile("%d(\\{(.*?)\\})?");
        private final java.util.regex.Pattern LEVEL_PATTERN_SHORT =
                java.util.regex.Pattern.compile("%(\\d+)?(\\.(\\d+))?p");
        private final java.util.regex.Pattern LOGGER_PATTERN_SHORT =
                java.util.regex.Pattern.compile("%(\\d+)?(\\.(\\d+))?c(\\{(\\d+)?(\\.(\\d+))?\\})?");
        private final java.util.regex.Pattern CALLER_PATTERN_SHORT =
                java.util.regex.Pattern.compile("%(\\d+)?(\\.(\\d+))?C(\\{(\\d+)?(\\.(\\d+))?\\})?");

        public Pattern compile(String string) {
            if (string == null) {
                return null;
            }

            this.position = 0;
            this.patternString = string;
            this.queue = new ArrayList<ConcatenatePattern>();
            queue.add(new ConcatenatePattern(0, 0, new ArrayList<Pattern>()));

            while (string.length() > position) {

                int index = string.indexOf("%", position);
                int bracketIndex = string.indexOf(")", position);
                if (queue.size() > 1 && bracketIndex < index) {
                    queue.get(queue.size() - 1).addPattern(new PlainPattern(0, 0, string.substring(position, bracketIndex)));
                    queue.get(queue.size() - 2).addPattern(queue.remove(queue.size() - 1));
                    position = bracketIndex + 1;
                }
                if (index == -1) {
                    queue.get(queue.size() - 1).addPattern(new PlainPattern(0, 0, string.substring(position)));
                    break;
                } else {
                    queue.get(queue.size() - 1).addPattern(new PlainPattern(0, 0, string.substring(position, index)));
                    position = index;
                    parse();
                }
            }

            return queue.get(0);
        }

        private void parse() {
            Matcher matcher;
            if ((matcher = findPattern(PERCENT_PATTERN)) != null) {
                queue.get(queue.size() - 1).addPattern(new PlainPattern(0, 0, "%"));
                position = matcher.end();
                return;
            }
            if ((matcher = findPattern(NEWLINE_PATTERN)) != null) {
                queue.get(queue.size() - 1).addPattern(new PlainPattern(0, 0, "\n"));
                position = matcher.end();
                return;
            }
            if ((matcher = findPattern(LEVEL_PATTERN)) != null || (matcher = findPattern(LEVEL_PATTERN_SHORT)) != null) {
                int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
                int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
                queue.get(queue.size() - 1).addPattern(new LevelPattern(count, length));
                position = matcher.end();
                return;
            }
            // the order is important because short logger pattern may match long caller occurrence
            if ((matcher = findPattern(CALLER_PATTERN)) != null || (matcher = findPattern(CALLER_PATTERN_SHORT)) != null) {
                int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
                int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
                int countCaller = Integer.parseInt(matcher.group(5) == null ? "0" : matcher.group(5));
                int lengthCaller = Integer.parseInt(matcher.group(7) == null ? "0" : matcher.group(7));
                queue.get(queue.size() - 1).addPattern(new CallerPattern(count, length, countCaller, lengthCaller));
                position = matcher.end();
                return;
            }
            if ((matcher = findPattern(LOGGER_PATTERN)) != null || (matcher = findPattern(LOGGER_PATTERN_SHORT)) != null) {
                int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
                int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
                int countLogger = Integer.parseInt(matcher.group(5) == null ? "0" : matcher.group(5));
                int lengthLogger = Integer.parseInt(matcher.group(7) == null ? "0" : matcher.group(7));
                queue.get(queue.size() - 1).addPattern(new LoggerPattern(count, length, countLogger, lengthLogger));
                position = matcher.end();
                return;
            }
            if ((matcher = findPattern(DATE_PATTERN)) != null || (matcher = findPattern(DATE_PATTERN_SHORT)) != null) {
                String dateFormat = matcher.group(2);
                queue.get(queue.size() - 1).addPattern(new DatePattern(0, 0, dateFormat));
                position = matcher.end();
                return;
            }
            if ((matcher = findPattern(CONCATENATE_PATTERN)) != null) {
                int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
                int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
                queue.add(new ConcatenatePattern(count, length, new ArrayList<Pattern>()));
                position = matcher.end();
                return;
            }

            throw new IllegalArgumentException();
        }

        private Matcher findPattern(java.util.regex.Pattern pattern) {
            Matcher matcher = pattern.matcher(patternString);
            return matcher.find(position) && matcher.start() == position ? matcher : null;
        }

    }

}


