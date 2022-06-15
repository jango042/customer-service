package com.jango.customerservice.dto.response;

import com.jango.customerservice.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StandardResponse implements Serializable {

  @JsonProperty
  protected Status status;

}
