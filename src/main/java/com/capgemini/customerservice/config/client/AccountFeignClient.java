package com.capgemini.customerservice.config.client;


import com.capgemini.customerservice.config.account.AccountFeignClientConfig;
import com.capgemini.customerservice.dto.request.AccountRequest;
import com.capgemini.customerservice.dto.response.BasicResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "accounts", url = "${capgemini.account.base.url}", configuration = AccountFeignClientConfig.class)
public interface AccountFeignClient {

  @PostMapping("/api/v1/account/")
  BasicResponse createAccount(AccountRequest request);

}