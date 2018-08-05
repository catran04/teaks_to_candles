package com.catran.trading.aggregator

import com.catran.trading.dao.teakDao.TeakDao
import com.catran.trading.model.Candle

class TeakHandler(teakDao: TeakDao){

  def getCandles(from: Long, to: Long): List[Candle] = {
    val teaks = teakDao.getTeaks(from, to)
    TeakAggregator.createCandles(teaks.toList).sortBy(_.timestamp)
  }
}
