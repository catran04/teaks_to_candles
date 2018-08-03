package com.catran.trading.dao.teakDao
import com.catran.trading.model.Teak
import com.catran.trading.sql.SQLConnector

class SQLiteTeakDao(connector: SQLConnector) extends TeakDao {
  override def getTeaks(from: Long, to: Long): Seq[Teak] = ???

  override def setTeaks(teaks: Seq[Teak]): Unit = ???

  override def setTeak(teak: Teak): Unit = ???
}
