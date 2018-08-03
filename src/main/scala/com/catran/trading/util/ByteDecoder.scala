package com.catran.trading.util

import java.nio.ByteBuffer

object ByteDecoder {
  def apply(bytes: Array[Byte]): List[String] = {
    println("hello")
    System.out.println("SYs heloo")
    val vectorBytes = bytes.toVector.take(2)
    Short
    // 24 + ticker len
    println("Short " + ByteBuffer.wrap(vectorBytes.toArray).getShort())
    List.empty
  }

}
