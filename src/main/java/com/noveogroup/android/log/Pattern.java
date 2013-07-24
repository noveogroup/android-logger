package com.noveogroup.android.log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

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
            this.dateFormat = new SimpleDateFormat(dateFormat);
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
        // todo throw IllegalArgumentException when caller == null for CallerPattern
        String string = doApply(caller, loggerName, level);
        return Utils.shorten(string, count, length);
    }

    protected abstract String doApply(StackTraceElement caller, String loggerName, Logger.Level level);

    protected boolean isCallerNeeded() {
        return false;
    }

    public static class Compiler {

        private String patternString;
        private int position;
        private List<ConcatenatePattern> queue;

        private final String PERCENT_REGEX = "%%";
        private final String NEWLINE_REGEX = "%n";
        private final String LEVEL_REGEX = "%(\\d+)?(\\.(\\d+))?level";
        private final String LOGGER_REGEX = "%(\\d+)?(\\.(\\d+))?logger(\\{(\\d+)?(\\.(\\d+))?\\})?";
        private final String CALLER_REGEX = "%(\\d+)?(\\.(\\d+))?caller(\\{(\\d+)?(\\.(\\d+))?\\})?";
        private final String DATE_REGEX = "date(\\{(.*?)\\})?";
        private final String CONCAT_REGEX  = "%(\\d+)?(\\.(\\d+))?(";

        public Pattern compile(String string) {
            position = 0;
            queue = new ArrayList<ConcatenatePattern>();
            queue.add(new ConcatenatePattern(0, 0, new ArrayList<Pattern>()));

            while (string.length() > position) {
                int index = string.indexOf("%", position);
                if (index == -1) {
                    queue.get(queue.size() - 1).addPattern(new PlainPattern(0, 0, string.substring(position)));
                    break;
                } else {
                    queue.get(queue.size() - 1).addPattern(new PlainPattern(0, 0, string.substring(position, index)));
                    position = index;
                    parse(string);
                }
            }

            return queue.get(0);
        }

        private void parse(String string) {

            Matcher matcher = java.util.regex.Pattern.compile(PERCENT_REGEX).matcher(string);
            if (matcher.find(position)) {
                queue.get(queue.size() - 1).addPattern(new PlainPattern(0, 0, "%"));
                position = matcher.end();
                return;
            }
            matcher = java.util.regex.Pattern.compile(NEWLINE_REGEX).matcher(string);
            if (matcher.find(position)) {
                queue.get(queue.size() - 1).addPattern(new PlainPattern(0, 0, "\n"));
                position = matcher.end();
                return;
            }
            matcher = java.util.regex.Pattern.compile(LEVEL_REGEX).matcher(string);
            if (matcher.find(position)) {
                int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
                int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
                queue.get(queue.size() - 1).addPattern(new LevelPattern(count, length));
                position = matcher.end();
                return;
            }
            matcher = java.util.regex.Pattern.compile(LOGGER_REGEX).matcher(string);
            if (matcher.find(position)) {
                int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
                int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
                int countLogger = Integer.parseInt(matcher.group(5) == null ? "0" : matcher.group(5));
                int lengthLogger = Integer.parseInt(matcher.group(7) == null ? "0" : matcher.group(7));
                queue.get(queue.size() - 1).addPattern(new LoggerPattern(count, length, countLogger, lengthLogger));
                position = matcher.end();
                return;
            }
            matcher = java.util.regex.Pattern.compile(CALLER_REGEX).matcher(string);
            if (matcher.find(position)) {
                int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
                int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
                int countCaller = Integer.parseInt(matcher.group(5) == null ? "0" : matcher.group(5));
                int lengthCaller = Integer.parseInt(matcher.group(7) == null ? "0" : matcher.group(7));
                queue.get(queue.size() - 1).addPattern(new CallerPattern(count, length, countCaller, lengthCaller));
                position = matcher.end();
                return;
            }
            matcher = java.util.regex.Pattern.compile(DATE_REGEX).matcher(string);
            if (matcher.find(position)) {
                String dateFormat = matcher.group(2);
                queue.get(queue.size() - 1).addPattern(new DatePattern(0, 0, dateFormat));
                position = matcher.end();
                return;
            }


            throw new IllegalArgumentException();
        }


        // "date({(.*?)})?"
       /* Matcher matcher = java.util.regex.Pattern.compile("^(1(\\d+))asd").matcher("123asd asd asjdhgasjhdgj");
        if (matcher.find()) {
            matcher.group(2);
        }*/

    }

}


