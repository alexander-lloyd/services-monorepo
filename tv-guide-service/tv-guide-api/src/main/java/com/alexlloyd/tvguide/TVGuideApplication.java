package com.alexlloyd.tvguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TVGuideApplication {
    public static void main(String[] args) {
        SpringApplication.run(TVGuideApplication.class, args);
    }
}
