package com.raenjamio.config;

import com.raenjamio.service.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestBeans {

    private RestProperties restProperties;

    public RestBeans(RestProperties restProperties) {
        this.restProperties = restProperties;
    }

    @Bean
    public RestClient restClient() {
        return new RestClient(restProperties.getUrl(), restProperties.getForecastUrl(), null);
    }
}
