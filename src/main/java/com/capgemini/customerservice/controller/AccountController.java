package com.capgemini.customerservice.controller;


import com.capgemini.customerservice.dto.request.CreateAccountRequest;
import com.capgemini.customerservice.dto.response.BasicResponse;
import com.capgemini.customerservice.service.AccountService;
import com.capgemini.customerservice.service.CustomerService;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {

  private final CustomerService customerService;
  private final AccountService accountService;


  @PostMapping("/account")
  public BasicResponse createAccount(@RequestBody CreateAccountRequest request) {
    return accountService.createCurrentAccount(request);
  }

  @GetMapping("/operation/{customerId}")
  public BasicResponse getCustomerOperations(@PathVariable("customerId") String customerId) {
    return customerService.getDetails(customerId);
  }

  @PostMapping("/webhook")
  public BasicResponse validateKey(@RequestHeader Map<String, String> requestHeaders) {
    return customerService.validateTransactionKey(requestHeaders);
  }

}
