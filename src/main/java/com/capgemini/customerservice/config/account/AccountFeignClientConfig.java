package com.capgemini.customerservice.config.account;

import com.capgemini.customerservice.helpers.FeignErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class AccountFeignClientConfig {

  @Bean
  public RequestInterceptor requestInterceptor() {
    return new AccountFeignRequestInterceptor();
  }

  @Bean
  public ErrorDecoder errorDecoder() {
    return new FeignErrorDecoder();
  }

}
