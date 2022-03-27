package com.antonr.webshop.web.servlet;

import com.antonr.webshop.entity.Session;
import com.antonr.webshop.service.CartService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class CartDeleteServlet extends HttpServlet {

  private final CartService cartService;

  @Override
  @SneakyThrows
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    int id = Integer.parseInt(req.getParameter("id"));
    Session session = (Session) req.getAttribute("session");
    cartService.removeFromCart(session.getCart(), id);
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.sendRedirect("/cart");
  }
}
