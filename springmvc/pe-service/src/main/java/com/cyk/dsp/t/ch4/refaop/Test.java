package com.cyk.dsp.t.ch4.refaop;

public class Test {

    public static void main(String[] args) {

        IBusServer server = (IBusServer)new  BusProxyInvocation()
                .bind(new BusLocalServer(), new BusAopOptHandler());

        server.business("abc");


    }



}
