package com.catran.trading.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.DefaultEventExecutor;

public class ServerHandlerJava extends SimpleChannelInboundHandler<ByteBuf> {

    private DefaultChannelGroup channels = new DefaultChannelGroup(new DefaultEventExecutor());

    Thread t1 = new Thread(new Runnable() {
        public void run() {
            while (true) {
                for (Channel ch : channels) {
                    System.out.println("write a message" + ch.remoteAddress());
                    ch.write("here you are.");
                    ch.flush();
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        for(Channel ch: channels) {
            ch.write(ctx.channel().remoteAddress());
        }
        System.out.println("I'm here");
        t1.start();
        Channel ch = ctx.channel();
        ch.write("10 minutes of data");
        ch.flush();
        channels.add(ctx.channel());
    }
}
