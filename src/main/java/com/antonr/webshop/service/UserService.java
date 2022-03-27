package com.antonr.webshop.service;

import com.antonr.webshop.dao.UserDao;
import com.antonr.webshop.entity.User;
import java.util.Optional;

public class UserService {

  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public Optional<User> getUserByLogin(String login) {
    return userDao.getUserByLogin(login);
  }

  public void saveUser(User user) {
    userDao.saveUser(user);
  }

}
