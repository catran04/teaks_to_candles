package com.catran.trading.netty.server


import com.catran.trading.dao.teakDao.SQLiteTeakDao
import com.catran.trading.netty.client.{Client, TeakHandlerJava}
import com.catran.trading.options.ApplicationOptions
import com.catran.trading.sql.sq_lite.SQLiteConnector
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.{LogLevel, LoggingHandler}


/**
  * Created by Administrator on 7/30/2018.
  */
class Server(port: Int) {
  def run(): Unit = {
    val bossGroup = new NioEventLoopGroup(1)
    val workerGroup = new NioEventLoopGroup()
    try {
      val bootstrap = new ServerBootstrap()
      bootstrap
        .group(bossGroup, workerGroup)
        .channel(classOf[NioServerSocketChannel])
        .handler(new LoggingHandler(LogLevel.INFO))
        .childHandler(new ServerInitializer())

      bootstrap.bind(port).sync().channel().closeFuture().sync()
    } finally {
      bossGroup.shutdownGracefully()
      workerGroup.shutdownGracefully()
    }
  }
}

object Server {
  def main(args: Array[String]): Unit = {
    val options = ApplicationOptions(args)
    val teakDao = new SQLiteTeakDao(options, new SQLiteConnector())
    new Client(
      host = "localhost",
      port = 5555,
      handler = new TeakHandlerJava(teakDao),
      options = options).run()
//    new Server(port = options.serverPort).run()
  }
}