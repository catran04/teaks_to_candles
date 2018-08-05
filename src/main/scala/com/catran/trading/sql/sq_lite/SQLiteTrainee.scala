package com.catran.trading.sql.sq_lite

import java.sql.Connection

import com.catran.trading.options.ApplicationOptions
import org.apache.log4j.Logger

class SQLiteTrainee(cn: Connection, options: ApplicationOptions) {

  private val logger = Logger.getLogger(getClass)
  private val statement = cn.createStatement()


  /**
    * creates the table for a working application
    */
  def createTable: Unit = {
    statement.execute(s"CREATE TABLE IF NOT EXISTS ${options.tableName}(" +
      "timestamp INTEGER NOT NULL," +
      "ticker VARCHAR(12) NOT NULL," +
      "price REAL NOT NULL," +
      "volume INTEGER);")
    logger.info(s"the table '${options.tableName}' was created")
  }
}

object SQLiteTrainee {

  /**
    * change database and creates table into it
    *
    * @param cn connection to MySql server
    * @param options
    */
  def apply(cn: Connection, options: ApplicationOptions): Unit = {
    val trainee = new SQLiteTrainee(cn, options)
    trainee.createTable
  }
}