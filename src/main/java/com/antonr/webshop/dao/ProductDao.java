package com.antonr.webshop.dao;

import com.antonr.webshop.entity.Product;
import io.vavr.collection.Seq;
import java.util.Optional;

public interface ProductDao {

  Seq<Product> findAll();

  Optional<Product> findById(int id);

  boolean save(Product product);

  boolean delete(int id);

  boolean update(Product product);
}
