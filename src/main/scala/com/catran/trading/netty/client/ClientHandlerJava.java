package com.catran.trading.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

public class ClientHandlerJava extends ChannelInboundMessageHandlerAdapter<String> {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("channelRead0");
        System.out.println(msg);
    }
}
