package com.catran.trading.netty.client

import com.catran.trading.aggregator.TeakAggregator
import com.catran.trading.dao.teakDao.TeakDao
import com.catran.trading.model.Candle

class TeakHandler(teakDao: TeakDao){

  def apply(teakDao: TeakDao): TeakHandler = new TeakHandler(teakDao)

  def getCandles(from: Long, to: Long): List[Candle] = {
    val teaks = teakDao.getTeaks(from, to)
    TeakAggregator.createCandles(teaks.toList).sortBy(_.timestamp)
  }
}
