package com.catran.trading.util

import java.nio.ByteBuffer

import com.catran.trading.model.Teak

object ByteDecoder {
  def apply(bytes: Array[Byte]): Teak = {

    val timestamp = getLong(bytes.slice(2, 10))
    val tickerLength = getShort(bytes.slice(10, 12))
    val ticker = new String(bytes.slice(12, 12 + tickerLength))
    val price = getDouble(bytes.slice(12 + tickerLength, 12 + tickerLength + 8))
    val volume = getInt(bytes.slice(12 + tickerLength + 8, 12 + tickerLength + 8 + 4))

    Teak(
      timestamp = timestamp,
      ticker = ticker,
      price = price,
      volume = volume
    )
  }

  private def getDouble(bytes: Array[Byte]): Double = ByteBuffer.wrap(bytes).getDouble()

  private def getShort(bytes: Array[Byte]): Short = ByteBuffer.wrap(bytes).getShort()

  private def getInt(bytes: Array[Byte]): Int = ByteBuffer.wrap(bytes).getInt()

  private def getLong(bytes: Array[Byte]): Long = ByteBuffer.wrap(bytes).getLong()

  private def getString(bytes: Array[Byte]): String = ""


}
