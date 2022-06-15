package com.jango.customerservice.service;

import com.jango.customerservice.config.client.AccountFeignClient;
import com.jango.customerservice.dto.request.AccountRequest;
import com.jango.customerservice.dto.request.CreateAccountRequest;
import com.jango.customerservice.dto.response.BasicResponse;
import com.jango.customerservice.enums.Status;
import com.jango.customerservice.model.Customer;
import com.jango.customerservice.repository.CustomerRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class AccountService {

  private final CustomerRepository customerRepository;

  private final AccountFeignClient accountFeignClient;



  public BasicResponse createCurrentAccount(CreateAccountRequest request) {
    Optional<Customer> customer = customerRepository.findByCustomerId(request.getCustomerId());
    if (customer.isPresent()) {
      return createAccount(request, customer.get());
    } else {
      log.info("Error{}",request.getCustomerId()+" not found");
      return new BasicResponse(Status.FORBIDDEN);
    }

  }

  private BasicResponse createAccount(CreateAccountRequest request, Customer customer) {
    AccountRequest accountRequest = new AccountRequest();
    accountRequest.setCustomerId(request.getCustomerId());
    accountRequest.setFirstName(customer.getFirstname());
    accountRequest.setInitialCredit(request.getInitialCredit());
    accountRequest.setSurname(customer.getSurname());
    return accountFeignClient.createAccount(accountRequest);
  }

}
