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

import java.text.SimpleDateFormat;
import java.util.Formatter;

/**
 * The basic implementation of {@link Handler} interface.
 * <p/>
 * This log handler is configured with a logging level, a tag and
 * a message patterns.
 * <p/>
 * The logging level parameter is the minimal level of log messages printed
 * by this handler instance. The logging level can be {@code null} which
 * means no messages should be printed using this logger.
 * <p/>
 * <b>Attention</b>: Android may set its own requirement for logging level
 * using {@link Log#isLoggable(String, int)} method. This logger doesn't take
 * it into account in {@link #isEnabled(Logger.Level)} method.
 * <p/>
 * The patterns are format strings written according to a special rules described
 * below. Log messages will be formatted and printed as it is specified in
 * the tag and the message pattern. The tag pattern configures log tag used
 * to print messages. The message pattern configures a head of the message but
 * not whole message printed to log.
 * <p/>
 * <table border=1>
 * <tr>
 * <th>The tag pattern</th>
 * <td>TAG</td>
 * </tr>
 * <tr>
 * <th>The message pattern</th>
 * <td>%d{yyyy-MM-dd}: </td>
 * </tr>
 * <tr>
 * <th>Resulting tag</th>
 * <td>TAG</td>
 * </tr>
 * <tr>
 * <th>Resulting message</th>
 * <td>2013-07-12: &lt;incoming message&gt;</td>
 * </tr>
 * </table>
 * <p/>
 * The tag and the message patterns are wrote according to similar rules.
 * So we will show only one pattern in further examples.
 * <p/>
 * The patterns is strings that contains a set of placeholders and other
 * special marks. Each special mark should start with '%' sign. To escape
 * this sign you can double it.
 * <p/>
 * The list of special marks and placeholders:
 * <table border=1>
 * <caption>Conversion marks</caption>
 * <tr>
 * <th>Mark</th><th>Effect</th>
 * </tr>
 * <tr>
 * <td>%%</td>
 * <td>Escapes special sign. Prints just one '%' instead.</td>
 * </tr>
 * <tr>
 * <td>%n</td>
 * <td>Prints a new line character '\n'.</td>
 * </tr>
 * <tr>
 * <td>%d{date format} %date{date format}</td>
 * <td>Prints date/time of a message. Date format
 * should be supported by {@link SimpleDateFormat}.
 * Default date format is "yyyy-MM-dd HH:mm:ss.SSS".</td>
 * </tr>
 * <tr>
 * <td>%p %level</td>
 * <td>Prints logging level of a message.</td>
 * </tr>
 * <tr>
 * <td>%c{count.length} %logger{count.length}</td>
 * <td>Prints a name of the logger. The algorithm will shorten some part of
 * full logger name to the specified length. You can find examples below.
 * <table border=1>
 * <tr>
 * <th>Conversion specifier</th>
 * <th>Logger name</th>
 * <th>Result</th>
 * </tr>
 * <tr>
 * <td>%logger</td>
 * <td>com.example.android.MainActivity</td>
 * <td>com.example.android.MainActivity</td>
 * </tr>
 * <tr>
 * <td>%logger{0}</td>
 * <td>com.example.android.MainActivity</td>
 * <td>com.example.android.MainActivity</td>
 * </tr>
 * <tr>
 * <td>%logger{3}</td>
 * <td>com.example.android.MainActivity</td>
 * <td>com.example.android</td>
 * </tr>
 * <tr>
 * <td>%logger{-1}</td>
 * <td>com.example.android.MainActivity</td>
 * <td>example.android.MainActivity</td>
 * </tr>
 * <tr>
 * <td>%logger{.0}</td>
 * <td>com.example.android.MainActivity</td>
 * <td>com.example.android.MainActivity</td>
 * </tr>
 * <td>%logger{.30}</td>
 * <td>com.example.android.MainActivity</td>
 * <td>com.example.android.*</td>
 * </tr>
 * <tr>
 * <td>%logger{.15}</td>
 * <td>com.example.android.MainActivity</td>
 * <td>com.example.*</td>
 * </tr>
 * <tr>
 * <td>%logger{.-25}</td>
 * <td>com.example.android.MainActivity</td>
 * <td>*.android.MainActivity</td>
 * </tr>
 * <tr>
 * <td>%logger{3.-18}</td>
 * <td>com.example.android.MainActivity</td>
 * <td>*.example.android</td>
 * </tr>
 * <tr>
 * <td>%logger{-3.-10}</td>
 * <td>com.example.android.MainActivity$SubClass</td>
 * <td>MainActivity$SubClass</td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * <tr>
 * <td>%C{count.length} %caller{count.length}</td>
 * <td>Prints information about a caller class which causes the logging event.
 * Additional parameters 'count' and 'length' means the same as
 * the parameters of %logger. Examples:
 * <table border=1>
 * <tr>
 * <th>Conversion specifier</th>
 * <th>Caller</th>
 * <th>Result</th>
 * </tr>
 * <tr>
 * <td>%caller</td>
 * <td>Class com.example.android.MainActivity at line 154</td>
 * <td>com.example.android.MainActivity:154</td>
 * </tr>
 * <tr>
 * <td>%caller{-3.-15}</td>
 * <td>Class com.example.android.MainActivity at line 154</td>
 * <td>MainActivity:154</td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * <tr>
 * <td>%s{count.length} %source{count.length}</td>
 * <td>Prints source of class which causes the logging event. Examples:
 * <table border=1>
 * <tr>
 * <th>Conversion specifier</th>
 * <th>Caller</th>
 * <th>Result</th>
 * </tr>
 * <tr>
 * <td>%source or %s</td>
 * <td>Class com.example.android.MainActivity at line 154</td>
 * <td>(MainActivity.java:154)</td>
 * </tr>
 * <tr>
 * <td>%source or %s</td>
 * <td>Native</td>
 * <td>(native)</td>
 * </tr>
 * <tr>
 * <td>%source or %s</td>
 * <td>Unknown</td>
 * <td>(unknown)</td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * <tr>
 * <td>%t{count.length} %thread{count.length}</td>
 * <td>Prints a name of the thread which causes the logging event.</td>
 * </tr>
 * <tr>
 * <td>%(...)</td>
 * <td>Special mark used to grouping parts of message. Format modifiers
 * (if specified) are applied on whole group. Examples:
 * <table border=1>
 * <tr>
 * <th>Example</th>
 * <th>Result</th>
 * </tr>
 * <tr>
 * <td>[%50(%d %caller{-3.-15})]</td>
 * <td><pre>[          2013-07-12 19:45:26.315 MainActivity:154]</pre></td>
 * </tr>
 * <tr>
 * <td>[%-50(%d %caller{-3.-15})]</td>
 * <td><pre>[2013-07-12 19:45:26.315 MainActivity:154          ]</pre></td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * </table>
 * <p/>
 * After special sign '%' user can add format modifiers. The modifiers
 * is similar to standard modifiers of {@link Formatter} conversions.
 * <table border=1>
 * <tr>
 * <th>Example</th>
 * <th>Result</th>
 * </tr>
 * <tr> <td>%6(text)</td>   <td><pre>'  text'</pre></td> </tr>
 * <tr> <td>%-6(text)</td>  <td><pre>'text  '</pre></td> </tr>
 * <tr> <td>%.3(text)</td>  <td><pre>'tex'</pre></td>    </tr>
 * <tr> <td>%.-3(text)</td> <td><pre>'ext'</pre></td>    </tr>
 * </table>
 */
