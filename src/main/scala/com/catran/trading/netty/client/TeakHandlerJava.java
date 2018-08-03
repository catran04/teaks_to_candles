package com.catran.trading.netty.client;

import com.catran.trading.dao.teakDao.TeakDao;
import com.catran.trading.util.ByteDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class TeakHandlerJava extends SimpleChannelInboundHandler<ByteBuf> {
    TeakDao teakDao;

    public TeakHandlerJava(TeakDao teakDao) {
        this.teakDao = teakDao;
    }
    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("hwlo");
        System.out.println("instanse: " + msg.getClass());
        byte[] bytes = new byte[msg.readableBytes()];
        System.out.println("msg length: " + bytes.length);
        try {
            ByteDecoder.apply(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ByteBuffer buf = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 1));
        short length = buf.getShort();
        System.out.println("short length: " + length);
//        try {
//            Teak teak = Teak.fromJson(u);
//            teakDao.setTeak(teak);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
    }
}
