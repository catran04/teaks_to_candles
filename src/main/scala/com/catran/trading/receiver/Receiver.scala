package com.catran.trading.receiver

import com.catran.trading.model.Teak

trait Receiver {
  def getTeaks: List[Teak]
}
