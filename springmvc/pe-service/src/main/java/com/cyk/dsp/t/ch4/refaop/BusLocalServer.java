package com.cyk.dsp.t.ch4.refaop;

import java.util.Date;

public class BusLocalServer implements IBusServer {
    @Override
    public String business(String content) {
        System.out.println("do business: content = " + content);
        return String.format("do business - " + content + "\t in time: " + new Date());
    }
}
