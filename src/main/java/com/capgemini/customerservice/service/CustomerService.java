package com.capgemini.customerservice.service;

import com.capgemini.customerservice.dto.request.LoginRequest;
import com.capgemini.customerservice.dto.response.BasicResponse;

public interface CustomerService {

  BasicResponse authenticateCustomer(LoginRequest customerDetails);

}
