package com.capgemini.customerservice.service.implementation;

import com.capgemini.customerservice.dto.request.LoginRequest;
import com.capgemini.customerservice.dto.response.BasicResponse;
import com.capgemini.customerservice.dto.response.JwtResponse;
import com.capgemini.customerservice.enums.Status;
import com.capgemini.customerservice.model.Customer;
import com.capgemini.customerservice.repository.CustomerRepository;
import com.capgemini.customerservice.repository.RoleRepository;
import com.capgemini.customerservice.security.service.CustomerDetailsImpl;
import com.capgemini.customerservice.service.CustomerService;
import com.capgemini.customerservice.util.JwtUtils;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  private final RoleRepository roleRepository;

  private final AuthenticationManager authenticationManager;

  private final PasswordEncoder passwordEncoder;

  @Value("${capgemini.customer.secret.key}")
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


  private boolean emailExists(String email) {
    return customerRepository.existsByEmail(email);
  }




}