public class PatternHandler implements Handler {

    private final Logger.Level level;
    private final String tagPattern;
    private final String messagePattern;
    private final Pattern compiledTagPattern;
    private final Pattern compiledMessagePattern;

    /**
     * Creates new {@link PatternHandler}.
     *
     * @param level          the level.
     * @param tagPattern     the tag pattern.
     * @param messagePattern the message pattern.
     */
    public PatternHandler(Logger.Level level, String tagPattern, String messagePattern) {
        this.level = level;
        this.tagPattern = tagPattern;
        this.compiledTagPattern = Pattern.compile(tagPattern);
        this.messagePattern = messagePattern;
        this.compiledMessagePattern = Pattern.compile(messagePattern);
    }

    /**
     * Returns the level.
     *
     * @return the level.
     */
    public Logger.Level getLevel() {
        return level;
    }

    /**
     * Returns the tag messagePattern.
     *
     * @return the tag messagePattern.
     */
    public String getTagPattern() {
        return tagPattern;
    }

    /**
     * Returns the message messagePattern.
     *
     * @return the message messagePattern.
     */
    public String getMessagePattern() {
        return messagePattern;
    }

    @Override
    public boolean isEnabled(Logger.Level level) {
        return this.level != null && level != null && this.level.includes(level);
    }

    @Override
    public void print(String loggerName, Logger.Level level,
                      Throwable throwable, String message) throws IllegalArgumentException {
        if (isEnabled(level)) {
            String messageBody;

            if (message == null) {
                if (throwable == null) {
                    messageBody = "";
                } else {
                    messageBody = Log.getStackTraceString(throwable);
                }
            } else {
                if (throwable == null) {
                    messageBody = message;
                } else {
                    messageBody = message + '\n' + Log.getStackTraceString(throwable);
                }
            }

            StackTraceElement caller = null;
            if ((compiledTagPattern != null && compiledTagPattern.isCallerNeeded())
                    || (compiledMessagePattern != null && compiledMessagePattern.isCallerNeeded())) {
                caller = Utils.getCaller();
            }

            String tag = compiledTagPattern == null ? "" : compiledTagPattern.apply(caller, loggerName, level);
            String messageHead = compiledMessagePattern == null ? "" : compiledMessagePattern.apply(caller, loggerName, level);

            if (messageHead.length() > 0 && !Character.isWhitespace(messageHead.charAt(0))) {
                messageHead = messageHead + " ";
            }
            Log.println(level.intValue(), tag, messageHead + messageBody);
        }
    }

    @Override
    public void print(String loggerName, Logger.Level level,
                      Throwable throwable, String messageFormat, Object... args) throws IllegalArgumentException {
        if (isEnabled(level)) {
            if (messageFormat == null && args != null && args.length > 0) {
                throw new IllegalArgumentException("message format is not set but arguments are presented");
            }

            print(loggerName, level, throwable, messageFormat == null ? null : String.format(messageFormat, args));
        }
    }

}
