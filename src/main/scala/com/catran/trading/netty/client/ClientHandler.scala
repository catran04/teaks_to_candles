package com.catran.trading.netty.client


import io.netty.channel.{ChannelHandlerContext, ChannelInboundMessageHandlerAdapter}
import org.apache.log4j.Logger

class ClientHandler extends ChannelInboundMessageHandlerAdapter[String]{
  private val logger = Logger.getLogger(getClass)

  override def messageReceived(ctx: ChannelHandlerContext, msg: String): Unit = logger.info(msg)

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = logger.error(cause)
}
