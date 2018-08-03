package com.catran.trading.netty.server

import io.netty.channel.group.DefaultChannelGroup
import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.handler.codec.http.websocketx._
import io.netty.util.concurrent.DefaultEventExecutor
import org.apache.log4j.Logger

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
/**
  * Created by Administrator on 7/30/2018.
  */
class WebSocketServerHandler extends SimpleChannelInboundHandler[String]{
  private var handshaker: WebSocketServerHandshaker = _
  private val logger = Logger.getLogger(getClass)

  val channels = new DefaultChannelGroup(new DefaultEventExecutor())
  sendLoop()

  override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
    println(s"message rec: ${msg}")
  }

  override def handlerAdded(ctx: ChannelHandlerContext): Unit = {
    val ch = ctx.channel()
    ch.write("10 minutes of data")
    channels.add(ctx.channel())
  }

  override def handlerRemoved(ctx: ChannelHandlerContext): Unit = {
    channels.remove(ctx.channel())
  }

  private def sendLoop(): Unit = Future{
    var i = 0
    while(true) {
      for(channel <- channels.asScala) {
        channel.write(s"hello: ${i} \n")
      }
      i += 1
      Thread.sleep(2000)
    }
  }
}
