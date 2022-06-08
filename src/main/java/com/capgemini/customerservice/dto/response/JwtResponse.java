package com.capgemini.customerservice.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

  private String token;
  private String type = "Bearer";
  private String email;
  private List<String> roles;

  public JwtResponse(String accessToken, String email, List<String> roles) {
    this.token = accessToken;
    this.email = email;
    this.roles = roles;
  }

}
