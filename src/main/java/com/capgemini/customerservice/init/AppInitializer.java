package com.capgemini.customerservice.init;

import com.capgemini.customerservice.dto.request.RoleDTO;
import com.capgemini.customerservice.enums.RoleType;
import com.capgemini.customerservice.exception.ResourceNotFoundException;
import com.capgemini.customerservice.model.Customer;
import com.capgemini.customerservice.model.Role;
import com.capgemini.customerservice.repository.CustomerRepository;
import com.capgemini.customerservice.repository.RoleRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
public class AppInitializer implements ApplicationRunner {

  private final RoleRepository roleRepository;
  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;

  @Override
  public void run(ApplicationArguments args) {
    seedRoles();
    seedCustomer();
  }

  private void seedRoles() {
    Role role;

    if (roleRepository.findByName(RoleType.ADMIN).isEmpty()) {
      role = modelMapper.map(new RoleDTO(RoleType.ADMIN), Role.class);
      roleRepository.save(role);
    }

    if (roleRepository.findByName(RoleType.USER).isEmpty()) {
      role = modelMapper.map(new RoleDTO(RoleType.USER), Role.class);
      roleRepository.save(role);
    }
  }

  private void seedCustomer() {
    String email = "jangoo042@gmail.com";
    String customerId = "35647-898475-09405";
    if (Boolean.FALSE.equals(customerRepository.existsByEmail(email))) {
      Set<Role> roles = new HashSet<>();
      Role customerRole = roleRepository.findByName(RoleType.USER)
          .orElseThrow(() -> new ResourceNotFoundException("Error: Role not found."));
      roles.add(customerRole);



      Customer customer = new Customer();
      customer.setCustomerId(customerId);
      customer.setFirstname("Jango");
      customer.setSurname("Amalu");
      customer.setEmail("jangoo042@gmail.com");
      customer.setPassword(passwordEncoder.encode("password"));
      customer.setRoles(roles);

      customerRepository.save(customer);
    }
  }


}
