package com.catran.trading.sql

import java.sql.Connection

import com.catran.trading.options.ApplicationOptions

trait SQLConnector {
  def getConnection(options: ApplicationOptions): Connection
}
