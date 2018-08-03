package com.catran.trading.aggregator

import com.catran.trading.model.{Candle, Teak}

object TeakAggregator {

  def createCandles(teaks: List[Teak]): List[Candle] = {
    require(teaks.nonEmpty, "List of teaks is empty")
    val grouped = teaks.groupBy(_.ticker)
    grouped.map(pair => createCandle(pair._2)).toList
  }

  private def createCandle(teaksOfOneTicker: List[Teak]): Candle = {
    new Candle(
      ticker = teaksOfOneTicker.head.ticker,
      timestamp = teaksOfOneTicker.minBy(_.timestamp).timestamp,
      open = teaksOfOneTicker.minBy(_.timestamp).price,
      high = teaksOfOneTicker.maxBy(_.price).price,
      low = teaksOfOneTicker.minBy(_.price).price,
      close = teaksOfOneTicker.maxBy(_.timestamp).price,
      volume = teaksOfOneTicker.map(_.volume).sum)
  }

/*  private def getPrices(teaksOfOneTicker: Vector[Teak]): CandlePrices = {
    val headPrice = teaksOfOneTicker.head.price
    var open: Double = headPrice
    var high: Double = headPrice
    var low: Double = headPrice
    var close: Double = headPrice
    var volume = teaksOfOneTicker.head.volume



    teaksOfOneTicker.foreach(teak => {
      val price = teak.price
      val volume = teak.volume
      if(teak.timestamp)
    })
  }*/
  case class CandlePrices(
                         open: Double,
                         high: Double,
                         low: Double,
                         close: Double,
                         volume: Long
                         )

}
