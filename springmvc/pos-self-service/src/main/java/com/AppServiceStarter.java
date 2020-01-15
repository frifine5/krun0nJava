package com;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//        (scanBasePackages = {"com.http" })
@ComponentScan("com.*")
@MapperScan(basePackages = {"com.pos.*.dao.*"})
public class AppServiceStarter {


    public static void main(String[] args) {
        SpringApplication.run(AppServiceStarter.class, args);
        System.out.println("starting...");
    }

}
