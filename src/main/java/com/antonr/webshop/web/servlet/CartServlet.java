package com.antonr.webshop.web.servlet;

import com.antonr.webshop.entity.Session;
import com.antonr.webshop.web.util.PageGenerator;
import io.vavr.collection.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

public class CartServlet extends HttpServlet {

  private final PageGenerator PAGE_GENERATOR;

  public CartServlet() {
    PAGE_GENERATOR = new PageGenerator();
  }

  @Override
  @SneakyThrows
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    Session session = (Session) req.getAttribute("session");
    PAGE_GENERATOR.generatePage(resp.getWriter(), "cart.html",
                                HashMap.of("cart", session.getCart()).toJavaMap());
    resp.setStatus(HttpServletResponse.SC_OK);
  }
}
