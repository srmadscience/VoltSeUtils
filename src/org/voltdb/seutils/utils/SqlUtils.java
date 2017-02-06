package org.voltdb.seutils.utils;

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
 * A set of useful static methods for working with SQL.
 * <p>
 */
public class SqlUtils {

	public static final int NULL_VALUE = -42;

	/**
	 * Constant for identifying statements as being queries
	 */
	public static final int SELECT = 0;

	/**
	 * Constant for identifying statements as being inserts
	 */
	public static final int INSERT = 1;

	/**
	 * Constant for identifying statements as being updates
	 */
	public static final int UPDATE = 2;

	/**
	 * Constant for identifying statements as being deletes
	 */
	public static final int DELETE = 3;

	/**
	 * Constant for identifying statements as being DDL statements
	 * 
	 * @since 2.0.1527 Support for Lock, Merge and DDL statements
	 */
	public static final int DDL = 5;

	/**
	 * Constant for identifying statements as being Lock statements
	 * 
	 * @since 2.0.1527 Support for Lock, Merge and DDL statements
	 */
	public static final int LOCK = 6;

	/**
	 * Constant for identifying statements as being Merge statements
	 * 
	 * @since 2.0.1527 Support for Lock, Merge and DDL statements
	 */
	public static final int MERGE = 7;

	/**
	 * Constant for identifying statements as being Lock statements
	 * 
	 * @since 2.0.1527 Support for Lock, Merge and DDL statements
	 */
	public static final int XPLAN = 8;

	/**
	 * Constant for identifying statements as being Lock statements
	 * 
	 * @since 2.0.1527 Support for Lock, Merge and DDL statements
	 */
	public static final int SAVEPOINT = 9;

	/**
	 * Constant for identifying statements as being Lock statements
	 * 
	 * @since 2.0.1527 Support for Lock, Merge and DDL statements
	 */
	public static final int SET_CONSTRAINTS = 10;

	/**
	 * Constant for identifying statements as being Lock statements
	 * 
	 * @since 2.0.1527 Support for Lock, Merge and DDL statements
	 */
	public static final int SET_TRANSACTION = 11;

	/**
	 * Constant for identifying statements as being Lock statements
	 * 
	 * @since 2.0.1527 Support for Lock, Merge and DDL statements
	 */
	public static final int SET_ROLE = 12;

	/**
	 * Constant for identifying statements as being PL/SQL statements
	 * 
	 * @since 2.0.1527 Support for Lock, Merge and DDL statements
	 */
	public static final int PLSQL = 14;

	/**
	 * Constant for identifying statements as being unidentifiable
	 */
	public static final int UNKNOWN = 4;

	/**
	 * Array containing words which are not legal identifers...
	 */
	public static final String[] RESERVED_WORDS = { "ACCESS", "ADD", "ALL", "ALTER", "AND", "ANY", "AS", "ASC", "AUDIT",
			"BETWEEN", "BY", "CHAR", "CHECK", "CLUSTER", "COLUMN", "COMMENT", "COMPRESS", "CONNECT", "CREATE",
			"CURRENT", "DATE", "DECIMAL", "DEFAULT", "DELETE", "DESC", "DISTINCT", "DROP", "ELSE", "EXCLUSIVE",
			"EXISTS", "FILE", "FLOAT", "FOR", "FROM", "GRANT", "GROUP", "HAVING", "IDENTIFIED", "IMMEDIATE", "IN",
			"INCREMENT", "INDEX", "INITIAL", "INSERT", "INTEGER", "INTERSECT", "INTO", "IS", "LEVEL", "LIKE", "LOCK",
			"LONG", "MAXEXTENTS", "MINUS", "MLSLABEL", "MODE", "MODIFY", "NOAUDIT", "NOCOMPRESS", "NOT", "NOWAIT",
			"NULL", "NUMBER", "OF", "OFFLINE", "ON", "ONLINE", "OPTION", "OR", "ORDER", "PCTFREE", "PRIOR",
			"PRIVILEGES", "PUBLIC", "RAW", "RENAME", "RESOURCE", "REVOKE", "ROW", "ROWID", "ROWNUM", "ROWS", "SELECT",
			"SESSION", "SET", "SHARE", "SIZE", "SMALLINT", "START", "SUCCESSFUL", "SYNONYM", "SYSDATE", "TABLE", "THEN",
			"TO", "TRIGGER", "UID", "UNION", "UNIQUE", "UPDATE", "USER", "VALIDATE", "VALUES", "VARCHAR", "VARCHAR2",
			"VIEW", "WHENEVER", "WHERE", "WITH" };

