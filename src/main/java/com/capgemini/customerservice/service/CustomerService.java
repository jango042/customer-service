package com.capgemini.customerservice.service;

import com.capgemini.customerservice.dto.request.LoginRequest;
import com.capgemini.customerservice.dto.response.BasicResponse;
import java.util.Map;

public interface CustomerService {

  BasicResponse authenticateCustomer(LoginRequest customerDetails);
  BasicResponse validateTransactionKey(Map<String, String> requestHeaders);
  BasicResponse getDetails(String customerId);

}
