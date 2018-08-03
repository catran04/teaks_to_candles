package com.catran.trading.netty.client


import com.catran.trading.dao.teakDao.TeakDao
import com.catran.trading.options.ApplicationOptions
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
/**
  * Created by Administrator on 7/31/2018.
  */
class TeakInitializer(options: ApplicationOptions, teakDao: TeakDao) extends ChannelInitializer[SocketChannel]{
  override def initChannel(ch: SocketChannel): Unit = {
    val pipeline = ch.pipeline()
    pipeline.addLast("handler", new TeakHandlerJava(teakDao))
  }
}
