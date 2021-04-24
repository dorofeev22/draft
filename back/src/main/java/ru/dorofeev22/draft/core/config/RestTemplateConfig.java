package ru.dorofeev22.draft.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        // todo: configure
        return new RestTemplate();
    }

}