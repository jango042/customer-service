package com.capgemini.customerservice.dto.response;

import com.capgemini.customerservice.enums.TransactionStatus;
import com.capgemini.customerservice.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
  private String customerId;
  private String accountId;
  private Double amount;
  private TransactionStatus status;
  private TransactionType type;
}
