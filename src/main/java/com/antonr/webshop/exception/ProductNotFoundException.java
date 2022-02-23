package com.antonr.webshop.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String str) {
        super(str);
    }
}
