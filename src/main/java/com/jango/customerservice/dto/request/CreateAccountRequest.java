package com.jango.customerservice.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {
  @NotBlank(message = "Customer Id cannot be blank")
  private String customerId;
  @NotBlank(message = "Initial Credit cannot be blank")
  private Double initialCredit;

}
