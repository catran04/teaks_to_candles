package com.catran.trading.netty.client

import com.catran.trading.model.Teak
import io.netty.channel.{ChannelHandlerContext, ChannelInboundMessageHandlerAdapter}

import scala.collection.mutable

class TeakHandler extends ChannelInboundMessageHandlerAdapter[String]{

  private val teaks: mutable.MutableList[String] = mutable.MutableList[String]()

  def getTeaks: List[Teak] = {
    val teakString = teaks
    teakString.map(Teak.fromJson).toList
  }

  override def messageReceived(ctx: ChannelHandlerContext, msg: String): Unit = {
      teaks += msg
  }
}
