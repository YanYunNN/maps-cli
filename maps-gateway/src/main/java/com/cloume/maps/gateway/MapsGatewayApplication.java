package com.cloume.maps.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.cloume.maps.gateway.service")
public class MapsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapsGatewayApplication.class, args);
    }

}
