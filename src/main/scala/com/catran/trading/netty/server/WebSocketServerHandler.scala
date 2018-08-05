package com.catran.trading.netty.server

import com.catran.trading.netty.client.TeakHandler
import com.catran.trading.util.TimeUtil
import io.netty.channel.group.DefaultChannelGroup
import io.netty.channel.{ChannelHandlerContext, ChannelInboundMessageHandlerAdapter}
import org.apache.log4j.Logger
import org.joda.time.DateTime

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
  * Created by Administrator on 7/30/2018.
  */
class WebSocketServerHandler(teakHandler: TeakHandler) extends ChannelInboundMessageHandlerAdapter[String]{

  private val logger = Logger.getLogger(getClass)
  private val ONE_MINUTE = 60000L
  private val TEN_MINUTES = ONE_MINUTE * 10

  private val channels = new DefaultChannelGroup()
  sendLoop()

  override def messageReceived(ctx: ChannelHandlerContext, msg: String): Unit = {
    logger.info(s"message rec: ${msg}")
  }

  override def handlerAdded(ctx: ChannelHandlerContext): Unit = {
    val to = TimeUtil.truncSeconds(System.currentTimeMillis())
    val from = to - TEN_MINUTES
    val candles = teakHandler.getCandles(from, to)
    candles.foreach(candle => ctx.write(candle + "\n"))
    channels.add(ctx.channel())
    logger.info(s"[SERVER] ${ctx.channel().remoteAddress()} was joined")
  }

  override def handlerRemoved(ctx: ChannelHandlerContext): Unit = {
    channels.remove(ctx.channel())
    logger.info(s"[SERVER] ${ctx.channel().remoteAddress()} was disconnected")
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    logger.error(cause)
  }

  private def sendLoop(): Unit = Future{
    val t = System.currentTimeMillis()
    var nextMinute = TimeUtil.truncSeconds(new DateTime(t).plusMinutes(1).getMillis)
    while(true) {
      if(System.currentTimeMillis() >= nextMinute) {
        for (channel <- channels.asScala) {
          val candles = teakHandler.getCandles(nextMinute - ONE_MINUTE, nextMinute)
          candles.foreach(candle => channel.write(candle + "\n"))
        }
        nextMinute = nextMinute + ONE_MINUTE
      }
    }
  }
}
