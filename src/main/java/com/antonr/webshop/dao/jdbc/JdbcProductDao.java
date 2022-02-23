package com.antonr.webshop.dao.jdbc;

import static com.antonr.webshop.dao.jdbc.SQLQueries.DELETE_BY_ID;
import static com.antonr.webshop.dao.jdbc.SQLQueries.INSERT_QUERY;
import static com.antonr.webshop.dao.jdbc.SQLQueries.SELECT_ALL;
import static com.antonr.webshop.dao.jdbc.SQLQueries.SELECT_BY_ID;
import static com.antonr.webshop.dao.jdbc.SQLQueries.UPDATE_QUERY;

import com.antonr.webshop.config.ConnectionFactory;
import com.antonr.webshop.dao.ProductDao;
import com.antonr.webshop.dao.jdbc.mapper.ProductRowMapper;
import com.antonr.webshop.entity.Product;
import com.antonr.webshop.exception.InconsistentDataException;
import com.antonr.webshop.web.UpdateProductServlet;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

@AllArgsConstructor
public class JdbcProductDao implements ProductDao {

  private static final Logger LOG = Logger.getLogger(UpdateProductServlet.class);
  private static final ProductRowMapper PRODUCT_ROW_MAPPER;

  private final ConnectionFactory connectionFactory;

  static {
    PRODUCT_ROW_MAPPER = new ProductRowMapper();
  }

  @Override
  public Seq<Product> findAll() {
    try (Connection connection = connectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
      try (ResultSet resultSet = statement.executeQuery()) {
        return getAllProducts(resultSet, List.empty());
      }
    } catch (SQLException e) {
      LOG.error("Can't get all products!", e);
      throw new InconsistentDataException(e);
    }
  }

  @Override
  public Optional<Product> findById(int id) {
    try (Connection connection = connectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
      statement.setInt(1, id);
      try (ResultSet resultSet = statement.executeQuery()) {
        return resultSet.next() ? Optional.of(PRODUCT_ROW_MAPPER.mapRow(resultSet))
            : Optional.empty();
      }
    } catch (SQLException e) {
      LOG.error("Can't id: " + id + " of product!", e);
      throw new InconsistentDataException(e);
    }
  }

  @Override
  public boolean save(Product product) {
    try (Connection connection = connectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
      statement.setInt(1, product.getId());
      statement.setString(2, product.getName());
      statement.setDouble(3, product.getPrice());
      statement.setDate(4, Date.valueOf(product.getCreationDate()));
      return statement.execute();
    } catch (SQLException e) {
      LOG.error("Error during INSERT!", e);
      throw new InconsistentDataException(e);
    }
  }

  @Override
  public int update(Product product) {
    try (Connection connection = connectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
      statement.setString(1, product.getName());
      statement.setDouble(2, product.getPrice());
      statement.setDate(3, Date.valueOf(product.getCreationDate()));
      statement.setInt(4, product.getId());
      return statement.executeUpdate();
    } catch (
        SQLException e) {
      LOG.error("Error during UPDATE!", e);
      throw new InconsistentDataException(e);
    }
  }

  @Override
  public boolean delete(int id) {
    try (Connection connection = connectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
      statement.setInt(1, id);
      return statement.execute();
    } catch (SQLException e) {
      LOG.error("ERROR during DELETE by id: " + id + "!", e);
      throw new InconsistentDataException(e);
    }
  }

  private static Seq<Product> getAllProducts(ResultSet resultSet, Seq<Product> productsList)
      throws SQLException {
    if (!resultSet.next()) {
      return productsList;
    }
    return getAllProducts(resultSet, productsList.append(PRODUCT_ROW_MAPPER.mapRow(resultSet)));
  }
}
