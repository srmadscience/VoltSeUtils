package org.voltdb.seutils.log;

import org.voltdb.seutils.utils.IOUtils;

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


public class ConsoleLog extends GenericLog implements LogInterface
{
  /**
  * Waiting for input message text
  */
  protected static final String PRESS_ENTER = "Press Enter to continue...";
  
  /**
  * Create an instance of ConsoleLog.
  */
  public ConsoleLog()
    {
    super();
    }

  /**
  * Format a message and write it to either Standard Output(INFO messages) or Standard Error (eEverything else)
  * @param String messageType should be one of the constants DEBUG, INFO, WARN, ERROR or SYSERR as defined in LogInterface.
  * @param String messageText The message
  * @param boolean isModal ignored as we are dumping messages to the console.
  * @param boolean isLogged ignored as we are dumping messages to the console.
  */
  protected synchronized void writeMessage(String messageType, String messageText, boolean isModal, boolean isLogged)
    {
    String outputMessage = formatMessage(messageType,messageText);

    if (messageType.equals(LogInterface.INFO))
      {
      System.out.println(outputMessage);
      }
    else
      {
      System.err.println(outputMessage);
      }

    // If this is a modal message print a message and wait for input...
    if (isModal)
      {
      IOUtils.getStringFromConsole(PRESS_ENTER, true);
      }

    }

  /**
  * This is required by <code>LogInterface</code> but is not used in the implementation.
  */
  public void setAutoFlush(boolean flushEveryMessage)
    {
    }
    
  /**
  * This is required by <code>LogInterface</code> but is not used in the implementation.
  */
  public void flush()
    {
    }

  /**
  * This is required by <code>LogInterface</code> but is not used in the implementation.
  */
  public String getCurrentLog()
    {
    return(this.getClass().getName());
    }
}


