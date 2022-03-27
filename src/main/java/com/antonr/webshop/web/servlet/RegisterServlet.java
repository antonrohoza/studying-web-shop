package com.antonr.webshop.web.servlet;

import com.antonr.webshop.exception.UserAlreadyExistsException;
import com.antonr.webshop.service.SecurityService;
import com.antonr.webshop.service.UserService;
import com.antonr.webshop.web.util.PageGenerator;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterServlet extends HttpServlet {

  private static final Logger LOG = LoggerFactory.getLogger(UpdateProductServlet.class);
  private final SecurityService securityService;
  private final UserService userService;
  private final PageGenerator pageGenerator;

  public RegisterServlet(SecurityService securityService, UserService userService) {
    this.securityService = securityService;
    this.userService = userService;
    this.pageGenerator = new PageGenerator();
  }

  @Override
  @SneakyThrows
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    pageGenerator.generatePage(resp.getWriter(), "register.html");
    resp.setStatus(HttpServletResponse.SC_OK);
  }

  @Override
  @SneakyThrows
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    String login = req.getParameter("login");
    String password = req.getParameter("password");
    if (userService.getUserByLogin(login).isPresent()) {
      LOG.error("Such login:" + login + "already exist!", UserAlreadyExistsException.class);
      resp.sendRedirect("/register");
      return;
    }
    String token = securityService.register(login, password);
    Cookie cookie = new Cookie("user-token", token);
    resp.addCookie(cookie);
    resp.sendRedirect("/products");
  }
}
