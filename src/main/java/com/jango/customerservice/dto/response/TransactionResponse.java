package com.jango.customerservice.dto.response;

import com.jango.customerservice.enums.TransactionStatus;
import com.jango.customerservice.enums.TransactionType;
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
