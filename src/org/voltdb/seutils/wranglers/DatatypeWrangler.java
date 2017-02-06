package org.voltdb.seutils.wranglers;

import org.voltdb.seutils.log.LogInterface;
import org.voltdb.seutils.utils.CSException;

/**
*/
public class DatatypeWrangler
{

  /**
  * Used to keep track of the Oracle Version.
  * A future release of Oracle may require that the methods
  * in the class return different values
  */
  String oracleVersion = null;

  /**
  * Log Object
  */
  LogInterface theLog = null;

  /**
  * Class used for mapping Oracle data type to OracleTypes
  */
  public DatatypeWrangler(String oracleVersion, LogInterface theLog)
  {
  this.oracleVersion = oracleVersion;
  this.theLog = theLog;
  }

  /**
  * Return an <tt>int</tt> that represents the offical oracle data type.
  * @param String An oracle data type
  * @return int A constant that represents the underlying oracle data type.
  */
  public  String getOracletypeCode(String theColumnDataType, LogInterface theLog) throws CSException
    {
    String returnCode = "SqlUtils.ORACLE_OTHER_DATATYPE";

    if (   theColumnDataType.equals("VARCHAR2")
        || theColumnDataType.equals("VARCHAR")
        || theColumnDataType.equals("STRING"))
      {
      returnCode = "OracleTypes.VARCHAR";
      }
    else if (  theColumnDataType.equals("CHAR") )
      {
      returnCode = "OracleTypes.CHAR";
      }
    else if (  theColumnDataType.equals("CHARACTER") )
      {
      returnCode = "OracleTypes.CHAR";
      }
    else if (   theColumnDataType.equals("ROWID") )
      {
      returnCode = "OracleTypes.ROWID";
      }
    else if (   theColumnDataType.equals("UROWID") )
      {
      returnCode = "OracleTypes.CHAR";
      }
    /*
    // KLUGE 001 N* data types should be done properly
    else if (  theColumnDataType.equals("NCHAR") )
      {
      returnCode = "OracleTypes.CHAR";
      }
    else if (  theColumnDataType.equals("NVARCHAR") )
      {
      returnCode = "OracleTypes.VARCHAR";
      }
    **/
    else if (   theColumnDataType.equals("DATE"))
      {
      returnCode = "OracleTypes.TIMESTAMP";
      }
    else if (   theColumnDataType.equals("ORACLE COLLECTION"))
      {
      returnCode = "SqlUtils.ORACLE_TABLE_DATATYPE";
      }
    else if (   theColumnDataType.equals("TABLE"))
      {
      returnCode = "SqlUtils.ORACLE_TABLE_DATATYPE";
      }
    else if (   theColumnDataType.equals("VARRAY"))
      {
      returnCode = "SqlUtils.ORACLE_VARRAY_DATATYPE";
      }
    else if (   theColumnDataType.equals("OBJECT"))
      {
      returnCode = "SqlUtils.ORACLE_OBJECT_DATATYPE";
      }
    else if (   theColumnDataType.equals("READONLYROWSET"))
      {
      returnCode = "SqlUtils.ORINDASOFT_READONLYROWSET";
      }
    else if (   theColumnDataType.equals("NUMBER")
             || theColumnDataType.equals("FLOAT")
             || theColumnDataType.equals("DECFLOAT"))
      {
      returnCode = "OracleTypes.NUMERIC";
      }
    else if (theColumnDataType.equals("PL/SQL BOOLEAN"))
      {
      returnCode = "OracleTypes.NUMBER";
      }
    else if (theColumnDataType.equals("BINARY_INTEGER"))
      {
      returnCode = "OracleTypes.NUMBER";
      }
    else if (   theColumnDataType.equals("RAW"))
      {
      returnCode = "OracleTypes.VARBINARY";
      }
    else if (    theColumnDataType.equals("TIMESTAMP WITH LOCAL TIME ZONE") // Datatype according to ALL_SOURCE
              || theColumnDataType.equals("TIMESTAMPLTZ") // Datatype according to ResultSet
              || (   theColumnDataType.startsWith("TIMESTAMP") // Allow for "TIMESTAMP(6) WITH LOCAL TIME ZONE"
                  && theColumnDataType.endsWith("LOCAL TIME ZONE")))
      {
      returnCode = "OracleTypes.TIMESTAMPLTZ";
      }
    else if (    theColumnDataType.equals("TIMESTAMP WITH TIME ZONE")  // Datatype according to ALL_SOURCE
              || theColumnDataType.equals("TIMESTAMPTZ")   // Datatype according to ResultSet
              || (   theColumnDataType.startsWith("TIMESTAMP") // Allow for "TIMESTAMP(6) WITH LOCAL TIME ZONE"
                  && theColumnDataType.endsWith("TIME ZONE")))
      {
      returnCode = "OracleTypes.TIMESTAMPTZ";
      }
    else if (   theColumnDataType.startsWith("TIMESTAMP"))
      {
      returnCode = "OracleTypes.TIMESTAMP";
      }
   else if (   theColumnDataType.equals("LONG"))
      {
      returnCode = "OracleTypes.LONGVARCHAR";
      }
    else if (   theColumnDataType.equals("LONG RAW"))
      {
      returnCode = "OracleTypes.LONGVARBINARY";
      }
    else if (   theColumnDataType.equals("BLOB"))
      {
      returnCode = "OracleTypes.BLOB";
      }
    else if (   theColumnDataType.equals("CLOB"))
      {
      returnCode = "OracleTypes.CLOB";
      }
    else if (   theColumnDataType.equals("BFILE"))
      {
      returnCode = "OracleTypes.BFILE";
      }
    else if (   theColumnDataType.equals("REF CURSOR"))
      {
      returnCode = "OracleTypes.CURSOR";
      }
    else if (theColumnDataType.equals("PL/SQL RECORD"))
      {
      returnCode = "SqlUtils.ORACLE_ROWTYPE_DATATYPE";
      }
    else if (    theColumnDataType.equals("INTERVAL YEAR TO MONTH")
              || theColumnDataType.equals("INTERVALYM")
              || (   theColumnDataType.startsWith("INTERVAL YEAR")
                  && theColumnDataType.endsWith("TO MONTH")))
      {
      returnCode = "OracleTypes.INTERVALYM";
      }
    else if (    theColumnDataType.equals("INTERVAL DAY TO SECOND")
              || theColumnDataType.equals("INTERVALDS")
              || (   theColumnDataType.startsWith("INTERVAL DAY")
                  && theColumnDataType.indexOf("TO SECOND") > -1))   
      {
      returnCode = "OracleTypes.INTERVALDS";
      }
    else if (theColumnDataType.equals("PL/SQL TABLE"))
      {
      //returnCode = "OracleTypes.PLSQL_INDEX_TABLE";
      returnCode = "SqlUtils.ORACLE_PLSQL_INDEXBY_ROWTYPE_DATATYPE";
      }
    else if (   theColumnDataType.equals("XMLTYPE")
             || theColumnDataType.equals("PUBLIC.XMLTYPE")
             || theColumnDataType.equals("SYS.XMLTYPE"))
      {
      returnCode = "OracleTypes.OPAQUE";
      }
    else if (   theColumnDataType.equals("SDO_GEOMETRY"))
      {
      returnCode = "SqlUtils.ORACLE_SDO_GEOMETRY_DATATYPE";
      }
    else
      {
      throw new CSException("Unknown data type of " + theColumnDataType + " seen");
      }

    return(returnCode);
    }

}


