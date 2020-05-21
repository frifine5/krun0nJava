package com.netty.t;

import com.common.utils.ParamsUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebSocketClient {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketClient.class);
    private static ExecutorService executor = Executors.newCachedThreadPool();

    private static String uri ;
    private static CountDownLatch latch;
    private static ClientInitializer clientInitializer;

    private WebSocketClient(){
    }
    public WebSocketClient(String uri){
        this.uri = uri;
        latch = new CountDownLatch(0);
        clientInitializer = new ClientInitializer(latch);
    }

    private static class SingletonHolder {
        static final WebSocketClient instance = new WebSocketClient();
    }
    public static WebSocketClient getInstance(){
        return SingletonHolder.instance;
    }


    public static String pushMsg(String content) throws URISyntaxException, InterruptedException {

        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap boot=new Bootstrap();
        boot.option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.SO_BACKLOG,1024*1024*10)
                .group(group)
                .handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer(latch));
        URI websocketURI = new URI(uri);
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        //进行握手
        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(websocketURI, WebSocketVersion.V13, (String)null, true,httpHeaders);
        logger.info("connect to server....");
        final Channel channel=boot.connect(websocketURI.getHost(),websocketURI.getPort()).sync().channel();
        WebSocketClientHandler handler = (WebSocketClientHandler)channel.pipeline().get("websocketHandler");
        handler.setHandshaker(handshaker);
        handshaker.handshake(channel);
        //阻塞等待是否握手成功
        handler.handshakeFuture().sync();
        return sendMsg(channel,handler,content);
    }

    public static String sendMsg(Channel channel, WebSocketClientHandler handler, String content) throws InterruptedException {
        logger.info("send msg:" + content);
        //发起线程发送消息
        executor.submit(new WebSocketCallable(channel, content));
        latch = new CountDownLatch(1);
        clientInitializer.setHandler(handler);
        clientInitializer.resetLathc(latch);
        //等待，当websocket服务端返回数据时唤醒屏障，并返回结果
        latch.await();
        return clientInitializer.getServerResult();

    }

    public static void main(String[] args) throws URISyntaxException, InterruptedException, UnsupportedEncodingException {
        String result = "";
        String wsIpUrl = "ws://localhost:11001/mms/" + ParamsUtil.getUUIDStr();
        wsIpUrl = "ws://192.168.6.238:11003/mms/" + ParamsUtil.getUUIDStr();


        WebSocketClient webSocketClient = new WebSocketClient(wsIpUrl);

        JSONObject reqJson = new JSONObject();
        String ctx = Base64.getEncoder().encodeToString("testdata-111111111111111111L".getBytes("UTF-8"));

        String svData = "{\"code\":0,\"msg\":\"SUCCESS\",\"data\":\"MEYCIQCgVvWrHl0GTWP4E2LaN0H2fMPvETCuVHdKSMdf0nbOJwIhAPQmdtFuGUbRaDcIYCkRC+iFSW8gNWa/0CZ3JfO1k2xp\"}";


        JSONObject svRtnJson = JSONObject.fromObject(svData);
        String data = svRtnJson.getString("data");

        reqJson.put("function", "eccVerify");
        reqJson.put("index", 1);
        reqJson.put("data", ctx);
        reqJson.put("sv", data);
        reqJson.put("pkPre", true);

        result = webSocketClient.pushMsg(reqJson.toString()); // 返回结果
        System.out.println("receive: " + result);




    }

}
