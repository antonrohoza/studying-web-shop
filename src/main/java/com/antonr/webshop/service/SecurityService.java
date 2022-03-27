package com.antonr.webshop.service;

import com.antonr.webshop.entity.Role;
import com.antonr.webshop.entity.Session;
import com.antonr.webshop.entity.User;
import com.antonr.webshop.exception.UserNotFoundException;
import com.antonr.webshop.exception.WrongPasswordException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityService {

  private static final long EXPIRE_TIME = 20L;

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final Map<String, Session> tokenToSessionMap;

  public SecurityService(UserService userService) {
    this.userService = userService;
    this.passwordEncoder = new PasswordEncoder();
    this.tokenToSessionMap = new ConcurrentHashMap<>();
  }

  public String login(String login, String password) {
    User user = getUser(login);
    checkPasswordMatchesName(user, password);
    String token = generateToken();
    tokenToSessionMap
        .put(token, new Session(token, LocalDateTime.now().plusMinutes(EXPIRE_TIME), user));
    return token;
  }

  public String register(String login, String password) {
    saveUser(login, password, passwordEncoder.getSalt());
    User user = getUser(login);
    String token = generateToken();
    tokenToSessionMap
        .put(token, new Session(token, LocalDateTime.now().plusMinutes(EXPIRE_TIME), user));
    return token;
  }

  public Session getSessionByToken(String token) {
    return tokenToSessionMap.entrySet().stream()
                            .filter(entry -> token.equals(entry.getKey()))
                            .map(Entry::getValue)
                            .findFirst()
                            .orElseThrow(() -> new UserNotFoundException(
                                "There is no users with such token: " + token + "!"));
  }

  private void saveUser(String login, String password, String salt) {
    String saltedPasswordHash = passwordEncoder.passwordHash(password + salt);
    User user = User.builder()
                    .login(login)
                    .password(saltedPasswordHash)
                    .salt(salt)
                    .role(Role.USER)
                    .build();
    userService.saveUser(user);
  }

  private User getUser(String login) {
    return userService.getUserByLogin(login)
                      .orElseThrow(
                          () -> new UserNotFoundException("There is no user with login: " + login));
  }

  private String generateToken() {
    return String.valueOf(UUID.randomUUID());
  }

  private void checkPasswordMatchesName(User user, String password) {
    String saltedHashedPassword = passwordEncoder.passwordHash(password + user.getSalt());
    if (!saltedHashedPassword.equals(user.getPassword())) {
      throw new WrongPasswordException(
          "Provided password doesn't match to user login:" + user.getLogin());
    }
  }
}
