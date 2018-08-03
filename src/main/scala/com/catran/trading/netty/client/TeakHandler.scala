package com.catran.trading.netty.client

import com.catran.trading.dao.teakDao.TeakDao
import com.catran.trading.model.Teak
import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}

class TeakHandler(teakDao: TeakDao) extends SimpleChannelInboundHandler[String]{


  override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
    try {
      println("msg: " + msg)
      val teak = Teak.fromJson(msg)
      teakDao.setTeak(teak)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
