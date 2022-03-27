package com.antonr.webshop.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class PasswordEncoder {

  private final MessageDigest messageDigest;

  public PasswordEncoder() {
    try {
      messageDigest = MessageDigest.getInstance("MD5");
    } catch (
        NoSuchAlgorithmException e) {
      throw new RuntimeException("MD5 not supported", e);
    }
  }

  public String passwordHash(String saltedPassword) {
    messageDigest.update(saltedPassword.getBytes());
    return new String(messageDigest.digest(), StandardCharsets.UTF_8);
  }

  public String getSalt() {
    return UUID.randomUUID().toString();
  }

}
