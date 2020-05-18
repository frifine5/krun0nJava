package com.netty.t;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.util.concurrent.CountDownLatch;


public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    private CountDownLatch latch;

    public ClientInitializer(CountDownLatch latch) {
        this.latch = latch;
    }

    private WebSocketClientHandler handler;

    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        handler = new WebSocketClientHandler(latch);
        ChannelPipeline p = sc.pipeline();
        p.addLast(new ChannelHandler[]{new HttpClientCodec(),
                new HttpObjectAggregator(1024 * 1024 * 10)});
        p.addLast("websocketHandler", handler);
    }

    public String getServerResult() {
        return handler.getResult();
    }

    public void resetLathc(CountDownLatch latch) {
        handler.resetLatch(latch);
    }

    public void setHandler(WebSocketClientHandler handler) {
        this.handler = handler;
    }
}