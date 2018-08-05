package com.catran.trading.netty.server


import com.catran.trading.dao.teakDao.SQLiteTeakDao
import com.catran.trading.netty.client.TeakHandler
import com.catran.trading.options.ApplicationOptions
import com.catran.trading.sql.sq_lite.SQLiteConnector
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.{LogLevel, LoggingHandler}
import org.apache.log4j.Logger


class Server(port: Int, teakHandler: TeakHandler) {
  private val logger = Logger.getLogger(getClass)

  def run(): Unit = {
    val bossGroup = new NioEventLoopGroup(1)
    val workerGroup = new NioEventLoopGroup()
    try {
      val bootstrap = new ServerBootstrap()
      bootstrap
        .group(bossGroup, workerGroup)
        .channel(classOf[NioServerSocketChannel])
        .handler(new LoggingHandler(LogLevel.INFO))
        .childHandler(new ServerInitializer(teakHandler))

      bootstrap.bind(port).sync().channel().closeFuture().sync()
      logger.error(s"Server was started on port: ${port}")
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
    val teakHandler = new TeakHandler(teakDao)
    new Server(port = options.serverPort, teakHandler).run()
  }
}