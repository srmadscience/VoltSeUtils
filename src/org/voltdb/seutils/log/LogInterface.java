package org.voltdb.seutils.log;

/* This file is part of VoltDB.
 * Copyright (C) 2008-2017 VoltDB Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */


/**
* Interface for Logging used by OrindaSoft software. Can be implemented by customers so
* that OrindaSoft software can be fully integrated with the customer's application.
* This interface supports 5 types of messages
* <P> debug - Can be turned off
* <P> info - Generated during the course of normal operations
* <P> warning - Something strange has happened
* <P> error - Something bad has happened but the application is still working
* <P> systemError - Something that's really bad and is never supposed to happen has happened. The application will usually stop after one of these.
* <P>
* <P> Each message has two methods to call it - a simple method which has the message text as the sole parameter
* and a more detailed method which allows the developer to specify
* <p><code>isModal</code> - whether a modal window should come up
* <p><code>isLogged</code> - whether the message should be logged to a file or other persistant storage mechanism.
* Although present in the interface the above two features do not have to work in all implementations.
* <P> There are also a couple of housekeeping Methods that need to implemented
* <P> <code>flush()</code> - Flushes any log files being written to.
* <P> <code>getCurrentLogFile()</code> - Returns name of current log file.
* <P>
*/
public interface LogInterface
{

/**
* Constant used when formatting message strings
*/
public static final String DEBUG = "Debug";

/**
* Constant used when formatting message strings
*/
public static final String INFO = "Information";

/**
* Constant used when formatting message strings
*/
public static final String WARN = "Warning";

/**
* Constant used when formatting message strings
*/
public static final String ERROR = "Error";

/**
* Constant used when formatting message strings
*/
public static final String SYSERR = "System Error";

/**
* Constant for the time format used in log messages
*/
public static final String DEFAULT_TIME_FORMAT_STRING = "yyyy/MM/dd HH:mm:ss.S z";

/**
* Default format for day component of log file names.
*/
public static final String DEFAULT_FILENAME_DAY_FORMAT  = "yyyyMMdd";

/**
* Default format for time component of log file names.
*/
public static final String DEFAULT_FILENAME_TIME_FORMAT  = "HHmmss";

/**
* Constant for the field delimiter used in log messages
*/
public static final String DEFAULT_FIELD_DELIMITER = ":";

/**
* Turn debug messages on
*/
public void debugOn();
/**
* Turn debug messages off
*/
public void debugOff();

/**
* Get debug status
* @return <code>true</code> if debug messages are being printed.
*/
public boolean getDebug();

/**
* Log a debug message. The user does not have to see it. It will always be logged.
* @param String theMessage A string containing the message.
*/
public void debug(String theMessage);
/**
* Log a debug message. The user may be forced to acknowledge it. It may be logged.
* Debug messages are off by default.
* @param String theMessage A string containing the message.
* @param boolean isModal <code>true</code> if the user must be forced to acknowlege this message.
* Only meaningful if a GUI is in use.
* @param boolean isLogged <code>true</code> if this message is to be stored somewhere.
*/
public void debug(String theMessage, boolean isModal, boolean isLogged);
/**
* Log an informational message. The user does not have to acknowlege it. It will not be logged.
* @param String theMessage A string containing the message.
*/
public void info(String theMessage);
/**
* Log an informational  message. The user may be forced to acknowledge it. It may be logged.
* @param String theMessage A string containing the message.
* @param boolean isModal <code>true</code> if the user must be forced to acknowlege this message.
* Only meaningful if a GUI is in use.
* @param boolean isLogged <code>true</code> if this message is to be stored somewhere.
*/
public void info(String theMessage, boolean isModal, boolean isLogged);
/**
* Log a warning message. The user does not have to acknowlege it. It will not be logged.
* @param String theMessage A string containing the message.
*/
public void warning(String theMessage);
/**
* Log a warning message. The user may be forced to acknowledge it. It may be logged.
* @param String theMessage A string containing the message.
* @param boolean isModal <code>true</code> if the user must be forced to acknowlege this message.
* Only meaningful if a GUI is in use.
* @param boolean isLogged <code>true</code> if this message is to be stored somewhere.
*/
public void warning(String theMessage, boolean isModal, boolean isLogged);
/**
* Log an error message. The user does not have to acknowlege it. It will not be logged.
* @param String theMessage A string containing the message.
*/
public void error(String theMessage);
/**
* Log an error message. The user may be forced to acknowledge it. It may be logged.
* @param String theMessage A string containing the message.
* @param boolean isModal <code>true</code> if the user must be forced to acknowlege this message.
* Only meaningful if a GUI is in use.
* @param boolean isLogged <code>true</code> if this message is to be stored somewhere.
*/
public void error(String theMessage, boolean isModal, boolean isLogged);
/**
* Log an Exception. The user does not have to acknowlege it. It will not be logged.
* @param String theMessage A string containing the message.
*/
public void error(Exception theException);
/**
* Log an error message. The user may be forced to acknowledge it. It may be logged.
* @param String theMessage A string containing the message.
* @param boolean isModal <code>true</code> if the user must be forced to acknowlege this message.
* Only meaningful if a GUI is in use.
* @param boolean isLogged <code>true</code> if this message is to be stored somewhere.
*/
public void error(Exception theException, boolean isModal, boolean isLogged);
/**
* If the log is buffered then flush any outstanding changes. If the log is not
* buffered do nothing.
*/
/**
* Log a serious error message. The user does not have to acknowlege it. It will not be logged.
* @param String theMessage A string containing the message.
*/
public void syserror(String theMessage);
/**
* Log a serious error message. The user may be forced to acknowledge it. It may be logged.
* @param String theMessage A string containing the message.
* @param boolean isModal <code>true</code> if the user must be forced to acknowlege this message.
* Only meaningful if a GUI is in use.
* @param boolean isLogged <code>true</code> if this message is to be stored somewhere.
*/
public void syserror(String theMessage, boolean isModal, boolean isLogged);
/**
* Log a serious Exception. The user does not have to acknowlege it. It will not be logged.
* @param String theMessage A string containing the message.
*/
public void syserror(Exception theException);
/**
* Log a serious Exception. The user may be forced to acknowledge it. It may be logged.
* @param String theMessage A string containing the message.
* @param boolean isModal <code>true</code> if the user must be forced to acknowlege this message.
* Only meaningful if a GUI is in use.
* @param boolean isLogged <code>true</code> if this message is to be stored somewhere.
*/
public void syserror(Exception theException, boolean isModal, boolean isLogged);

/**
* Controls whether the class should flush the log every time it is asked to print a message. Slower but more reliable.
* @param boolean flushEveryMessage <code>true</code> if the log is to be flushed every time a message is written to it.
*/
public void setAutoFlush(boolean flushEveryMessage);


/**
* If the log is buffered then flush any outstanding changes. If the log is not
* buffered do nothing.
*/
public void flush();
/**
* Returns a string with the name of the current log.
* @return A full path name if a conventional log file is in use.
* @return A descriptive string if no log file is in use.
*/
public String getCurrentLog();
/**
* Controls whether the class should log every message unless told not to.
* @param boolean logEveryMessageByDefault <code>true</code> if messages are to be logged by default.
*/
public void setAutoLog(boolean logEveryMessageByDefault);

/**
* Returns the current Date Format
* @return The Date format
*/
public String getDateFormat();
}



