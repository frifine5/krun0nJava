package com.cyk.dsp.t.p0.uml;

public class Rectangle implements Graph{

    double length;
    double width;

    @Override
    public double getArea() {
        return length * width;
    }

    @Override
    public double getPerimeter() {
        return (length + width) * 2;
    }
}
