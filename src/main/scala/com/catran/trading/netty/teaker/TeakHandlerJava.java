package com.catran.trading.netty.teaker;

import com.catran.trading.dao.teakDao.TeakDao;
import com.catran.trading.model.Teak;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import org.apache.log4j.Logger;

public class TeakHandlerJava extends ChannelInboundMessageHandlerAdapter<Teak> {
    private TeakDao teakDao;
    private Logger logger = Logger.getLogger(getClass());

    public TeakHandlerJava(TeakDao teakDao) {
        this.teakDao = teakDao;
    }
    @Override
    public void messageReceived(ChannelHandlerContext ctx, Teak teak) throws Exception {
        try {
            logger.info(teak);
            teakDao.setTeak(teak);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