	/**
	 * Constant for identifiying oracle Text datatypes
	 */
	public static final int ORACLE_TEXT_DATATYPE = 0;

	/**
	 * Constant for identifiying oracle Number datatypes
	 */
	public static final int ORACLE_NUMBER_DATATYPE = 1;

	/**
	 * Constant for identifiying oracle Date datatypes
	 */
	public static final int ORACLE_DATE_DATATYPE = 2;

	/**
	 * Constant for identifiying oracle Long Text datatypes
	 */
	public static final int ORACLE_LONGTEXT_DATATYPE = 3;

	/**
	 * Constant for identifiying oracle Long Binary datatypes
	 */
	public static final int ORACLE_LONG_BINARY_DATATYPE = 4;

	/**
	 * Constant for identifiying oracle Binary datatypes
	 */
	public static final int ORACLE_BINARY_DATATYPE = 11;

	/**
	 * Constant for identifiying oracle CLOB datatypes
	 */
	public static final int ORACLE_CLOB_DATATYPE = 15;

	/**
	 * Constant for identifiying oracle BLOB datatypes
	 */
	public static final int ORACLE_BLOB_DATATYPE = 16;

	/**
	 * Constant for identifiying oracle BFILE datatypes
	 */
	public static final int ORACLE_BFILE_DATATYPE = 17;

	/**
	 * Constant for identifiying oracle ref cursors
	 */
	public static final int ORACLE_REFCURSOR_DATATYPE = 6;

	/**
	 * Constant for identifiying PL/SQL Boolean
	 */
	public static final int ORACLE_BOOLEAN_DATATYPE = 7;

	/**
	 * Constant for identifiying PL/SQL Boolean
	 */
	public static final int ORINDASOFT_READONLYROWSET = 8;

	/**
	 * Constant for identifiying ROWID
	 */
	public static final int ORACLE_ROWID_DATATYPE = 9;

	/**
	 * Constant for identifiying UROWID
	 */
	public static final int ORACLE_UROWID_DATATYPE = 10;

	/**
	 * Constant for identifiying TIMESTAMP
	 */
	public static final int ORACLE_TIMESTAMP_DATATYPE = 12;

	/**
	 * Constant for identifiying TIMESTAMPTZ
	 */
	public static final int ORACLE_TIMESTAMPTZ_DATATYPE = 13;

	/**
	 * Constant for identifiying TIMESTAMPLTZ
	 */
	public static final int ORACLE_TIMESTAMPLTZ_DATATYPE = 14;

	/**
	 * Constant for identifiying oracle Collection's TABLE
	 */
	public static final int ORACLE_TABLE_DATATYPE = 18;

	/**
	 * Constant for identifiying oracle Collection's VARRAY
	 */
	public static final int ORACLE_VARRAY_DATATYPE = 19;

	/**
	 * Constant for identifiying Oracle OBJECT Datatype
	 */
	public static final int ORACLE_OBJECT_DATATYPE = 20;

	/**
	 * Constant for identifiying PL/SQL Rowtype Datatype
	 */
	public static final int ORACLE_ROWTYPE_DATATYPE = 21;

	/**
	 * Constant for identifiying INTERVAL YEAR TO MONTH Datatype
	 */
	public static final int ORACLE_INTERVAL_YEAR_TO_MONTH_DATATYPE = 22;

	/**
	 * Constant for identifiying INTERVAL DAY TO SECOND Datatype
	 * 
	 * @since Oracle 10.1.0
	 */
	public static final int ORACLE_INTERVAL_DAY_TO_SECOND_DATATYPE = 23;

