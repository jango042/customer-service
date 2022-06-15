package com.jango.customerservice.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

  private String token;
  private String type = "Bearer";
  private String email;
  private String customerId;
  private List<String> roles;

  public JwtResponse(String accessToken, String email, String customerId, List<String> roles) {
    this.token = accessToken;
    this.email = email;
    this.customerId = customerId;
    this.roles = roles;
  }

}
