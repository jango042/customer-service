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
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  private final RoleRepository roleRepository;

  private final AuthenticationManager authenticationManager;

  private final PasswordEncoder passwordEncoder;

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

    List<String> roles = customerInfo.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    return new BasicResponse(Status.SUCCESS, new JwtResponse(jwt, customerInfo.getUsername(), roles));

  }



  private boolean emailExists(String email) {
    return customerRepository.existsByEmail(email);
  }




}
