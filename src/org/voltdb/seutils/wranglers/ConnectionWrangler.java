package org.voltdb.seutils.wranglers;

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

import java.sql.*;
import java.util.Properties;

import org.voltdb.seutils.log.LogInterface;
import org.voltdb.seutils.utils.CSException;

import com.timesten.jdbc.TimesTenDataSource;

import oracle.jdbc.driver.*;
import java.net.InetAddress;

/***
 * This class handles connecting to Oracle, TimesTen and provides various utility Methods.
 */
public class ConnectionWrangler {
	/**
	 * TCP/IP hostname of server
	 */
	public String mrHostName;

	/**
	 * TCP/IP IP Address of server
	 */
	public String mrIpAddress;

	/**
	 * TCP/IP Port in use by Sql*Net
	 */
	public int mrPort;

	/**
	 * Database Instance Name
	 */
	public String mrSid;

	/**
	 * OCI Connect URL
	 */
	public String mrURL;

	/**
	 * Database User
	 */
	public String mrUser;

	/**
	 * Database Password
	 */
	public String mrPassword;

	/**
	 * Log Object
	 */
	public LogInterface mrLog;

	/**
	 * Oracle connection
	 */
	public Connection mrConnection;

	/**
	 * How many times we have been used since we last logged in
	 **/
	int usageCounter = 0;

	/**
	 * Timestamp for last DB activity
	 */
	java.util.Date usageDatetime = new java.util.Date();

	/**
	 * Flag to show whether we are currently connected or not
	 */
	protected boolean haveConnection = false;

	public static final int DB2 = 2;
	public static final int ORA = 11;
	public static final int TT = 42;

	static final String ORA_2_DB2 = "Oracle Trying to Speak to DB2";
	static final String ORA_2_DB2_ERROR_MESSAGE = "Got minus one from a read call";

	/*
	 * DB2 What product 11 = oracle, 2 = DB2 
	 */ 
	public int dbType = ORA; 

	/***
	 * Attempt to log into an Oracle database using the information provided.
	 * pPort will default to 1521. If pUser is SYSTEM and pPassword is null a
	 * guess will be made about the system password.
	 *
	 * @param pHostName
	 *            TCP Hostname of class. An IP Address can also be provided
	 * @param pPort
	 *            The port the Oracle listener is running on
	 * @param pSid
	 *            The Oracle SID
	 * @param pUser
	 *            An Oracle user with SA privs
	 * @param pPassword
	 *            Password for Oracle user with SA privs
	 * @param pLog
	 *            A Log object of some variety.
	 * @see com.spookyaction.util.Log
	 */
	public ConnectionWrangler(String pHostName, int pPort, String pSid, String pUser, String pPassword,
			LogInterface pLog, int dbType) throws CSException {

		this.dbType = dbType;

		InetAddress tempAddress;

		if (pHostName == null || pHostName.length() == 0) {
			throw new CSException("Server Hostname or IP address must be provided");
		} else {
			mrHostName = pHostName;
		}

		// get IP Address from Hostname...
		try {
			tempAddress = InetAddress.getByName(mrHostName);
			mrIpAddress = tempAddress.getHostAddress();
		} catch (Exception e) {
			mrIpAddress = mrHostName;
		}

		mrPort = pPort;

		if (pSid == null || pSid.length() == 0) {
			throw new CSException("SID must be provided");
		} else {
			mrSid = pSid;
		}

		if (pUser == null || pUser.length() == 0) {
			throw new CSException("Database Username must be provided");
		} else {
			mrUser = pUser;
		}

		if (pPassword == null || pPassword.length() == 0) {
			throw new CSException("Database password must be provided");
		} else {
			mrPassword = pPassword;
		}

		mrLog = pLog;

		haveConnection = false;

		this.connect();

	}

	/**
	 * @throws CSException
	 */
	private void loadDriver() throws CSException {
		//
		// Attempt to load database driver...
		try {
			if (dbType == TT)
				Class.forName("com.timesten.jdbc.TimesTenDriver");
			if (dbType == ORA)
				Class.forName("oracle.jdbc.driver.OracleDriver");
			if (dbType == DB2)
				Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		} catch (ClassNotFoundException oe) {
			mrLog.error("ConnectionWrangler:Unable to find DB driver '" + oe.getMessage()
					+ "'. Please add the JAR file containing " + "it to your CLASSPATH and restart.");
			throw new CSException("Unable to find required DB driver '" + oe.getMessage()
					+ "'. Please add the JAR file containing " + "it to your CLASSPATH and restart.");
		} catch (InstantiationException oe) {
			mrLog.error("ConnectionWrangler:DB2 Driver not found " + oe);
			throw new CSException("Unable to find DB2 driver");
		} catch (IllegalAccessException oe) {
			mrLog.error("ConnectionWrangler:DB2 Driver not found " + oe);
			throw new CSException("Unable to find DB2 driver");
		}
	}

	/***
	 * Attempt to log into a database using the information provided.
	 */
	public ConnectionWrangler(String pURL, String pUser, String pPassword, LogInterface pLog) throws CSException {

		if (pURL == null || pURL.length() == 0) {
			throw new CSException("OCI URL must be provided");
		} else {
			mrURL = pURL;
		}

		if (pUser == null || pUser.length() == 0) {
			throw new CSException("Username must be provided");
		} else {
			mrUser = pUser;
		}

		if (pPassword == null || pPassword.length() == 0) {
			throw new CSException("Password must be provided");
		} else {
			mrPassword = pPassword;
		}

		mrLog = pLog;

		haveConnection = false;

		this.connect();

	}

