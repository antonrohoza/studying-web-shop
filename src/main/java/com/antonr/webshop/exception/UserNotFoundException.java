package com.antonr.webshop.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String str) {
    super(str);
  }

}
