package com.catran.trading.netty.server

import io.netty.channel.group.DefaultChannelGroup
import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.handler.codec.http.websocketx._
import io.netty.util.concurrent.DefaultEventExecutor
import org.apache.log4j.Logger
import scala.collection.JavaConverters._

/**
  * Created by Administrator on 7/30/2018.
  */
class WebSocketServerHandler extends SimpleChannelInboundHandler[String]{
  private var handshaker: WebSocketServerHandshaker = _
  private val logger = Logger.getLogger(getClass)

  val channels = new DefaultChannelGroup(new DefaultEventExecutor())

  override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
    println(s"from client: ${msg}. address: ${ctx.channel().remoteAddress()}")
    val cf1 = ctx.writeAndFlush("hello from ctx")
    val cf2 = ctx.channel().writeAndFlush("hello from channel")
    println(s"future: ${cf1.await().channel()}")
    if(!cf1.isSuccess) {
      println(s"send failed ${cf1.cause()}")
    }
    if(!cf2.isSuccess) {
      println(s"send2 failed ${cf2.cause()}")
    }
    channels.asScala.foreach(channel => {
      println("remoteAddress"+channel.remoteAddress())
        channel.writeAndFlush("take 10 minutes read")
      })
  }

  override def handlerAdded(ctx: ChannelHandlerContext): Unit = {
    println(s"client: ${ctx.channel().remoteAddress()} was connected")
    ctx.channel().writeAndFlush("take 10 minutes of data")
    ctx.writeAndFlush("write and flush")
    channels.add(ctx.channel())
  }

  override def handlerRemoved(ctx: ChannelHandlerContext): Unit = {
    channels.remove(ctx.channel())
    println(s"${ctx.channel().remoteAddress()} was disconnected")
  }

}