	/**
	 * Connect to DB. If we are already connected disconnect first and then
	 * reconnect.
	 */
	public synchronized void connect() throws CSException {

		if (haveConnection) {
			this.disconnect();
		}

		loadDriver();

		//
		// Attempt to login to the DB...
		//
		try {
			haveConnection = false;

			if (dbType == DB2) {
				String url = "jdbc:db2://" + mrIpAddress + ":" + mrPort + "/" + mrSid;
				mrLog.info("Connect to DB2 '" + mrSid + "' database using JDBC Universal type 4 driver.");

				String nodeNumber = "0";
				Properties props = new Properties();

				props.setProperty("user", mrUser);
				props.setProperty("password", mrPassword);
				props.setProperty("CONNECTNODE", nodeNumber);

				mrConnection = DriverManager.getConnection(url, props);
			} else if (dbType == TT) {
				
				mrLog.info("Connect to TT '" + mrSid + "' database using JDBC Universal type 4 driver.");

				TimesTenDataSource ds = new TimesTenDataSource();
				ds.setUser(mrUser); // User name to log in to TimesTen
				ds.setPassword(mrPassword); // Password to log in to TimesTen
				ds.setUrl("jdbc:timesten:client:" + mrSid);
				ds.setOraclePassword(mrPassword); // Password to log in to
													// Oracle DB
				mrConnection = ds.getConnection();

			} else {
				
				// ORACLE
				if (mrURL == null)
				// thin driver
				{
					mrConnection = DriverManager.getConnection(
							"jdbc:oracle:thin:@" + mrIpAddress + ":" + mrPort + ":" + mrSid, mrUser, mrPassword);
				} else {
					// oci driver
					mrConnection = DriverManager.getConnection(mrURL, mrUser, mrPassword);
				}
			}
			
			mrLog.info("Logged in as user " + mrUser);

			haveConnection = true;
			usageCounter = 1;
			usageDatetime.setTime(System.currentTimeMillis());

			// By default JDBC drivers commit automatically. Turn this off...
			mrConnection.setAutoCommit(false);

			if (mrConnection instanceof OracleConnection) {
				// Tell Oracle Driver to send stuff back in batches of 50
				((OracleConnection) mrConnection).setDefaultRowPrefetch(50);
				// Tell Oracle Driver to send updates in batches of 50
				((OracleConnection) mrConnection).setDefaultExecuteBatch(50);
			}

		} catch (SQLException e) {
			String mrMessage = e.getMessage();
			mrLog.info(mrMessage);

			// We get a specific error message when we try to connect to DB2
			// with an Oracle driver.
			if (mrMessage.indexOf(ORA_2_DB2_ERROR_MESSAGE) >= 0) {
					throw new CSException(ORA_2_DB2);
			
			} else if (mrMessage.startsWith("Io exception: Connection refused")) {
				throw new CSException("Io Error: Contact made but connection refused. Check that instance '" + mrSid
						+ "' is up and the listener is running");
			}

			else if (e.getErrorCode() == 17002 /* Io Exception */) {
				throw new CSException(
						"Network Error: Check Hostname '" + mrIpAddress + "' and Port '" + mrPort + "' are valid");
			}

			else if (e.getErrorCode() == 1017 /* Invalid Username/Password */) {
				throw new CSException("Login Error: Check Username '" + mrUser + "' and the password are valid");
			}

			else if (e.getErrorCode() == 1033 /*
												 * Startup of Shutdown in progress
												 */) {
				throw new CSException("Server Error: Startup of Shutdown in progress");
			}

			else if (e.getErrorCode() == 12535 /* operation timed out */) {
				throw new CSException("Io Error: Hostname '" + mrIpAddress + "' known but not reachable");
			}

			throw new CSException(e.toString());
		}
	}

	/**
	 * Make sure we have a connection by connecting if we are disconnected.
	 */
	public synchronized void confirmConnected() throws CSException {
		if (!haveConnection) {
			this.connect();
		}
	}

	/**
	 * @return <code>true</code> if we are connected.
	 */
	public synchronized boolean haveConnection() {
		return (haveConnection);
	}

	/**
	 * Return a connection object.
	 *
	 * Because of out habit of disconnecting and reconnecting access to this
	 * method is limited.
	 * 
	 * @return a live connection.
	 */
	protected synchronized Connection getConnection() throws CSException {
		if (!haveConnection) {
			this.connect();
		}
		return (mrConnection);
	}

	/**
	 * Cycle our connection
	 */
	public synchronized Connection cycleConnection() throws CSException {
		this.disconnect();
		this.connect();
		return (mrConnection);
	}

	/**
	 * Disconnect from the DB
	 */
	public synchronized void disconnect() {
		if (haveConnection) {
			try {
				mrConnection.commit();
				mrConnection.close();
			} catch (SQLException e) {
				mrLog.debug(e.getMessage());
				mrConnection = null;
			}
			haveConnection = false;
			usageCounter = 0;
		}
	}

	/**
	 * @return how many trancactions we have clocked up on this connection.
	 */
	public synchronized int getUsageCount() {
		return (usageCounter);
	}

	/**
	 * Set Batch size
	 */
	public void setBatchSize(int newBatchSize) {
		if (newBatchSize > 0) {
			try {
				// Tell Oracle Driver to send stuff back in batches of
				// newBatchSize
				((OracleConnection) mrConnection).setDefaultRowPrefetch(newBatchSize);

				// Tell Oracle Driver to send updates in batches of newBatchSize
				((OracleConnection) mrConnection).setDefaultExecuteBatch(newBatchSize);
			} catch (SQLException e) {
				mrLog.error(e);
			}
		}
	}
}
