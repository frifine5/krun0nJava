package com.netty.t1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.util.UUID;

public class N1ServerHandler extends ChannelInboundHandlerAdapter {


    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 获取客户端ip和端口
        InetSocketAddress cli = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = cli.getAddress().getHostAddress();
        String clientPort = String.valueOf(cli.getPort());
        System.out.println(String.format("客户终端：%s : %s", clientIP, clientPort ) );

        // 接收的信息
        RpcRequest request = (RpcRequest) msg;
        System.out.println("接收到客户端信息:" + request.toString());

        //返回的数据结构
        RpcResponse response = new RpcResponse();
        response.setId(UUID.randomUUID().toString());
        response.setData("server响应结果");
        response.setStatus(1);
        ctx.writeAndFlush(response);
    }


    //通知处理器最后的channelRead()是当前批处理中的最后一条消息时调用
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接收数据完毕..");
        ctx.flush();
    }

    //读操作时捕获到异常时调用
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    //客户端去和服务端连接成功时触发
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush("hello client");
    }


}
