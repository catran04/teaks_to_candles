package com.catran.trading.receiver

import com.catran.trading.model.Teak
import com.catran.trading.netty.client.{Client, TeakHandler}

class TCPReceiver(client: Client) extends Receiver {


  override def getTeaks: List[Teak] = {
    client.hand.hand.asInstanceOf[TeakHandler].getTeaks
  }

}

object Main {
  def main(args: Array[String]): Unit = {
    println(new TCPReceiver())
  }
}