	/**
	 * Constant for identifiying PL/SQL Index By tables
	 * 
	 * @since Oracle 10.1.0
	 */
	public static final int ORACLE_PLSQL_INDEXBY_DATATYPE = 24;

	/**
	 * Constant for identifiying PL/SQL Index By tables
	 * 
	 * @since Oracle 10.1.0
	 */
	public static final int ORACLE_PLSQL_INDEXBY_ROWTYPE_DATATYPE = 25;

	/**
	 * Constant for identifiying XMLType
	 * 
	 * @since Oracle 10.2.0
	 */
	public static final int ORACLE_XMLTYPE_DATATYPE = 26;

	/**
	 * Constant for identifiying ORACLE_SDO_GEOMETRY_DATATYPE
	 * 
	 * @since Oracle 10.2.0
	 */
	public static final int ORACLE_SDO_GEOMETRY_DATATYPE = 27;

	/**
	 * Constant for identifiying unrecognized oracle datatypes
	 */
	public static final int ORACLE_OTHER_DATATYPE = 99;

	/**
	 * Constant for identifiying null datatypes
	 */
	public static final int ORACLE_NULL_DATATYPE = 100;

	/**
	 * Constant for ORA-4043 - Object does not exist
	 */
	public static final int OBJECT_DOES_NOT_EXIST = 4043;

	/**
	 * Constant for ORA-17074 message - invalid name pattern
	 **/
	public static final int INVALID_NAME_PATTERN = 17074;

	/**
	 * Constant for ORA-17059 message - invalid name pattern The most common
	 * cause for this is a DB/Driver version mismatch
	 **/
	public static final int FAILED_TO_CONVERT_INTERNAL = 17059;

	/**
	 * Constant for ORA-2303 message - cannot drop or replace a type with type
	 * or table dependents
	 **/
	public static final int TYPE_HAS_DEPENDENTS = 2303;

	/**
	 * A set of useful static methods for working with SQL.
	 */
	public SqlUtils() {
	}

	/**
	 * Count parameters in a SQL Statement This method has a <a href=
	 * "http://www.orindasoft.com/public/Supporttwo.php4#SqlUtils.countParameters()_returns_incorrect_value_if_comment_contains_'?'"
	 * target=_blank class=news>known bug</a>
	 * 
	 * @param String
	 *            aSqlStatement a SQL Statement
	 * @return an int The number of JDBC parameters in this SQL statement
	 */
	public static int countParameters(String aSqlStatement) {
		char[] statementArray = aSqlStatement.toCharArray();
		boolean inQuote = false;
		int paramCounter = 0;

		for (int i = 0; i < statementArray.length; i++) {
			if (statementArray[i] == '\'') {
				if (inQuote) {
					inQuote = false;
				} else {
					inQuote = true;
				}
			}

			if ((!inQuote) && statementArray[i] == '?') {
				paramCounter++;
			}
		}

		return (paramCounter);
	}

	public static String getUnderlyingVoltDBDataType(int theOracleDataType, int length, int precision, int scale) {

		String datatype = null;

		switch (theOracleDataType) {
		case ORACLE_TEXT_DATATYPE:
		case ORACLE_ROWID_DATATYPE:
		case ORACLE_UROWID_DATATYPE:
		case ORACLE_XMLTYPE_DATATYPE:
		case ORACLE_CLOB_DATATYPE:
		case ORACLE_LONGTEXT_DATATYPE:
			datatype = "VARCHAR";

			if (length > 0) {
				datatype = datatype + "(" + length + ")";
			}

			break;
		case ORACLE_NUMBER_DATATYPE:

			// See if we can use something smaller
			if (precision == -42) {
				datatype = "BIGINT";
			} else {
				datatype = "DECIMAL";
			}

			// TODO
			break;
		case ORACLE_LONG_BINARY_DATATYPE:
		case ORACLE_BINARY_DATATYPE:
		case ORACLE_BLOB_DATATYPE:
		case ORACLE_BFILE_DATATYPE:
			datatype = "VARBINARY";
			break;
		case ORACLE_REFCURSOR_DATATYPE:
		case ORACLE_TABLE_DATATYPE:
		case ORACLE_VARRAY_DATATYPE:
		case ORACLE_OBJECT_DATATYPE:
		case ORACLE_ROWTYPE_DATATYPE:
		case ORACLE_PLSQL_INDEXBY_DATATYPE:
		case ORACLE_PLSQL_INDEXBY_ROWTYPE_DATATYPE:
			datatype = "VoltTable";
			break;
		case ORACLE_BOOLEAN_DATATYPE:
			datatype = "TINYINT";
			break;
		case ORACLE_DATE_DATATYPE:
		case ORACLE_TIMESTAMP_DATATYPE:
		case ORACLE_TIMESTAMPTZ_DATATYPE:
		case ORACLE_TIMESTAMPLTZ_DATATYPE:
			datatype = "TIMESTAMP";
			break;
		case ORACLE_SDO_GEOMETRY_DATATYPE:
			datatype = "GEOGRAPHY";
			break;
		case ORACLE_INTERVAL_YEAR_TO_MONTH_DATATYPE:
		case ORACLE_INTERVAL_DAY_TO_SECOND_DATATYPE:
		case ORACLE_OTHER_DATATYPE:
		case ORACLE_NULL_DATATYPE:
		default:
			datatype = "VoltTable";
			break;
		}

		return datatype;

	}

