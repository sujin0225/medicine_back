package com.medicine.medicine_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


public class restTemplate {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
