package com.catran.trading.util

object TimeUtil {
  def truncSeconds(timestamp: Long): Long = {
    timestamp - timestamp % (1000 * 60)
  }
}
