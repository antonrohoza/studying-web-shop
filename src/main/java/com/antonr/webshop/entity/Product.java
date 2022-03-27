package com.antonr.webshop.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Product {

  int id;
  String name;
  double price;
  LocalDateTime creationDate;
}
