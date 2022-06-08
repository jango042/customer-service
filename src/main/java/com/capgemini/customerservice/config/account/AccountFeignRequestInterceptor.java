package com.capgemini.customerservice.config.account;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AccountFeignRequestInterceptor implements RequestInterceptor {

  private static final Pattern BEARER_TOKEN_HEADER_PATTERN = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
      Pattern.CASE_INSENSITIVE);

  @Override
  public void apply(RequestTemplate requestTemplate) {
    final String authorization = HttpHeaders.AUTHORIZATION;
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (Objects.nonNull(requestAttributes)) {
      String authorizationHeader = requestAttributes.getRequest().getHeader(authorization);
      Matcher matcher = BEARER_TOKEN_HEADER_PATTERN.matcher(authorizationHeader);
      if (matcher.matches()) {
        requestTemplate.header(authorization, authorizationHeader);
      }
    }
  }

}
