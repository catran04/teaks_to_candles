package com.catran.trading.netty.server

import com.catran.trading.aggregator.TeakAggregator
import com.catran.trading.dao.teakDao.TeakDao
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
class WebSocketServerHandler(teakDao: TeakDao) extends ChannelInboundMessageHandlerAdapter[String]{

  private val logger = Logger.getLogger(getClass)
  private val ONE_MINUTE = 60000L
  private val TEN_MINUTES = ONE_MINUTE * 10

  val channels = new DefaultChannelGroup()
  sendLoop()

  override def messageReceived(ctx: ChannelHandlerContext, msg: String): Unit = {
    println(s"message rec: ${msg}")
  }

  override def handlerAdded(ctx: ChannelHandlerContext): Unit = {
    val to = TimeUtil.truncSeconds(System.currentTimeMillis())
    val from = to - TEN_MINUTES
    val teaks = teakDao.getTeaks(from, to)
    println(s"teaks length: ${teaks.length}")

    val candles = TeakAggregator.createCandles(teaks.toList).sortBy(_.timestamp)
    println(candles.mkString("\n"))
    candles.foreach(candle => ctx.write(candle + "\r\n"))
    channels.add(ctx.channel())
  }

  override def handlerRemoved(ctx: ChannelHandlerContext): Unit = {
    channels.remove(ctx.channel())
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    cause.printStackTrace()
  }

  private def sendLoop(): Unit = Future{
    val t = System.currentTimeMillis()
    var nextMinute = TimeUtil.truncSeconds(new DateTime(t).plusMinutes(1).getMillis)
    println(s"nextMinute: ${new DateTime(nextMinute).toString("yyyy-MM-dd'T'HH:mm:ss")}")
    while(true) {
      if(System.currentTimeMillis() >= nextMinute) {
        for (channel <- channels.asScala) {
          val teaks = teakDao.getTeaks(nextMinute - ONE_MINUTE, nextMinute)
          val candles = TeakAggregator.createCandles(teaks.toList)
          println(s"candles in loop: ${candles.mkString("\n")}")
          candles.foreach(candle => channel.write(candle + "\r\n"))
        }
        nextMinute = nextMinute + ONE_MINUTE
      }
    }
  }


}
