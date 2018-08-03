package com.catran.trading.netty.client

import com.catran.trading.dao.teakDao.SQLiteTeakDao
import com.catran.trading.options.ApplicationOptions
import com.catran.trading.sql.sq_lite.SQLiteConnector

object Main {

  def main(args: Array[String]): Unit = {
    val options = ApplicationOptions(args)
    val teakDao = new SQLiteTeakDao(options, new SQLiteConnector())
    new Client(
      host = "localhost",
      port = 5555,
      initializer = new TeakInitializer(options, teakDao)).run()
  }

}
