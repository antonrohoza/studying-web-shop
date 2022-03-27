package com.antonr.webshop.web.servlet;

import com.antonr.webshop.web.util.PageGenerator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class NoRightsServlet extends HttpServlet {

  private final PageGenerator pageGenerator;

  @Override
  @SneakyThrows
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    pageGenerator.generatePage(resp.getWriter(), "noRights.html");
    resp.setStatus(HttpServletResponse.SC_OK);
  }

}
