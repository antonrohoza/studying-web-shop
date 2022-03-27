package com.antonr.webshop.web.servlet;

import com.antonr.webshop.entity.Session;
import com.antonr.webshop.service.CartService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CartAddServlet extends HttpServlet {

  private final CartService cartService;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    int id = Integer.parseInt(req.getParameter("id"));
    Session session = (Session) req.getAttribute("session");
    cartService.addToCart(session.getCart(), id);
    resp.setStatus(HttpServletResponse.SC_OK);
  }

}
