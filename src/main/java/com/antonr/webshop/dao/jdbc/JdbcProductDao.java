package com.antonr.webshop.dao.jdbc;

import com.antonr.webshop.config.ConnectionFactory;
import com.antonr.webshop.dao.ProductDao;
import com.antonr.webshop.dao.jdbc.mapper.ProductRowMapper;
import com.antonr.webshop.entity.Product;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class JdbcProductDao implements ProductDao {

  private static final String SELECT_ALL = "SELECT id, name, price, creation_date FROM products;";
  private static final String SELECT_BY_ID = "SELECT id, name, price, creation_date FROM products WHERE id=?;";
  private static final String INSERT_QUERY = "INSERT INTO products (id, name, price, creation_date) VALUES (?, ?, ?, ?);";
  private static final String DELETE_BY_ID = "DELETE FROM products WHERE id=?;";
  private static final String UPDATE_QUERY = "UPDATE products SET name=?, price=?, creation_date=? WHERE id=?;";

  private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
  private final ConnectionFactory connectionFactory;

  @Override
  @SneakyThrows
  public Seq<Product> findAll() {
    try (Connection connection = connectionFactory.getConnection();
         PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
      try (ResultSet resultSet = statement.executeQuery()) {
        return PRODUCT_ROW_MAPPER.getAllElements(resultSet, List.empty());
      }
    }
  }

  @Override
  @SneakyThrows
  public Optional<Product> findById(int id) {
    try (Connection connection = connectionFactory.getConnection();
         PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
      statement.setInt(1, id);
      try (ResultSet resultSet = statement.executeQuery()) {
        return resultSet.next() ? Optional.of(PRODUCT_ROW_MAPPER.mapRow(resultSet))
                                : Optional.empty();
      }
    }
  }

  @Override
  @SneakyThrows
  public boolean save(Product product) {
    try (Connection connection = connectionFactory.getConnection();
         PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
      statement.setInt(1, product.getId());
      statement.setString(2, product.getName());
      statement.setDouble(3, product.getPrice());
      statement.setDate(4, Date.valueOf(product.getCreationDate().toLocalDate()));
      return statement.execute();
    }
  }

  @Override
  @SneakyThrows
  public boolean update(Product product) {
    try (Connection connection = connectionFactory.getConnection();
         PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
      statement.setString(1, product.getName());
      statement.setDouble(2, product.getPrice());
      statement.setDate(3, Date.valueOf(product.getCreationDate().toLocalDate()));
      statement.setInt(4, product.getId());
      statement.execute();
      return statement.getUpdateCount() == 1;
    }
  }

  @Override
  @SneakyThrows
  public boolean delete(int id) {
    try (Connection connection = connectionFactory.getConnection();
         PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
      statement.setInt(1, id);
      statement.execute();
      return statement.getUpdateCount() == 1;
    }
  }
}
