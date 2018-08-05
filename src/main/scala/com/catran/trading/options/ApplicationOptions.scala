package com.catran.trading.options

/**
  * using for application the different options
  */
case class ApplicationOptions(
                               storage: String = "SqLite",
                               tableName: String = "teakTable",
                               teakReceiverHost: String = "localhost",
                               teakReceiverPort: Int = 5555,
                               serverHost: String = "localhost",
                               serverPort: Int = 9590,
                               clientHost: String = "localhost",
                               clientPort: Int = 9590,
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
//        case Array("tableName", value) => options.copy(tableName = value)

        case Array("teakReceiverHost", value) => options.copy(teakReceiverHost = value)
        case Array("teakReceiverPort", value) => options.copy(teakReceiverPort = value.toInt)
        case Array("serverHost", value) => options.copy(serverHost = value)
        case Array("serverPort", value) => options.copy(serverPort = value.toInt)
        case Array("clientHost", value) => options.copy(clientHost = value)
        case Array("clientPort", value) => options.copy(clientPort = value.toInt)

//        case Array("sqlite.workingConnection", value) => options.copy(sqlite = options.sqlite.copy(workingConnection = value))
//        case Array("sqlite.testConnection", value) => options.copy(sqlite = options.sqlite.copy(testConnection = value))
//        case Array("sqlite.driver", value) => options.copy(sqlite = options.sqlite.copy(driver = value))

        case exc => throw new RuntimeException(s"invalid args: ${exc.mkString}")
      }
    }
  }
}

case class SqLiteOptions(
                          workingConnection: String = s"jdbc:sqlite:teakTable.db",
                          testConnection: String = "jdbc:sqlite:testdb.db",
                          driver: String = "org.sqlite.JDBC"
                        )