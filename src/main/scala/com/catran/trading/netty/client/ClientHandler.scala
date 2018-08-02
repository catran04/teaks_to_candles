package com.catran.trading.netty.client


import io.netty.channel.{ChannelHandlerContext, ChannelInboundMessageHandlerAdapter}
/**
  * Created by Administrator on 7/31/2018.
  */
class ClientHandler extends ChannelInboundMessageHandlerAdapter[String]{
  override def messageReceived(ctx: ChannelHandlerContext, msg: String): Unit = {
    println(msg)
  }
}
