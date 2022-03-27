package com.antonr.webshop.exception;

public class WrongPasswordException extends RuntimeException {

  public WrongPasswordException(String str) {
    super(str);
  }
}
