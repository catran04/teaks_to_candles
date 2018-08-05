package com.catran.trading.aggregator

import com.catran.trading.model.{Candle, Teak}
import com.catran.trading.util.TimeUtil
import org.joda.time.{DateTime, DateTimeZone}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object TeakAggregator {

  private val ONE_MINUTE = 60000

  def createCandles(teaks: List[Teak]): List[Candle] = {
    val groupedByTicker: Map[String, List[Teak]] = teaks.groupBy(_.ticker) // groups by ticker
    val groupedByMinuteAndTicker =
      groupedByTicker.par.map { case (k, v) => groupByMinute(v) } //groups by one minute
    val minutesTeaks = for {
      minuteTeaks <- groupedByMinuteAndTicker
      minuteTeaksByOneMinute <- minuteTeaks
    } yield minuteTeaksByOneMinute // flatten our groupByMinuteAndTicker
    minutesTeaks.par.map { case (timestamp, teaks) => createCandle(timestamp, teaks) }.toList
  }

  private[aggregator] def groupByMinute(teaksOfOneTicker: List[Teak]): Map[String, List[Teak]] = {
    DateTimeZone.setDefault(DateTimeZone.UTC)
    val minTime = teaksOfOneTicker.minBy(_.timestamp).timestamp
    val maxTime = teaksOfOneTicker.maxBy(_.timestamp).timestamp

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

  private[aggregator] def getMinuteTimeRange(timeFrom: Long, timeTo: Long): Seq[Long] = {
    val timeSet = new ArrayBuffer[Long]
    var t = timeFrom
    while (t <= timeTo) {
      timeSet += t
      t = t + ONE_MINUTE
    }
    timeSet
  }

  private[aggregator] def createCandle(timestamp: String, teaksOfOneTickerByMinute: List[Teak]): Candle = {
    Candle(
      ticker = teaksOfOneTickerByMinute.head.ticker,
      timestamp = timestamp,
      open = teaksOfOneTickerByMinute.minBy(_.timestamp).price,
      high = teaksOfOneTickerByMinute.maxBy(_.price).price,
      low = teaksOfOneTickerByMinute.minBy(_.price).price,
      close = teaksOfOneTickerByMinute.maxBy(_.timestamp).price,
      volume = teaksOfOneTickerByMinute.map(_.volume).sum)
  }

  /*    private def getPrices(teaksOfOneTicker: Vector[Teak]): CandlePrices = {
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
      }
    case class CandlePrices(
                             open: Double,
                             high: Double,
                             low: Double,
                             close: Double,
                             volume: Long
                           )*/
}
