package com.catran.trading.receiver

import com.catran.trading.model.Teak

import scala.collection.mutable

class TCPReceiver extends  Receiver {

  private val teaks = mutable.MutableList[Teak]()

  override def getTeaks: List[Teak] = teaks.toList

}
