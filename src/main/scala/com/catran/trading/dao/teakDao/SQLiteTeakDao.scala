package com.catran.trading.dao.teakDao
import com.catran.trading.model.Teak

class SQLiteTeakDao(connector: SQLConnector) extends TeakDao {
  override def getTeaks(from: Long, to: Long): Seq[Teak] = ???

  override def setTeaks(teaks: Seq[Teak]): Unit = ???
}
