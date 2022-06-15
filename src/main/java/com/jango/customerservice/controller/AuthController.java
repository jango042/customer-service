package com.jango.customerservice.controller;

import com.jango.customerservice.dto.request.LoginRequest;
import com.jango.customerservice.dto.response.BasicResponse;
import com.jango.customerservice.service.CustomerService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController extends Controller {

  private final CustomerService customerService;

  @PostMapping("/login")
  public BasicResponse login(@Valid @RequestBody LoginRequest userDetails) {
    return responseWithUpdatedHttpStatus(customerService.authenticateCustomer(userDetails));
  }

}
