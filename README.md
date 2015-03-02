Android Logger
==============

Useful logger for Android based on standard android.util.Log class.
Simple lightweight (< 50 Kb) implementation of SLF4J API. Easy but powerful
configuration via properties file and some additional helpful logging methods.
Easy analogue of popular log4j library.

__Android SDK Version__: API 7 [ Android 2.1 ]

__Code Samples__: [[here]](https://github.com/noveogroup/android-logger/tree/sample)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-android--logger-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1052)

Downloads
---------

 - [android-logger-1.3.5.jar](http://search.maven.org/remotecontent?filepath=com/noveogroup/android/android-logger/1.3.5/android-logger-1.3.5.jar)
 - [android-logger-1.3.5-sources.jar](http://search.maven.org/remotecontent?filepath=com/noveogroup/android/android-logger/1.3.5/android-logger-1.3.5-sources.jar)
 - [android-logger-1.3.5-javadoc.jar](http://search.maven.org/remotecontent?filepath=com/noveogroup/android/android-logger/1.3.5/android-logger-1.3.5-javadoc.jar)

Maven Dependency
----------------
```xml
<dependency>
    <groupId>com.noveogroup.android</groupId>
    <artifactId>android-logger</artifactId>
    <version>1.3.5</version>
</dependency>
```

Gradle Dependency
-----------------
```groovy
'com.noveogroup.android:android-logger:1.3.5'
```

Getting Started
---------------

If you want to use Android Logger in your Android application you need to do
just the following simple steps:

 - Add Android Logger as a library OR add it as Maven or Gradle dependency.

 - Configure Android Logger.

Place the following android-logger.properties file to your source directory (src/main/resources/android-logger.properties):

```properties
# Android Logger configuration example

# By default logger will print only ERROR (and higher) messages
# with "MyApplication" tag
root=ERROR:MyApplication

# DEBUG (and higher) messages from classes of com.example.database
# will be logged with "MyApplication-Database" tag
logger.com.example.database=DEBUG:MyApplication-Database

# All messages from classes of com.example.ui will be logged with
# "MyApplication-UI" tag
logger.com.example.ui=MyApplication-UI
```

The configuration manages which log tag will be used to print messages and
which logging level filter will be applied.

```properties
logger.<package/classname>=<level>:<tag>:<message head>
# or
logger.<package/classname>=<level>:<tag>
# or
logger.<package/classname>=<tag>
```

The rest of messages will be managed by root logger:

```properties
root=<level>:<tag>:<message head>
# or
root=<level>:<tag>
# or
root=<tag>
```

You can use VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT as level in
configuration files.

Format of <tag> and <message head> is desribed below in [Patterns](#patterns) section.

 - You need to get logger instance to print messages

You can use LoggerManager to get a logger instance to print messages.

```java
package com.example.ui;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

public class MainActivity extends Activity {

  // get a logger instance by name
  private static final Logger logger = LoggerManager.getLogger("com.example.ui.MyActivity");
  // get a logger instance by class
  private static final Logger logger = LoggerManager.getLogger(MainActivity.class);
  // just to use current class
  private static final Logger logger = LoggerManager.getLogger();

  private void foo(int value) {
    logger.i("entered MainActivity::foo value=%d", value);
    
    try {
      // some code
    } catch(IOException e) {
      logger.e("I/O error occurred", e);
    }
  }

}
```

 - You can use Log class to make logging calls shorter.

Any call like Log.someMethod() is equal to LoggerManager.getLogger().someMethod().
So, there will be some additional overhead to get a logger each time.

```java
import com.noveogroup.android.log.Log;

public class Bar {

  public void foo() {
    Log.i("some log message");
  }

}
```

### Patterns

You can configure format of tags and messages headers in your configuration file.

For example, the following configuration file:

```properties
logger.ROOT=INFO:MyApplication:%caller{-2}
```

will generate the following output:

```text
PatternTest#<init>:15 your message
```

So, the logger insert inforation about caller before the message.

The patterns are format strings written according to a special rules described
below. Log messages will be formatted and printed as it is specified in
the tag and the message pattern. The tag pattern configures log tag used
to print messages. The message pattern configures a head of the message but
not whole message printed to log.

| The tag pattern | The message pattern | Resulting tag | Resulting message                  |
|-----------------|---------------------|---------------|------------------------------------|
| TAG             | %d{yyyy-MM-dd}:     | TAG           | %d{yyyy-MM-dd}: <incoming message> |

The tag and the message patterns are wrote according to similar rules.
So we will show only one pattern in further examples.

The patterns is strings that contains a set of placeholders and other
special marks. Each special mark should start with '%' sign. To escape
this sign you can double it.

#### Conversion marks

##### Mark %%

Escapes special sign. Prints just one '%' instead.

##### Mark %n

Prints a new line character '\n'.

##### Marks %d{date format} and %date{date format}

Prints date/time of a message. Date format should be supported by SimpleDateFormat.
Default date format is "yyyy-MM-dd HH:mm:ss.SSS".

##### Marks %p and %level

Prints logging level of a message.

##### Marks %c{count.length} and %logger{count.length}

Prints a name of the logger. The algorithm will shorten some part of
full logger name to the specified length. You can find examples below.

| Conversion specifier | Logger name                               | Result                           |
|----------------------|-------------------------------------------|----------------------------------|
| %logger              | com.example.android.MainActivity          | com.example.android.MainActivity |
| %logger{0}           | com.example.android.MainActivity          | com.example.android.MainActivity |
| %logger{3}           | com.example.android.MainActivity          | com.example.android              |
| %logger{-1}          | com.example.android.MainActivity          | example.android.MainActivity     |
| %logger{.0}          | com.example.android.MainActivity          | com.example.android.MainActivity |
| %logger{.30}         | com.example.android.MainActivity          | com.example.android.*            |
| %logger{.15}         | com.example.android.MainActivity          | com.example.*                    |
| %logger{.-25}        | com.example.android.MainActivity          | *.android.MainActivity           |
| %logger{3.-18}       | com.example.android.MainActivity          | *.example.android                |
| %logger{-3.-10}      | com.example.android.MainActivity$SubClass | MainActivity$SubClass            |

##### Marks %C{count.length} and %caller{count.length}

Prints information about a caller class which causes the logging event.
Additional parameters 'count' and 'length' means the same as the parameters of %logger.

Examples:

| Conversion specifier | Caller                                             | Result                               |
|----------------------|----------------------------------------------------|--------------------------------------|
| %caller              | Class com.example.android.MainActivity at line 154 | com.example.android.MainActivity:154 |
| %caller{-3.-15}      | Class com.example.android.MainActivity at line 154 | MainActivity:154                     |

##### Marks %s and %source

Prints source of class which causes the logging event.

Examples:

| Conversion specifier | Caller                                             | Result                  |
|----------------------|----------------------------------------------------|-------------------------|
| %source or %s        | Class com.example.android.MainActivity at line 154 | (MainActivity.java:154) |
| %source or %s        | Native                                             | (native)                |
| %source or %s        | Unknown                                            | (unknown)               |

##### Marks %t and %thread

Prints a name of the thread which causes the logging event.

##### Mark %(...)

Special mark used to grouping parts of message.
Format modifiers (if specified) are applied on whole group.

Examples:

| Example                    | Result                                               |
|----------------------------|------------------------------------------------------|
| [%50(%d %caller{-3.-15})]  | [          2013-07-12 19:45:26.315 MainActivity:154] |
| [%-50(%d %caller{-3.-15})] | [2013-07-12 19:45:26.315 MainActivity:154          ] |

#### Format modifiers

After special sign '%' user can add format modifiers. The modifiers
is similar to standard modifiers of Formatter conversions.

| Example    | Result   |
|------------|----------|
| %6(text)   | '  text' |
| %-6(text)  | 'text  ' |
| %.3(text)  | 'tex'    |
| %.-3(text) | 'ext'    |

For an additional information see sources of *com.noveogroup.android.log.PatternHandler* class

SLF4J compatibility
-------------------

Android Logger is SLF4J compatible. For example, you can write such code in
your library:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bar {

  private static final Logger logger = LoggerFactory.getLogger(Bar.class);

  public void foo(int value) {
    logger.info("entered Bar::foo value={}", value);

    try {
      // some code
    } catch(IOException e) {
      logger.error("I/O error occurred", e);
    }
  }

}
```

Suppose you compiled your library as JAR-file and publish it. After that anyone
who uses your JAR library will be able to add any SLF4J implementation to
change the way how the library logs messages.
The most powerful implementation of SLF4J is [LOGBack](http://logback.qos.ch/)
available for Android. Unfortunately, it has about 1 Mb size and it may be
critical for some Android applications.
Android Logger is SLF4J compatible too. So you can just add its JAR as
a library to get all your log messages in Android LogCat.

Developed By
============

* [Noveo Group][1]
* [Pavel Stepanov](https://github.com/stefan-nsk) - <pstepanov@noveogroup.com>
* [Margarita Kurushina](https://github.com/mymargaret) - <mkurushina@noveogroup.com>

License
=======

    Copyright (c) 2013 Noveo Group

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    Except as contained in this notice, the name(s) of the above copyright holders
    shall not be used in advertising or otherwise to promote the sale, use or
    other dealings in this Software without prior written authorization.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
    THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.

[1]: http://noveogroup.com/
