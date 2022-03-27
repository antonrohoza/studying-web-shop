package com.antonr.webshop.dao.jdbc.mapper;

import com.antonr.webshop.entity.Role;
import com.antonr.webshop.entity.User;
import java.sql.ResultSet;
import lombok.SneakyThrows;

public class UserRowMapper extends RowMapper<User> {

  @Override
  @SneakyThrows
  public User mapRow(ResultSet resultSet) {
    int id = resultSet.getInt("id");
    String login = resultSet.getString("login");
    String password = resultSet.getString("password");
    String salt = resultSet.getString("salt");
    Role role = Role.valueOf(resultSet.getString("Role"));

    return User.builder()
               .id(id)
               .login(login)
               .password(password)
               .salt(salt)
               .role(role)
               .build();
  }

}
