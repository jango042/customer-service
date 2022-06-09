package com.capgemini.customerservice.repository;

import com.capgemini.customerservice.model.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Optional<Customer> findByEmail(String email);
  Optional<Customer> findByCustomerId(String customerId);

  Boolean existsByEmail(String email);


}
