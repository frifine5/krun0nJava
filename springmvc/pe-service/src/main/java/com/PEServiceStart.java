package com;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
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
@MapperScan(basePackages = {"com.pe.*"})
public class PEServiceStart {
    public static void main(String[] args) {
        SpringApplication.run(PEServiceStart.class, args);
    }

//    @Bean
//    public MapperScannerConfigurer apperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setBasePackage("com.**.dao");
//        return mapperScannerConfigurer;
//    }
//
//    @Bean
//    public EmbeddedServletContainerFactory servletContainer(){
//
//        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory(){
//
//            @Override
//            protected void postProcessContext(Context context) {
//
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");//confidential
//                securityConstraint.addCollection(collection);
//
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        return tomcat;
//    }
//
//    @Value("$server.port:8080")
//    int defPort;
//
//    @Bean
//    public Connector httpConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(defPort);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);
//        return connector;
//    }

}
