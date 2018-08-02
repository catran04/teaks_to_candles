package com.catran.trading.netty.server


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

      println("I'm here0")
      val fut = bootstrap.bind(port).sync().channel().closeFuture().sync()
      while(true) {
        println("I'm here")
        fut.channel().write("asdf")
        Thread.sleep(2000)
      }
    } finally {
      bossGroup.shutdownGracefully()
      workerGroup.shutdownGracefully()
    }
  }
}

object Server {
  def main(args: Array[String]): Unit = {
    new Server(5555).run()
  }
}