package com.antonr.webshop.service;

import com.antonr.webshop.entity.Product;
import java.util.Map;

public class CartService {

  private final ProductService productService;

  public CartService(ProductService productService) {
    this.productService = productService;
  }

  public void addToCart(Map<Product, Integer> cart, int id) {
    Product product = productService.getById(id);
    cart.put(product, cart.getOrDefault(product, 0) + 1);
  }

  public void removeFromCart(Map<Product, Integer> cart, int id) {
    Product product = productService.getById(id);
    Integer productCount = cart.get(product);
    if (productCount == 1) {
      cart.remove(product);
    } else {
      cart.put(product, productCount - 1);
    }
  }
}
