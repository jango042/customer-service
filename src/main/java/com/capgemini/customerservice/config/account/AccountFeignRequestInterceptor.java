package com.capgemini.customerservice.config.account;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class AccountFeignRequestInterceptor implements RequestInterceptor {
  private final String SECRET_KEY;

  public AccountFeignRequestInterceptor(String apiKey) {
    this.SECRET_KEY = apiKey;
  }

  @Override
  public void apply(RequestTemplate requestTemplate) {
    final String secret = "secret_key";
    requestTemplate.header(secret, SECRET_KEY);
  }

}
