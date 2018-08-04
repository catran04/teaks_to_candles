package com.catran.trading.aggregator

import com.catran.trading.model.{Candle, Teak}
import com.catran.trading.util.TimeUtil
import org.joda.time.{DateTime, DateTimeZone}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object TeakAggregator {

  private val ONE_MINUTE = 60000

  def createCandles(teaks: List[Teak]): List[Candle] = {
    require(teaks.nonEmpty, "List of teaks is empty")
    val groupedByTicker: Map[String, List[Teak]] = teaks.groupBy(_.ticker)
    val groupedByMinuteAndTicker = groupedByTicker.map{case(k, v) => groupByMinute(v)}
//    println(s"byTicker: ${groupedByMinuteAndTicker.values.flatMap(t => t.map(f => f.ticker)).mkString("\n")}")

   val grouped =  for{
      minuteTeaks <- groupedByMinuteAndTicker
      candle <- minuteTeaks
    } yield candle

    grouped.map{case(timestamp, teaks) => createCandle(timestamp, teaks)}.toList

//    val candles = groupedByMinuteAndTicker.flatMap(f => ).map{case(k, v) => createCandle(k, v)}.toList
//    println(candles.mkString("\n"))

  }

  private def groupByMinute(teaksOfOneTicker: List[Teak]): Map[String, List[Teak]] = {
    DateTimeZone.setDefault(DateTimeZone.UTC)
    val minTime = teaksOfOneTicker.minBy(_.timestamp).timestamp
    val maxTime = teaksOfOneTicker.maxBy(_.timestamp).timestamp
    println(s"max Time of ticket: ${maxTime}")

    val truncedMinTime = TimeUtil.truncSeconds(minTime)
    val truncedMaxTime = TimeUtil.truncSeconds(maxTime)

    val timeRange = getMinuteTimeRange(truncedMinTime, truncedMaxTime)

    val grouped = mutable.Map[String, List[Teak]]()
    timeRange.foreach(time => {
      val stringTime = new DateTime(time).toString("yyyy-MM-dd'T'HH:mm:ss")
      val theseTeaks = teaksOfOneTicker.filter(teak => teak.timestamp >= time && teak.timestamp < time + ONE_MINUTE)
      grouped += (stringTime -> theseTeaks)
    })
    grouped.toMap
  }

  private def getMinuteTimeRange(timeFrom: Long, timeTo: Long): Seq[Long] = {
    val timeSet = new ArrayBuffer[Long]
    var t = timeFrom
    while(t <= timeTo) {
      timeSet += t
      t = t + ONE_MINUTE
    }
    timeSet
  }

  private def createCandle(timestamp: String, teaksOfOneTickerByMinute: List[Teak]): Candle = {
    val candle = Candle(
      ticker = teaksOfOneTickerByMinute.head.ticker,
      timestamp = timestamp,//new DateTime(teaksOfOneTicker.minBy(_.timestamp).timestamp).toString("yyyy-MM-dd'T'HH:mm:ss"),
      open = teaksOfOneTickerByMinute.minBy(_.timestamp).price,
      high = teaksOfOneTickerByMinute.maxBy(_.price).price,
      low = teaksOfOneTickerByMinute.minBy(_.price).price,
      close = teaksOfOneTickerByMinute.maxBy(_.timestamp).price,
      volume = teaksOfOneTickerByMinute.map(_.volume).sum)
    candle
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
