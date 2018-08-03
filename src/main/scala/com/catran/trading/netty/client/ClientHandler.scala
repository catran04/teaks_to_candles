package com.catran.trading.netty.client


import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
/**
  * Created by Administrator on 7/31/2018.
  */
class ClientHandler extends SimpleChannelInboundHandler[String]{
  override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
    println(msg)
  }
}
