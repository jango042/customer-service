package com.capgemini.customerservice.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

  @NotBlank(message = "Customer Id cannot be blank")
  private String customerId;
  @NotBlank(message = "Initial Credit cannot be blank")
  private Double initialCredit;
  private String firstName;
  private String surname;

}
