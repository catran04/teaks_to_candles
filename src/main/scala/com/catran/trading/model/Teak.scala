package com.catran.trading.model

import org.json4s.{NoTypeHints, native}
import org.json4s.native.Serialization

/**
  * Created by Administrator on 7/30/2018.
  */
case class Teak(
                 timestamp: Long,
                 ticker: String,
                 price: Double,
                 volume: Int
               ) {

}

object Teak {
  def fromJson(json: String): Teak = {
    implicit val formats = native.Serialization.formats(NoTypeHints)
    Serialization.read[Teak](json)
  }
}
