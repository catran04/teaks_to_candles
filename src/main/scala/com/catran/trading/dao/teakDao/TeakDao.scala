package com.catran.trading.dao.teakDao

import com.catran.trading.model.Teak

trait TeakDao {
  def getTeaks(from: Long, to: Long): Seq[Teak]
  def setTeak(teak: Teak): Unit
}
