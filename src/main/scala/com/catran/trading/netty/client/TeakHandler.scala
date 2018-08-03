package com.catran.trading.netty.client

import com.catran.trading.dao.teakDao.TeakDao
import com.catran.trading.model.Teak
import io.netty.channel.{ChannelHandlerContext, ChannelInboundMessageHandlerAdapter}

class TeakHandler(teakDao: TeakDao) extends ChannelInboundMessageHandlerAdapter[String]{


  override def messageReceived(ctx: ChannelHandlerContext, msg: String): Unit = {
    try {
      println("msg: " + msg)
      val teak = Teak.fromJson(msg)
      teakDao.setTeak(teak)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
