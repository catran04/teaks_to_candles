package com.catran.trading.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;


public class ChatServerHandler extends ChannelInboundMessageHandlerAdapter<String> {

    //private static final String name = "NEWTUN";
    private static final ChannelGroup channels = new DefaultChannelGroup();
    Object obj = sendLoop();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.write("[SERVER] - " + incoming.remoteAddress() + " has joined!");
        }
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.write("[SERVER] - " + incoming.remoteAddress() + " has left!");
        }
        channels.remove(ctx.channel());
    }

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel incoming = channelHandlerContext.channel();
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.write("[ " + incoming.remoteAddress() + " ]" + ": " + s + " : \n");
            }
        }
    }

    private Object sendLoop() {
        Thread thread = new Thread(() -> {
            while (true) {
                for (Channel channel : channels) {
                    channel.write("here you are\n");
                    channel.flush();
                }
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        return null;
    }
}