package com.catran.trading.model

import org.json4s.{NoTypeHints, native}
import org.json4s.native.Serialization

/**
  * Created by Administrator on 7/30/2018.
  */
case class Candle(
                   ticker: String,
                   timestamp: String,
                   open: Double,
                   high: Double,
                   low: Double,
                   close: Double,
                   volume: Int
                 ) {
  override def toString: String = {
    implicit val formats = native.Serialization.formats(NoTypeHints)
    Serialization.write[Candle](this)
  }
}
