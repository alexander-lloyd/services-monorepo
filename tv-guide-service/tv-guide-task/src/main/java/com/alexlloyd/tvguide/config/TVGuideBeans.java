package com.alexlloyd.tvguide.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TVGuideBeans {
    private final MappingJackson2XmlHttpMessageConverter messageConverter;

    @Autowired
    public TVGuideBeans(MappingJackson2XmlHttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(List.of(messageConverter));
    }
}
