package com.antonr.webshop.dao;

import com.antonr.webshop.entity.User;
import java.util.Optional;

public interface UserDao {

  Optional<User> getUserByLogin(String login);

  void saveUser(User user);

}
