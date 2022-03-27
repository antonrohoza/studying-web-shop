package com.antonr.webshop.web.servlet;

import com.antonr.webshop.exception.UserNotFoundException;
import com.antonr.webshop.service.SecurityService;
import com.antonr.webshop.web.util.PageGenerator;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class LoginServlet extends HttpServlet {

  private final PageGenerator pageGenerator;
  private final SecurityService securityService;

  @Override
  @SneakyThrows
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    pageGenerator.generatePage(resp.getWriter(), "login.html");
    resp.setStatus(HttpServletResponse.SC_OK);
  }

  @Override
  @SneakyThrows
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    try {
      String login = req.getParameter("login");
      String password = req.getParameter("password");
      String token = securityService.login(login, password);
      Cookie cookie = new Cookie("user-token", token);
      resp.addCookie(cookie);
      resp.setStatus(HttpServletResponse.SC_OK);
      resp.sendRedirect("/products");
    } catch (UserNotFoundException e) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      pageGenerator.generatePage(resp.getWriter(), "loginFailed.html");
    }
  }
}
