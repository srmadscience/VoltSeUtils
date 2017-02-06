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


import java.util.Date;
import java.text.SimpleDateFormat;

/**
* Partial implementation of LogInterface that composes error messages but leaves the actual
* logging to other classes.
*
*/
public abstract class GenericLog implements LogInterface
{

  /**
  * "Log file being flushed" message
  */
  protected static final String LOG_BEING_FLUSHED = "Log File Flush Request Received";

  /**
  * SimpleDateFormat used in the message formatting process.
  */
  protected static java.text.SimpleDateFormat theDateFormat;

  /**
  * Flag to indicate whether we are printing debug messages. Defaults to <code>false</code>.
  */
  protected static boolean debugMessagesPrinted = false;

  /**
  * Flag to indicate whether messages should be logged by default. Defaults to <code>true</code>.
  */
  protected boolean autoLog = true;

  /**
  * Flag to indicate whether we should call the <code>flush()</code> method every time we print a message. Defaults to <code>false</code>.
  * @see GenericLog#flush
  */
  protected boolean autoFlush = false;

  /**
  * Flag to indicate whether we should include the Thread id every time we print a message. Defaults to <code>false</code>.
  */
  protected boolean printThreadId = false;

  /**
  * Create an instance of GenericLog.
  */
  public GenericLog()
    {
    theDateFormat = new SimpleDateFormat (LogInterface.DEFAULT_TIME_FORMAT_STRING);
    }

  /**
  * Return date format string
  */
  public String getDateFormat()
    {
    return(theDateFormat.toPattern());
    }

  /**
  * Turn debug messages on
  */
  public void debugOn()
    {
    debugMessagesPrinted = true;
    }

  /**
  * Turn debug messages off
  */
  public void debugOff()
    {
    debugMessagesPrinted = false;
    }

  /**
  * Get debug status
  * @return <code>true</code> if debug messages are being printed.
  */
  public boolean getDebug()
    {
    return (debugMessagesPrinted);
    }

  public void debug(String theMessage)
    {
    debug(theMessage, false, autoLog);
    }

  public void debug(String theMessage, boolean isModal, boolean isLogged)
    {
    // Only generate message if we are supposed to...
    if (debugMessagesPrinted)
      {
      writeMessage(LogInterface.DEBUG, theMessage, isModal, isLogged);
      }
    }

  public void info(String theMessage)
    {
    info(theMessage, false, autoLog);
    }

  public void info(String theMessage, boolean isModal, boolean isLogged)
    {
    writeMessage(LogInterface.INFO, theMessage, isModal, isLogged);
    }

  public void warning(String theMessage)
    {
    warning(theMessage, false, autoLog);
    }

  public void warning(String theMessage, boolean isModal, boolean isLogged)
    {
    writeMessage(LogInterface.WARN, theMessage, isModal, isLogged);
    }

  public void error(String theMessage)
    {
    error(theMessage, false, autoLog);
    }

  public void error(String theMessage, boolean isModal, boolean isLogged)
    {
    writeMessage(LogInterface.ERROR, theMessage, isModal, isLogged);
    }

  public void error(Exception theException)
    {
    error(theException, false, autoLog);
    }

  public void error(Exception theException, boolean isModal, boolean isLogged)
    {
    writeMessage(LogInterface.ERROR, theException.getMessage(), isModal, isLogged);
    }
  public void syserror(String theMessage)
    {
    syserror(theMessage, false, autoLog);
    flush();
    }

  public void syserror(String theMessage, boolean isModal, boolean isLogged)
    {
    writeMessage(LogInterface.SYSERR, theMessage, isModal, isLogged);
    }

  public void syserror(Exception theException)
    {
    syserror(theException, false, autoLog);
    flush();
    }

  public void syserror(Exception theException, boolean isModal, boolean isLogged)
    {
    writeMessage(LogInterface.ERROR, theException.getMessage(), isModal, isLogged);
    }

  public abstract void flush();

  public abstract String getCurrentLog();

  /**
  * Format a message and return it as a printable string
  * @param String messageType should be one of the constants DEBUG, INFO, WARN, ERROR or SYSERR as defined in LogInterface.
  * @param String messageText The message
  * @return String a formatted message
  */
  protected String formatMessage(String messageType, String messageText)
    {
    Date tempDate = new Date(System.currentTimeMillis());
    if (printThreadId)
      {
      return(theDateFormat.format(tempDate) + LogInterface.DEFAULT_FIELD_DELIMITER
                        + messageType
                     //REMOVEFORJDK1.5   + " ["+Thread.currentThread().getId()+"] "
                        + LogInterface.DEFAULT_FIELD_DELIMITER
                        + messageText);
      }

    return(theDateFormat.format(tempDate) + LogInterface.DEFAULT_FIELD_DELIMITER
                        + messageType + LogInterface.DEFAULT_FIELD_DELIMITER
                        + messageText);
    }

  /**
  * Controls whether the class should log every message unless told not to.
  * @param boolean logEveryMessageByDefault <code>true</code> if messages are to be logged by default.
  */
  public void setAutoLog(boolean logEveryMessageByDefault)
    {
    autoLog = logEveryMessageByDefault;
    }

  /**
  * Controls whether the class should flush the log every time it is asked to print a message. Slower but more reliable.
  * @param boolean flushEveryMessage <code>true</code> if the log is to be flushed every time a message is written to it.
  */
  public void setAutoFlush(boolean flushEveryMessage)
    {
    autoFlush = flushEveryMessage;
    }

  /**
  * Format a message and write it to whatever it is we are writing to.
  * @param String messageType should be one of the constants DEBUG, INFO, WARN, ERROR or SYSERR as defined in LogInterface.
  * @param String messageText The message
  */
  protected abstract void writeMessage(String messageType, String messageText, boolean isModal, boolean isLogged);

  }




