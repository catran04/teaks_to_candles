package com.catran.trading.netty.client


import com.catran.trading.dao.teakDao.SQLiteTeakDao
import com.catran.trading.sql.sq_lite.SQLiteConnector
import io.netty.channel.socket.SocketChannel
import io.netty.channel.{ChannelHandler, ChannelInitializer}
/**
  * Created by Administrator on 7/31/2018.
  */
class ClientInitializer(handler: ChannelHandler) extends ChannelInitializer[SocketChannel]{
  override def initChannel(ch: SocketChannel): Unit = {
    val pipeline = ch.pipeline()

//    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter(): _*))
//    pipeline.addLast("decoder", new StringDecoder())
//    pipeline.addLast("encoder", new StringEncoder())
    pipeline.addLast("handler", new TeakHandlerJava(new SQLiteTeakDao(new SQLiteConnector())))
  }
}
