package com.catran.trading.netty.server

import io.netty.channel.group.DefaultChannelGroup
import io.netty.channel.{ChannelHandlerContext, ChannelInboundMessageHandlerAdapter}
import org.apache.log4j.Logger

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
/**
  * Created by Administrator on 7/30/2018.
  */
class WebSocketServerHandler() extends ChannelInboundMessageHandlerAdapter[String]{
  private val logger = Logger.getLogger(getClass)

  val channels = new DefaultChannelGroup()
  sendLoop()

  override def messageReceived(ctx: ChannelHandlerContext, msg: String): Unit = {
    println(s"message rec: ${msg}")
  }

  override def handlerAdded(ctx: ChannelHandlerContext): Unit = {


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
