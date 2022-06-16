package com.jango.customerservice.service.implementation;

import com.jango.customerservice.config.client.AccountFeignClient;
import com.jango.customerservice.config.client.TransactionFeignClient;
import com.jango.customerservice.dto.request.LoginRequest;
import com.jango.customerservice.dto.response.AccountResponse;
import com.jango.customerservice.dto.response.BasicResponse;
import com.jango.customerservice.dto.response.CustomerOperationResponse;
import com.jango.customerservice.dto.response.JwtResponse;
import com.jango.customerservice.dto.response.TransactionResponse;
import com.jango.customerservice.enums.Status;
import com.jango.customerservice.exception.ResourceNotFoundException;
import com.jango.customerservice.model.Customer;
import com.jango.customerservice.repository.CustomerRepository;
import com.jango.customerservice.security.service.CustomerDetailsImpl;
import com.jango.customerservice.service.CustomerService;
import com.jango.customerservice.util.JwtUtils;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  private final AuthenticationManager authenticationManager;

  private final AccountFeignClient accountFeignClient;

  private final TransactionFeignClient transactionFeignClient;

  @Value("${jango.customer.secret.key}")
  private String secretKey;

  private final JwtUtils jwtUtils;

  @Override
  public BasicResponse authenticateCustomer(LoginRequest customerDetails) {

    if (!emailExists(customerDetails.getEmail())) {
      return new BasicResponse(Status.NOT_FOUND, "Error: Customer not found! Make sure email is correct " +
          "or signup if you don't have an account");
    }

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(customerDetails.getEmail(), customerDetails.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);


    CustomerDetailsImpl customerInfo = (CustomerDetailsImpl) authentication.getPrincipal();

    Customer customer = customerRepository.findByEmail(customerInfo.getUsername()).get();

        List<String> roles = customerInfo.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    return new BasicResponse(Status.SUCCESS, new JwtResponse(jwt, customerInfo.getUsername(), customer.getCustomerId(), roles));

  }

  @Override
  public BasicResponse validateTransactionKey(Map<String, String> requestHeaders) {
    try {

      String secretKeyFromHeader = requestHeaders.get("secret_key");
      if (Objects.isNull(secretKeyFromHeader) || !secretKey.equals(secretKeyFromHeader)) {
        throw new ValidationException("Secret key is not present or not correct!");
      }
      return new BasicResponse(Status.SUCCESS);
    } catch (Exception e) {
      log.error("There was an error while updating customer: {}", e.getMessage());
      return new BasicResponse(Status.FORBIDDEN);
    }
  }

  @Override
  public BasicResponse getDetails(String customerId) {
    return customerRepository.findByCustomerId(customerId).map(customer -> {
      BasicResponse accountList = accountFeignClient.getCustomerAccounts(customerId);
      BasicResponse transactionList = transactionFeignClient.getCustomerTransactions(customerId);
      Gson gson = new Gson();
      Type accountListType = new TypeToken<ArrayList<AccountResponse>>(){}.getType();
      Type transactionListType = new TypeToken<ArrayList<TransactionResponse>>(){}.getType();

      List<AccountResponse> accountResponses = gson.fromJson(accountList.getResponseData().toString(), accountListType);
      List<TransactionResponse> transactionResponses = gson.fromJson(transactionList.getResponseData().toString(), transactionListType);
      return getCustomerOperationResponse(customer, accountResponses, transactionResponses);
    }).orElseThrow(() -> new ResourceNotFoundException("CustomerId provided not found"));

  }

  private BasicResponse getCustomerOperationResponse(Customer customer,
      List<AccountResponse> accountResponses, List<TransactionResponse> transactionResponses) {
    CustomerOperationResponse customerOperationResponse = new CustomerOperationResponse();
    customerOperationResponse.setFirstName(customer.getFirstname());
    customerOperationResponse.setSurname(customer.getSurname());
    customerOperationResponse.setAccountList(accountResponses);
    customerOperationResponse.setTransactionList(transactionResponses);
    return new BasicResponse(Status.SUCCESS, customerOperationResponse);
  }

  private boolean emailExists(String email) {
    return customerRepository.existsByEmail(email);
  }

}
