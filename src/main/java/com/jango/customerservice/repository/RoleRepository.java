package com.jango.customerservice.repository;

import com.jango.customerservice.enums.RoleType;
import com.jango.customerservice.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

  Optional<Role> findByName(RoleType name);

}
