package com.antonr.webshop.dao.jdbc.mapper;

import com.antonr.webshop.entity.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ProductRowMapper {

  public Product mapRow(ResultSet resultSet) throws SQLException {
    int id = resultSet.getInt("id");
    String name = resultSet.getString("name");
    double price = resultSet.getDouble("price");
    LocalDate creationDate = resultSet.getDate("creation_date").toLocalDate();

    return Product.builder()
        .id(id)
        .name(name)
        .price(price)
        .creationDate(creationDate)
        .build();
  }
}
