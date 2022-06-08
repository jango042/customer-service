package com.capgemini.customerservice.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {

  @NotBlank(message = "Customer Id cannot be blank")
  private String customerId;
  @NotBlank(message = "Initial Credit cannot be blank")
  private Double initialCredit;

}
