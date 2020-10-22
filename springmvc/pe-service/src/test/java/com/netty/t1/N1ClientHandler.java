package com.netty.t1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class N1ClientHandler extends SimpleChannelInboundHandler<RpcResponse> {



    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        System.out.println(">>>>>> get msg from server: " + msg.toString());
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }


    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
