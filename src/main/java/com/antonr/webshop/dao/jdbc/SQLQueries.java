package com.antonr.webshop.dao.jdbc;

public enum SQLQueries {
  ;
  static final String SELECT_ALL = "SELECT id, name, price, creation_date FROM products;";
  static final String SELECT_BY_ID = "SELECT id, name, price, creation_date FROM products WHERE id=?;";
  static final String INSERT_QUERY = "INSERT INTO products (id, name, price, creation_date) VALUES (?, ?, ?, ?);";
  static final String DELETE_BY_ID = "DELETE FROM products WHERE id=?;";
  static final String UPDATE_QUERY = "UPDATE products SET name=?, price=?, creation_date=? WHERE id=?;";
}
