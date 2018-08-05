package com.catran.trading.netty.teaker

import com.catran.trading.dao.teakDao.SQLiteTeakDao
import com.catran.trading.netty.client.{Client, TeakInitializer}
import com.catran.trading.options.ApplicationOptions
import com.catran.trading.sql.sq_lite.SQLiteConnector

object TeakerGetter {

  def main(args: Array[String]): Unit = {
    val options = ApplicationOptions(args)
    val teakDao = new SQLiteTeakDao(options, new SQLiteConnector())
    new Client(
      host = options.teakReceiverHost,
      port = options.teakReceiverPort,
      initializer = new TeakInitializer(options, teakDao)).run()
  }
}
