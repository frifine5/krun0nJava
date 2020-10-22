package com.netty.t1;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class N1Decoder extends ByteToMessageDecoder {

    //目标对象类型进行解码
    private Class<?> target;

    public N1Decoder(Class target) {
        this.target = target;
    }

    /**
     * 重写解码器
     */
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {   // 长度不够，废弃
            return;
        }
        in.markReaderIndex();           // 标记当前的readIndex的位置
        int dataLength = in.readInt();
        if(in.readableBytes() < dataLength){ // 读到的消息长度小于发过来的
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        // 按json转码

        Object obj = JSON.parseObject(data, target); //将byte数据转化为我们需要的对象
        out.add(obj);
    }


}
