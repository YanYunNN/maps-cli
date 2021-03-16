package com.cloume.maps.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class MapsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapsAuthApplication.class, args);
    }

}
