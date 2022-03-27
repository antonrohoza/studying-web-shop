package com.antonr.webshop.exception;

public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(String str) {
    super(str);
  }

}
