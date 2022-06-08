package com.capgemini.customerservice.controller;

import com.capgemini.customerservice.dto.response.StandardResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Slf4j
public class Controller {

  @Autowired
  private HttpServletResponse response;

  <T extends StandardResponse> T responseWithUpdatedHttpStatus(T responseDTO) {
    switch (responseDTO.getStatus()) {
      case CREATED:
        response.setStatus(HttpStatus.CREATED.value());
        break;
      case SUCCESS:
        response.setStatus(HttpStatus.OK.value());
        break;
      case NOT_FOUND:
        response.setStatus(HttpStatus.NOT_FOUND.value());
        break;
      case FORBIDDEN:
        response.setStatus(HttpStatus.FORBIDDEN.value());
      case BAD_REQUEST:
        response.setStatus(HttpStatus.BAD_REQUEST.value());
      default:
        response.setStatus(HttpStatus.BAD_REQUEST.value());
    }

    return responseDTO;
  }

}
