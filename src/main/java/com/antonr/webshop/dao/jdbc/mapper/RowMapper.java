package com.antonr.webshop.dao.jdbc.mapper;

import io.vavr.collection.Seq;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class RowMapper<T> {

  abstract T mapRow(ResultSet resultSet);

  public Seq<T> getAllElements(ResultSet resultSet, Seq<T> elementsList)
      throws SQLException {
    if (!resultSet.next()) {
      return elementsList;
    }
    return getAllElements(resultSet, elementsList.append(mapRow(resultSet)));
  }
}
