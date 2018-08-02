package com.catran.trading.netty.client


import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
/**
  * Created by Administrator on 7/31/2018.
  */
class ClientHandler extends SimpleChannelInboundHandler[String]{


  override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
    println("I'm here")
    println(s"msg: ${msg}")
    println(s"${ctx.channel().remoteAddress()}")
  }

  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
    println(s"msg: ${msg.toString}")
  }
}
