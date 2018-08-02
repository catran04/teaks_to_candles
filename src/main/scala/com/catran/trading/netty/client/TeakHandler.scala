package com.catran.trading.netty.client

import io.netty.channel.{ChannelHandlerContext, ChannelInboundMessageHandlerAdapter}

class TeakHandler extends ChannelInboundMessageHandlerAdapter[String]{


  override def messageReceived(ctx: ChannelHandlerContext, msg: String): Unit = {
    try {
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
