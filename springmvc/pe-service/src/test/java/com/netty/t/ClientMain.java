package com.netty.t;

import com.common.utils.ParamsUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;

public class ClientMain {

    public static void main(String[] args) throws Exception{
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap boot=new Bootstrap();
        boot.option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .group(group)
                .handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast(new ChannelHandler[]{new HttpClientCodec(),
                                new HttpObjectAggregator(1024*1024*10)});
                        p.addLast("hookedHandler", new WebSocketClientHandler());
                    }
                });
        URI websocketURI = new URI("ws://localhost:11001/mms/" + ParamsUtil.getUUIDStr());
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        //进行握手
        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(websocketURI, WebSocketVersion.V13, (String)null, true,httpHeaders);
        System.out.println("connect");
        final Channel channel=boot.connect(websocketURI.getHost(),websocketURI.getPort()).sync().channel();
        WebSocketClientHandler handler = (WebSocketClientHandler)channel.pipeline().get("hookedHandler");
        handler.setHandshaker(handshaker);
        handshaker.handshake(channel);
        //阻塞等待是否握手成功
        handler.handshakeFuture().sync();

        Thread text=new Thread(new Runnable() {
            public void run() {
                int i=1;
                while (i>0){
                    try{
                        System.out.println("text send");
                        JSONObject reqJson = new JSONObject();
                        String ctx = Base64.getEncoder().encodeToString("testdata-111111111111111111L".getBytes("UTF-8"));

                        String svData = "{\"code\":0,\"msg\":\"SUCCESS\",\"data\":\"MEQCIAvdBjEBMrvlXcVhPFJJRXQ2GndxFNSRrOjKLLCBbiprAiBMrlpwQMYOkLX9BKXdbyrkxMwZ+fV0iZRb1vbkxuXU+A==\"}";
                        JSONObject svRtnJson = JSONObject.fromObject(svData);
                        String data = svRtnJson.getString("data");

                        reqJson.put("function", "eccVerify");
                        reqJson.put("index", 1);
                        reqJson.put("data", ctx);
                        reqJson.put("sv", data);
                        reqJson.put("pkPre", true);


                        TextWebSocketFrame frame = new TextWebSocketFrame(reqJson.toString());
                        channel.writeAndFlush(frame).addListener(new ChannelFutureListener() {
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                if(channelFuture.isSuccess()){
                                    System.out.println("text send success");
                                }else{
                                    System.out.println("text send failed  "+channelFuture.cause().getMessage());
                                }
                            }
                        });


                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    i--;
                }

            }
        });

        text.start();



    }
}


