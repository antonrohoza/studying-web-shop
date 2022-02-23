package com.antonr.webshop.entity;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDate creationDate;
}
