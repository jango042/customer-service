package com.jango.customerservice.service;

import com.jango.customerservice.dto.request.LoginRequest;
import com.jango.customerservice.dto.response.BasicResponse;
import java.util.Map;

public interface CustomerService {

  BasicResponse authenticateCustomer(LoginRequest customerDetails);
  BasicResponse validateTransactionKey(Map<String, String> requestHeaders);
  BasicResponse getDetails(String customerId);

}
