package com.catran.trading.dao.teakDao

import com.catran.trading.model.Teak
import com.catran.trading.options.ApplicationOptions
import com.catran.trading.sql.SQLConnector
import com.catran.trading.sql.sq_lite.SQLiteTrainee
import org.apache.log4j.Logger

import scala.collection.mutable

class SQLiteTeakDao(appOptions: ApplicationOptions, connector: SQLConnector) extends TeakDao {
  private val connection = connector.getConnection(appOptions)
  private val statement = connection.createStatement()
  private val logger = Logger.getLogger(getClass)
  SQLiteTrainee(connection, appOptions) //prepare database and creates table for the using


  override def getTeaks(from: Long, to: Long): Seq[Teak] = {
    try {
      val query = s"SELECT * FROM ${appOptions.tableName}" +
        s" WHERE timestamp >= ${from} AND timestamp <= ${to};"
      val rs = statement.executeQuery(query)
      var teaks: mutable.HashSet[Teak] = mutable.HashSet[Teak]()
      while (rs.next()) {
        val teak = Teak(
          timestamp = rs.getLong(1),
          ticker = rs.getString(2),
          price = rs.getDouble(3),
          volume = rs.getInt(4)
        )
        teaks += teak
      }
      teaks.toSeq
    } catch {
      case e: Exception =>
        e.printStackTrace()
        throw e
    }
  }


    /**
      * added new ticker into a storage
      */
    override def setTeak(teak: Teak): Unit = {
      try {
        val query = s"INSERT INTO ${appOptions.tableName}(timestamp,ticker,price,volume) VALUES(?,?,?,?)"
        val ps = connection.prepareStatement(query)
        ps.setLong(1, teak.timestamp)
        ps.setString(2, teak.ticker)
        ps.setDouble(3, teak.price)
        ps.setInt(4, teak.volume)
        ps.executeUpdate()
      } catch {
        case e: Exception => e.printStackTrace()
      }
  }
}