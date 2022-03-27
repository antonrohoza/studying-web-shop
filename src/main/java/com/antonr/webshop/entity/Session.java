package com.antonr.webshop.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class Session {

  private String token;
  private LocalDateTime expireDateTime;
  private Map<Product, Integer> cart;
  private User user;

  public Session(String token, LocalDateTime localDateTime, User user) {
    this.token = token;
    this.expireDateTime = localDateTime;
    this.user = user;
    this.cart = new HashMap<>();
  }
}
