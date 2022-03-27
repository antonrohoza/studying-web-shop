package com.antonr.webshop.service;

import com.antonr.webshop.dao.ProductDao;
import com.antonr.webshop.entity.Product;
import com.antonr.webshop.exception.ProductNotFoundException;
import io.vavr.collection.Seq;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductService {

  private final ProductDao productDao;

  public Seq<Product> getAll() {
    return productDao.findAll();
  }

  public Product getById(int id) {
    Optional<Product> maybeId = productDao.findById(id);
    return maybeId.orElseThrow(() -> new ProductNotFoundException("There is no such product in DB!"));
  }

  public boolean save(Product product) {
    return productDao.save(product);
  }

  public boolean delete(int id) {
    return productDao.delete(id);
  }

  public boolean update(Product product) {
    return productDao.update(product);
  }
}
