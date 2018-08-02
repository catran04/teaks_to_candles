package com.catran.trading.netty.client


import com.catran.trading.model.Teak
import io.netty.channel.{ChannelHandlerContext, ChannelInboundMessageHandlerAdapter}

import scala.collection.parallel.mutable
/**
  * Created by Administrator on 7/31/2018.
  */
class ClientHandler extends ChannelInboundMessageHandlerAdapter[String]{

  val teaks = mutable.ParSeq[Teak]()

  override def messageReceived(ctx: ChannelHandlerContext, msg: String): Unit = {
    println(msg)
  }
}
