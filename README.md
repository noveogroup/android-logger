Android Logger
==============

Useful logger for Android based on standard android.util.Log class.
Simple lightweight (< 50 Kb) implementation of SLF4J API. Easy but powerful
configuration via properties file and some additional helpful logging methods.
Easy analogue of popular log4j library.

__Android SDK Version__: API 7 [ Android 2.1 ]

__Code Samples__: [[here]](https://github.com/noveogroup/android-logger/tree/sample)

Downloads
---------

 - [android-logger-1.2.2.jar](https://github.com/noveogroup/android-logger/blob/gh-downloads/android-logger-1.2.2.jar?raw=true)
 - [android-logger-1.2.2-sources.jar](https://github.com/noveogroup/android-logger/blob/gh-downloads/android-logger-1.2.2-sources.jar?raw=true)
 - [android-logger-1.2.2-javadoc.jar](https://github.com/noveogroup/android-logger/blob/gh-downloads/android-logger-1.2.2-javadoc.jar?raw=true)

[Previous versions](https://github.com/noveogroup/android-logger/tree/gh-downloads)

Maven Dependency
----------------

    <dependency>
        <groupId>com.noveogroup.android</groupId>
        <artifactId>android-logger</artifactId>
        <version>1.2.2</version>
    </dependency>

Gradle Dependency
-----------------

    'com.noveogroup.android:android-logger:1.2.2'

Forks
-----

 - [jromero/android-logger](https://github.com/jromero/android-logger).
   Logging tags can be generated automatically using logger name.

Getting Started
---------------

If you want to use Android Logger in your Android application you need to do
just the following simple steps:

 - Add Android Logger as a library OR add it as Maven or Gradle dependency.

 - Configure Android Logger.

Place the following android-logger.properties file to your source directory (src/android-logger.properties):

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

The configuration manages which log tag will be used to print messages and
which logging level filter will be applied.


    logger.<package/classname>=<level>:<tag>:<message head>
    # or
    logger.<package/classname>=<level>:<tag>
    # or
    logger.<package/classname>=<tag>

The rest of messages will be managed by root logger:

    root=<level>:<tag>:<message head>
    # or
    root=<level>:<tag>
    # or
    root=<tag>

You can use VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT as level in
configuration files.

 - You need to get logger instance to print messages

You can use LoggerManager to get a logger instance to print messages.

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

 - You can use Log class to make logging calls shorter.

Any call like Log.someMethod() is equal to LoggerManager.getLogger().someMethod().
So, there will be some additional overhead to get a logger each time.

    import static com.noveogroup.android.log.Log;

    public class Bar {

      public void foo() {
        Log.i("some log message");
      }

    }

SLF4J compatibility
-------------------

Android Logger is SLF4J compatible. For example, you can write such code in
your library:

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
* Pavel Stepanov - <pstepanov@noveogroup.com>
* Margarita Kurushina - <mkurushina@noveogroup.com>

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
