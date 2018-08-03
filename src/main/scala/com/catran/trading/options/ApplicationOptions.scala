package com.catran.trading.options

import com.catran.trading.dao.teakDao.SQLiteTeakDao
import com.catran.trading.netty.client.{ClientHandler, TeakHandler}
import com.catran.trading.sql.sq_lite.SQLiteConnector
import io.netty.channel.ChannelHandler

/**
  * using for application the different options
  */
case class ApplicationOptions(
                               storage: String = "SqLite",
                               tableName: String = "teakTable",
                               brokerHost: String = "localhost",
                               brokerPort: Int = 5555,
                               serverHost: String = "localhost",
                               serverPort: Int = 5554,
                               clientHost: String = "localhost",
                               clientPort: Int = 5554,
                               brokerHandler: ChannelHandler = new TeakHandler(new SQLiteTeakDao(new SQLiteConnector())),
                               clientHandler: ChannelHandler = new ClientHandler(),
                               sqlite: SqLiteOptions = SqLiteOptions()
                             )

object ApplicationOptions {
  val defaults = new ApplicationOptions()

  /**
    * Initialize options with given arguments
    */
  def apply(args: Array[String]): ApplicationOptions = {
    if (args.isEmpty) return ApplicationOptions.defaults
    args.foldLeft(ApplicationOptions()) { (options, arg) =>
      arg.split("=") match {
        case Array("storage", value) => options.copy(storage = value)
        case Array("tableName", value) => options.copy(tableName = value)

        case Array("sqlite.workingConnection", value) => options.copy(sqlite = options.sqlite.copy(workingConnection = value))
        case Array("sqlite.testConnection", value) => options.copy(sqlite = options.sqlite.copy(testConnection = value))
        case Array("sqlite.driver", value) => options.copy(sqlite = options.sqlite.copy(driver = value))

        case exc => throw new RuntimeException(s"invalid args: ${exc.mkString}")
      }
    }
  }
}

case class SqLiteOptions(
                          workingConnection: String = "jdbc:sqlite:userdb.db",
                          testConnection: String = "jdbc:sqlite:testdb.db",
                          driver: String = "org.sqlite.JDBC"
                        )