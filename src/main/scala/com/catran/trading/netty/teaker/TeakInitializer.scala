package com.catran.trading.netty.teaker

import com.catran.trading.dao.teakDao.TeakDao
import com.catran.trading.options.ApplicationOptions
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

class TeakInitializer(options: ApplicationOptions, teakDao: TeakDao) extends ChannelInitializer[SocketChannel]{
  override def initChannel(ch: SocketChannel): Unit = {
    val pipeline = ch.pipeline()
    pipeline.addLast("handler", new TeakHandlerJava(teakDao))
  }
}
