package com.jango.customerservice.dto.response;

import com.jango.customerservice.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicResponse extends StandardResponse{

  @JsonProperty
  private Object responseData;

  public BasicResponse(Status status) {
    super(status);
  }

  public BasicResponse(Status status, Object responseData) {
    super(status);
    this.responseData = responseData;
  }

}
