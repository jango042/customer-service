package com.capgemini.customerservice.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

  @NotBlank(message = "email cannot be empty")
  private String email;

  @NotBlank(message = "password cannot be empty")
  private String password;

}
