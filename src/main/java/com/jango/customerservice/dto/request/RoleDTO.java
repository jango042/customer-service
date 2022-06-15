package com.jango.customerservice.dto.request;

import com.jango.customerservice.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleDTO {

  private RoleType name;

}
