package com.catran.trading.netty.new_server;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.sql.SQLOutput;


public class ChatServerHandler extends ChannelInboundMessageHandlerAdapter<String> {

    //private static final String name = "NEWTUN";
    private static final ChannelGroup channels = new DefaultChannelGroup();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "was added");
        Channel incoming = ctx.channel();
        for (Channel channel: channels){
            channel.write("[SERVER] - " + incoming.remoteAddress() + " has joined!");
        }
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println(ctx.channel().remoteAddress() + "was removed");
        for (Channel channel: channels){
            channel.write("[SERVER] - " + incoming.remoteAddress() + " has left!");
        }
        channels.remove(ctx.channel());
    }

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(channelHandlerContext.channel().remoteAddress() + "wrote" + s);
        Channel incoming = channelHandlerContext.channel();
        for (Channel channel: channels){
            if(channel != incoming){
                channel.write("[ "+incoming.remoteAddress()+" ]" + ": " + s + " : \n");
            }
        }
    }
}