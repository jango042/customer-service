package com.jango.customerservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionStatus {
  PENDING("pending"),
  SUCCESS("success"),
  FAILED("failed");

  private final String alias;
}
