package com.jango.customerservice.config.account;

import com.jango.customerservice.helpers.FeignErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class AccountFeignClientConfig {

  @Value("${jango.customer.secret.key}")
  private String secretKey;

  @Bean
  public RequestInterceptor requestInterceptor() {
    return new AccountFeignRequestInterceptor(secretKey);
  }

  @Bean
  public ErrorDecoder errorDecoder() {
    return new FeignErrorDecoder();
  }


}
