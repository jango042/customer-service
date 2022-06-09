package com.capgemini.customerservice.config.client;

import com.capgemini.customerservice.config.account.AccountFeignClientConfig;
import com.capgemini.customerservice.dto.response.BasicResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "transactions", url = "${capgemini.transaction.base.url}", configuration = AccountFeignClientConfig.class)
public interface TransactionFeignClient {

  @GetMapping("/api/v1/transaction/{customerId}")
  BasicResponse getCustomerTransactions(@PathVariable("customerId") String customerId);

}
