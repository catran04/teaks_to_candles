package com.catran.trading.netty.teaker

import com.catran.trading.util.ByteDecoder
import io.netty.buffer.{ByteBuf, MessageBuf}
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder


class TeakerDecoder extends ByteToMessageDecoder{
  override def decode(ctx: ChannelHandlerContext, in: ByteBuf, out: MessageBuf[AnyRef]): Unit = {
    val bytes = getByteArrayFromByteBuffer(in)
    try {
      val teak = ByteDecoder.apply(bytes)
      out.add(teak)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  private def getByteArrayFromByteBuffer(buf: ByteBuf) = {
    val bytes = new Array[Byte](buf.readableBytes)
    buf.readBytes(bytes)
    bytes
  }
}
