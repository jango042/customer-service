package com.jango.customerservice.config.client;


import com.jango.customerservice.config.account.AccountFeignClientConfig;
import com.jango.customerservice.dto.request.AccountRequest;
import com.jango.customerservice.dto.response.BasicResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "accounts", url = "${capgemini.account.base.url}", configuration = AccountFeignClientConfig.class)
public interface AccountFeignClient {

  @PostMapping("/api/v1/account/")
  BasicResponse createAccount(AccountRequest request);

  @GetMapping("/api/v1/account/{customerId}")
  BasicResponse getCustomerAccounts(@PathVariable("customerId") String customerId);

}
