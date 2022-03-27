package com.antonr.webshop.dao.jdbc.mapper;

import com.antonr.webshop.entity.Product;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import lombok.SneakyThrows;

public class ProductRowMapper extends RowMapper<Product> {

  @Override
  @SneakyThrows
  public Product mapRow(ResultSet resultSet) {
    int id = resultSet.getInt("id");
    String name = resultSet.getString("name");
    double price = resultSet.getDouble("price");
    LocalDateTime creationDate = resultSet.getTimestamp("creation_date").toLocalDateTime();

    return Product.builder()
                  .id(id)
                  .name(name)
                  .price(price)
                  .creationDate(creationDate)
                  .build();
  }
}
