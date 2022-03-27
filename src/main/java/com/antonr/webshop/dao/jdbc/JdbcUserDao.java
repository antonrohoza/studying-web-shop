package com.antonr.webshop.dao.jdbc;

import com.antonr.webshop.config.ConnectionFactory;
import com.antonr.webshop.dao.UserDao;
import com.antonr.webshop.dao.jdbc.mapper.UserRowMapper;
import com.antonr.webshop.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class JdbcUserDao implements UserDao {

  private static final String SELECT_USER_BY_LOGIN = "SELECT id, login, password, salt, Role FROM users WHERE login=?;";
  private static final String INSERT_USER = "INSERT INTO users (login, password, salt, Role) VALUES(?, ?, ?, ?);";

  private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
  private final ConnectionFactory connectionFactory;

  @Override
  @SneakyThrows
  public Optional<User> getUserByLogin(String login) {
    try (Connection connection = connectionFactory.getConnection();
         PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
      statement.setString(1, login);
      try (ResultSet resultSet = statement.executeQuery()) {
        return resultSet.next() ? Optional.of(USER_ROW_MAPPER.mapRow(resultSet))
                                : Optional.empty();
      }
    }
  }

  @Override
  @SneakyThrows
  public void saveUser(User user) {
    try (Connection connection = connectionFactory.getConnection();
         PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
      statement.setString(1, user.getLogin());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getSalt());
      statement.setString(4, user.getRole().toString());
      statement.execute();
    }
  }
}
