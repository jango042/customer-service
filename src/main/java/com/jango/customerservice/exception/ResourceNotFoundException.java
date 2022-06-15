package com.jango.customerservice.exception;

public class ResourceNotFoundException extends RuntimeException{

  public ResourceNotFoundException(String message) {
    super(message);
  }

}
