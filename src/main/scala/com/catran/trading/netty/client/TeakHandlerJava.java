package com.catran.trading.netty.client;

import com.catran.trading.dao.teakDao.TeakDao;
import com.catran.trading.model.Teak;
import com.catran.trading.util.ByteDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TeakHandlerJava extends SimpleChannelInboundHandler<ByteBuf> {
    TeakDao teakDao;

    public TeakHandlerJava(TeakDao teakDao) {
        this.teakDao = teakDao;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = getByteArrayFromByteBuffer(msg);
        try {
            Teak teak = ByteDecoder.apply(bytes);
            teakDao.setTeak(teak);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] getByteArrayFromByteBuffer(ByteBuf buf) {
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        return bytes;
    }
}
