package com;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.http", "com.pe"})
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
@MapperScan(basePackages = {"com..pe.*"})
public class PEServiceStart
{
    public static void main(String[] args) {
            SpringApplication.run(PEServiceStart.class, args);
    }

    @Bean
    public MapperScannerConfigurer apperScannerConfigurer()
    {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.**.dao");
        return mapperScannerConfigurer;
    }
}
