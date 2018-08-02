package com.catran.trading.netty.server

import io.netty.channel.group.DefaultChannelGroup
import io.netty.channel.{ChannelHandlerContext, ChannelInboundMessageHandlerAdapter}
import io.netty.handler.codec.http.websocketx._
import org.apache.log4j.Logger

import scala.collection.JavaConverters._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by Administrator on 7/30/2018.
  */
class WebSocketServerHandler extends ChannelInboundMessageHandlerAdapter[String]{
  private var handshaker: WebSocketServerHandshaker = _
  private val logger = Logger.getLogger(getClass)

  val channels = new DefaultChannelGroup()
  sendLoop

  override def messageReceived(ctx: ChannelHandlerContext, msg: String): Unit = {
    val incoming = ctx.channel()
    for(channel <- channels.asScala) {
      if(channel != incoming) {
        channel.write(s"[ ${incoming.remoteAddress()} ]: $msg \n")
//        channel.flush()
      }
    }
  }

  override def handlerAdded(ctx: ChannelHandlerContext): Unit = {
    val channel = ctx.channel()
    for(channel <- channels.asScala) {
      channel.write(s"${channel.remoteAddress()} was connected\n")
//      channel.flush()
    }
    channels.add(ctx.channel())
  }

  override def handlerRemoved(ctx: ChannelHandlerContext): Unit = {
    val channel = ctx.channel()
    for(channel <- channels.asScala) {
      channel.write(s"${channel.remoteAddress()} was left\n")
      channel.flush()
    }
    channels.remove(ctx.channel())
  }

  private def sendLoop: Unit = Future{
    var i = 0
    while(true) {
      for(channel <- channels.asScala) {
        channel.write(s"hello: ${i} \n")
      }
      Thread.sleep(2000)
    }
  }
}
