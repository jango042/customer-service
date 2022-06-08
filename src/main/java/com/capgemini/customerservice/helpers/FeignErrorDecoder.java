package com.capgemini.customerservice.helpers;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    HttpStatus httpStatus = HttpStatus.valueOf(response.status());
    return new ResponseStatusException(httpStatus, httpStatus.getReasonPhrase());
  }

}
