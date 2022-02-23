package com.antonr.webshop.web;

import com.antonr.webshop.web.util.PageGenerator;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginServlet extends HttpServlet {
  private final List<String> tokens;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    PageGenerator.generatePage(resp.getWriter(), "template/login.html", new HashMap<>());
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String login = req.getParameter("login");
    String password = req.getParameter("password");
    if(login != null && password != null){
      String token = UUID.randomUUID().toString();
      tokens.add(token);
      Cookie cookie = new Cookie("user-token", token);
      resp.addCookie(cookie);
    }
    resp.sendRedirect("/");
  }
}
