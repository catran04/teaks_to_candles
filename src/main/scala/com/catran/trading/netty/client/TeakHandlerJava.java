package com.catran.trading.netty.client;

import com.catran.trading.dao.teakDao.TeakDao;
import com.catran.trading.model.Teak;
import com.catran.trading.util.ByteDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import io.netty.channel.*;

public class TeakHandlerJava extends ChannelInboundByteHandlerAdapter {
    TeakDao teakDao;

    public TeakHandlerJava(TeakDao teakDao) {
        this.teakDao = teakDao;
    }

//    @Override
//    public void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//        byte[] bytes = getByteArrayFromByteBuffer(msg);
//        try {
//            Teak teak = ByteDecoder.apply(bytes);
//            System.out.println(teak);
//            teakDao.setTeak(teak);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private static byte[] getByteArrayFromByteBuffer(ByteBuf buf) {
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        return bytes;
    }

    @Override
    protected void inboundBufferUpdated(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        byte[] bytes = getByteArrayFromByteBuffer(in);
        try {
            Teak teak = ByteDecoder.apply(bytes);
            System.out.println(teak);
            teakDao.setTeak(teak);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
