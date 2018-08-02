package com.catran.trading.netty.client

import java.io.{BufferedReader, InputStreamReader}

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelHandler
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel

/**
  * Created by Administrator on 7/30/2018.
  */
class Client(host: String, port: Int, handler: ChannelHandler) {
  val hand = new ClientInitializer(handler)

  def run(): Unit = {
    val group = new NioEventLoopGroup()
    try {
      val bootstrap = new Bootstrap()
        .group(group)
        .channel(classOf[NioSocketChannel])
        .handler(handler)

      val channel = bootstrap.connect(host, port).sync().channel()
      val in = new BufferedReader(new InputStreamReader(System.in))
      while(true) {
        channel.write(in.readLine() + "\r\n")
      }
    } finally {
      group.shutdownGracefully()
    }
  }
}

object Client {
  def main(args: Array[String]): Unit = {
    new Client("localhost", 5555, new ClientHandler()).run()
  }
}
