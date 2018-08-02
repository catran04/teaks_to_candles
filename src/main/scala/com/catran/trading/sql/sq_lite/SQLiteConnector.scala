package com.catran.trading.sql.sq_lite

import java.sql.{Connection, DriverManager}

import com.catran.trading.options.ApplicationOptions
import com.catran.trading.sql.SQLConnector
import org.apache.log4j.Logger

class SQLiteConnector extends SQLConnector {

  private val logger = Logger.getLogger(getClass)
  private var url: String = _

  /**
    * creates the connection to a SqLite
    *
    * @param appOpt : ApplicationOptions - the options for a handling of connection
    * @return Connection - the connection to the SqLite
    */
  override def getConnection(appOpt: ApplicationOptions): Connection = {
    val options = appOpt.sqlite

    url = appOpt.sqlite.workingConnection

    val driver = options.driver
    Class.forName(driver)
    val connection = DriverManager.getConnection(url)
    logger.info(s"connection to database test was successed")
    connection
  }
}