	public static String getUnderlyingVoltJavaDataType(int theOracleDataType, int length, int precision, int scale) {

		String datatype = null;

		switch (theOracleDataType) {
		case ORACLE_TEXT_DATATYPE:
		case ORACLE_ROWID_DATATYPE:
		case ORACLE_UROWID_DATATYPE:
		case ORACLE_XMLTYPE_DATATYPE:
		case ORACLE_CLOB_DATATYPE:
		case ORACLE_LONGTEXT_DATATYPE:
			datatype = "String";

			break;
		case ORACLE_NUMBER_DATATYPE:

			// See if we can use something smaller
			if (precision == -42) {
				datatype = "long";
			} else {
				datatype = "double";
			}

			// TODO
			break;
		case ORACLE_LONG_BINARY_DATATYPE:
		case ORACLE_BLOB_DATATYPE:
		case ORACLE_BFILE_DATATYPE:
		case ORACLE_BINARY_DATATYPE:
			datatype = "byte[]";
			break;
		case ORACLE_REFCURSOR_DATATYPE:
		case ORACLE_TABLE_DATATYPE:
		case ORACLE_VARRAY_DATATYPE:
		case ORACLE_OBJECT_DATATYPE:
		case ORACLE_ROWTYPE_DATATYPE:
		case ORACLE_PLSQL_INDEXBY_DATATYPE:
		case ORACLE_PLSQL_INDEXBY_ROWTYPE_DATATYPE:
			datatype = "VoltTable";
			break;
		case ORACLE_BOOLEAN_DATATYPE:
			datatype = "int";
			break;
		case ORACLE_DATE_DATATYPE:
		case ORACLE_TIMESTAMP_DATATYPE:
		case ORACLE_TIMESTAMPTZ_DATATYPE:
		case ORACLE_TIMESTAMPLTZ_DATATYPE:
			datatype = "TimestampType";
			break;
		case ORACLE_SDO_GEOMETRY_DATATYPE:
			datatype = "String";
			break;
		case ORACLE_INTERVAL_YEAR_TO_MONTH_DATATYPE:
		case ORACLE_INTERVAL_DAY_TO_SECOND_DATATYPE:
		case ORACLE_OTHER_DATATYPE:
		case ORACLE_NULL_DATATYPE:
		default:
			datatype = "VoltTable";
			break;
		}

		return datatype;

	}

