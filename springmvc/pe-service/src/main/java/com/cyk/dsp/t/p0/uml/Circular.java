package com.cyk.dsp.t.p0.uml;

public class Circular implements Graph{

    double radious;


    @Override
    public double getArea() {
        return Math.PI * Math.sqrt(radious);
    }

    @Override
    public double getPerimeter() {
        return Math.PI * radious * 2;
    }
}
