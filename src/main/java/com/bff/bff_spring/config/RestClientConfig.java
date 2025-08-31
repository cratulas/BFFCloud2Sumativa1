package com.bff.bff_spring.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Configuration
public class RestClientConfig {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    RestTemplate rt = builder
        .setConnectTimeout(Duration.ofSeconds(5))
        .setReadTimeout(Duration.ofSeconds(20))
        .build();

    rt.setErrorHandler(new ResponseErrorHandler() {
      @Override public boolean hasError(ClientHttpResponse response) { return false; }
      @Override public void handleError(ClientHttpResponse response) { /* noop */ }
    });

    return rt;
  }
}
