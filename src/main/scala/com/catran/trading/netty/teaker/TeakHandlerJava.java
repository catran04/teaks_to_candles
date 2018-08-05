package com.catran.trading.netty.teaker;

import com.catran.trading.dao.teakDao.TeakDao;
import com.catran.trading.model.Teak;
import com.catran.trading.util.ByteDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import org.apache.log4j.Logger;

public class TeakHandlerJava extends ChannelInboundByteHandlerAdapter {
    private TeakDao teakDao;
    private Logger logger = Logger.getLogger(getClass());

    public TeakHandlerJava(TeakDao teakDao) {
        this.teakDao = teakDao;
    }

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
            logger.info(teak);
            teakDao.setTeak(teak);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
