package com.capgemini.customerservice.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOperationResponse {

  private String firstName;
  private String surname;
  private List<AccountResponse> accountList;
  private List<TransactionResponse> transactionList;

}
