package com.cyk.dsp.t.p0.uml;

public class TClient {

    public static void main(String[] args) {

        Graph a = new Rectangle();
        ((Rectangle) a).length = 10;
        ((Rectangle) a).width = 6;

        Graph b = new Circular();
        ((Circular) b).radious = 12;

        Graph c = new Circular();
        ((Circular) c).radious = 6;


        calculate(a);
        calculate(b);
        calculate(c);



    }


    static void calculate(Graph g){
        if(g instanceof Circular){
            System.out.println("图形是圆");
        }else if( g instanceof Rectangle){
            System.out.println("图形是方");
        }else{
            System.out.println("图形未知");
        }

        String pln = String.format("面积 = %s,  周长 = %s", g.getArea(), g.getPerimeter());
        System.out.println(pln);

    }


}