	/**
	 * Return an <tt>int</tt> that represents the underlying oracle data type.
	 * This method takes an oracle data type and classifies it as Text, Number,
	 * Date Long Text or Other.
	 * 
	 * @param String
	 *            An oracle data type
	 * @return int A SqlUtils constant that represents the underlying oracle
	 *         data type.
	 */
	public static int getUnderlyingOracleDatatype(String theColumnDataType) {
		int returnCode = ORACLE_OTHER_DATATYPE;
		if (theColumnDataType == null) {
			returnCode = ORACLE_NULL_DATATYPE;
		} else if (theColumnDataType.equals("VARCHAR2") || theColumnDataType.equals("VARCHAR")
				|| theColumnDataType.equals("CHAR") || theColumnDataType.equals("CHARACTER")
				|| theColumnDataType.equals("STRING")) {
			returnCode = ORACLE_TEXT_DATATYPE;
		} else if (theColumnDataType.equals("ROWID")) {
			returnCode = ORACLE_ROWID_DATATYPE;
		} else if (theColumnDataType.equals("UROWID")) {
			returnCode = ORACLE_UROWID_DATATYPE;
		} else if (theColumnDataType.equals("DATE")) {
			returnCode = ORACLE_DATE_DATATYPE;
		} else if (theColumnDataType.equals("NUMBER") || theColumnDataType.equals("FLOAT")
				|| theColumnDataType.equals("NATURAL") || theColumnDataType.equals("NATURALN")
				|| theColumnDataType.equals("POSITIVE") || theColumnDataType.equals("POSITIVEN")
				|| theColumnDataType.equals("SIGNTYPE") || theColumnDataType.equals("BINARY_INTEGER")
				|| theColumnDataType.equals("DEC") || theColumnDataType.equals("DECIMAL")
				|| theColumnDataType.equals("DOUBLE PRECISION") || theColumnDataType.equals("FLOAT")
				|| theColumnDataType.equals("INTEGER") || theColumnDataType.equals("INT")
				|| theColumnDataType.equals("NUMERIC") || theColumnDataType.equals("REAL")
				|| theColumnDataType.equals("SMALLINT") || theColumnDataType.equals("PLS_INTEGER")
				|| theColumnDataType.equals("DECFLOAT")// DB2
		) {
			returnCode = ORACLE_NUMBER_DATATYPE;
		} else if (theColumnDataType.equals("LONG")) {
			returnCode = ORACLE_LONGTEXT_DATATYPE;
		} else if (theColumnDataType.equals("CLOB")) {
			returnCode = ORACLE_CLOB_DATATYPE;
		} else if (theColumnDataType.equals("com.orindasoft.pub.ReadOnlyRowSet")) {
			returnCode = ORINDASOFT_READONLYROWSET;
		} else if (theColumnDataType.equals("ORACLE COLLECTION")) {
			returnCode = ORACLE_TABLE_DATATYPE;
		} else if (theColumnDataType.equals("TABLE")) {
			returnCode = ORACLE_TABLE_DATATYPE;
		} else if (theColumnDataType.equals("VARRAY")) {
			returnCode = ORACLE_VARRAY_DATATYPE;
		} else if (theColumnDataType.equals("OBJECT")) {
			returnCode = ORACLE_OBJECT_DATATYPE;
		} else if (theColumnDataType.equals("PL/SQL BOOLEAN")) {
			returnCode = ORACLE_BOOLEAN_DATATYPE;
		} else if (theColumnDataType.equals("PL/SQL RECORD")) {
			returnCode = ORACLE_ROWTYPE_DATATYPE;
		} else if (theColumnDataType.equals("LONG RAW")) {
			returnCode = ORACLE_LONG_BINARY_DATATYPE;
		} else if (theColumnDataType.equals("BLOB")) {
			returnCode = ORACLE_BLOB_DATATYPE;
		} else if (theColumnDataType.equals("BFILE")) {
			returnCode = ORACLE_BFILE_DATATYPE;
		} else if (theColumnDataType.equals("RAW")) {
			returnCode = ORACLE_BINARY_DATATYPE;
		} else if (theColumnDataType.equals("REF CURSOR")) {
			returnCode = ORINDASOFT_READONLYROWSET;
		} else if (theColumnDataType.equals("INTERVAL YEAR TO MONTH") || theColumnDataType.equals("INTERVALYM")
				|| (theColumnDataType.startsWith("INTERVAL YEAR") && theColumnDataType.endsWith("TO MONTH"))) {
			returnCode = ORACLE_INTERVAL_YEAR_TO_MONTH_DATATYPE;
		} else if (theColumnDataType.equals("INTERVAL DAY TO SECOND") // NOTIN8NY
																		// //NOTIN920
																		// //NOTIN901
				|| theColumnDataType.equals("INTERVALDS") // NOTIN8NY //NOTIN920
															// //NOTIN901
				|| (theColumnDataType.startsWith("INTERVAL DAY") // NOTIN8NY
																	// //NOTIN920
																	// //NOTIN901
						&& theColumnDataType.indexOf("TO SECOND") > -1)) // NOTIN8NY
																			// //NOTIN920
																			// //NOTIN901
		{ // NOTIN8NY //NOTIN920 //NOTIN901
			returnCode = ORACLE_INTERVAL_DAY_TO_SECOND_DATATYPE; // NOTIN8NY
																	// //NOTIN920
																	// //NOTIN901
		} // NOTIN8NY //NOTIN920 //NOTIN901
		else if (theColumnDataType.equals("TIMESTAMP WITH LOCAL TIME ZONE") // Datatype
																			// according
																			// to
																			// ALL_SOURCE
				|| theColumnDataType.equals("TIMESTAMPLTZ") // Datatype
															// according to
															// ResultSet
				|| (theColumnDataType.startsWith("TIMESTAMP") // Allow for
																// "TIMESTAMP(6)
																// WITH LOCAL
																// TIME ZONE"
						&& theColumnDataType.endsWith("LOCAL TIME ZONE"))) {
			returnCode = ORACLE_TIMESTAMPLTZ_DATATYPE;
		} else if (theColumnDataType.equals("TIMESTAMP WITH TIME ZONE") // Datatype
																		// according
																		// to
																		// ALL_SOURCE
				|| theColumnDataType.equals("TIMESTAMPTZ") // Datatype according
															// to ResultSet
				|| (theColumnDataType.startsWith("TIMESTAMP") // Allow for
																// "TIMESTAMP(6)
																// WITH LOCAL
																// TIME ZONE"
						&& theColumnDataType.endsWith("TIME ZONE"))) {
			returnCode = ORACLE_TIMESTAMPTZ_DATATYPE;
		} else if (theColumnDataType.startsWith("TIMESTAMP")) {
			returnCode = ORACLE_TIMESTAMP_DATATYPE;
		} else if (theColumnDataType.startsWith("PL/SQL TABLE")) // NOTIN8NY
																	// //NOTIN920
																	// //NOTIN901
		{ // NOTIN8NY //NOTIN920 //NOTIN901
			returnCode = ORACLE_PLSQL_INDEXBY_DATATYPE; // NOTIN8NY //NOTIN920
														// //NOTIN901
		} // NOTIN8NY //NOTIN920 //NOTIN901
		else if (theColumnDataType.equals("XMLTYPE")) // NOTIN8NY //NOTIN920
														// //NOTIN901
		{ // NOTIN8NY //NOTIN920 //NOTIN901
			returnCode = ORACLE_XMLTYPE_DATATYPE; // NOTIN8NY //NOTIN920
													// //NOTIN901
		} // NOTIN8NY //NOTIN920 //NOTIN901
		else if (theColumnDataType.equals("SDO_GEOMETRY")) // NOTIN8NY
															// //NOTIN920
															// //NOTIN901
		{ // NOTIN8NY //NOTIN920 //NOTIN901
			returnCode = ORACLE_SDO_GEOMETRY_DATATYPE; // NOTIN8NY //NOTIN920
														// //NOTIN901
		} // NOTIN8NY //NOTIN920 //NOTIN901

		return (returnCode);
	}
	// NOTIN815 //NOTIN816
	// NOTIN815 //NOTIN816

	public static String toJavaName(String inputValue) {
		
		if (inputValue == null) {
			return null;
		}

		return inputValue.replace("_", "").toLowerCase();

	}

	public static String getVoltDBDataTypeEnumerationName(String voltDbType) {
		String result = "VoltType." + voltDbType;

		if (voltDbType.startsWith("VARCHAR(")) {
			result = "VoltType.String";
		} else if (voltDbType.startsWith("VARBINARY(")) {
			result = "VoltType.VARBINARY";
		} else if (voltDbType.startsWith("GEOGRAPHY(")) {
			result = "VoltType.String";
		}

		return result;
	}
}
