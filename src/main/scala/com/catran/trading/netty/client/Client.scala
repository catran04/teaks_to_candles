package com.catran.trading.netty.client

import java.io.{BufferedReader, InputStreamReader}

import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel

/**
  * Created by Administrator on 7/30/2018.
  */
class Client(host: String, port: Int) {

  def run(): Unit = {
    val group = new NioEventLoopGroup()
    try {
      val bootstrap = new Bootstrap()
        .group(group)
        .channel(classOf[NioSocketChannel])
        .handler(new ClientInitializer())

      val channel = bootstrap.connect(host, port).sync().channel()
      println(s"Client was connect to:${host}:${port}. ")
      val in = new BufferedReader(new InputStreamReader(System.in))
//      while(true) {
//        channel.write(in.readLine() + "\r\n")
//        channel.flush()
//      }
      in.readLine()
    } finally {
      group.shutdownGracefully()
    }
  }
}

object Client {
  def main(args: Array[String]): Unit = {
    new Client("localhost", 5555).run()
  }
}
