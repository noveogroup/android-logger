Android Logger
==============

Useful logger for Android based on standard android.util.Log class.
Simple lightweight (< 50 Kb) implementation of SLF4J API. Easy but powerful
configuration via properties file and some additional helpful logging methods.
Easy analogue of popular log4j library.

Getting Started
---------------

If you want to use Android Logger in your Android application you need to do
just the following simple steps:

1. Add Android Logger as a library.

2. You need to configure Android Logger

3. You need to get logger instance to print messages

TODO add an example

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

Known Issues
============

1. ...

Developed By
============

* [Noveo Group][1]
* Pavel Stepanov - <pstepanov@noveogroup.com>

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
