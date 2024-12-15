package ru.gav.creditbank.deal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${links.urls.calculator-host}")
    private String calcHost;

    @Bean(name = "calculatorWebClient")
    public WebClient calculatorWebClient(){
        return WebClient.builder().baseUrl(calcHost).build();
    }
}
