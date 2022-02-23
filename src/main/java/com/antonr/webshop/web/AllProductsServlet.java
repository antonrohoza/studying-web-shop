package com.antonr.webshop.web;

import com.antonr.webshop.entity.Product;
import com.antonr.webshop.service.ProductService;
import com.antonr.webshop.web.util.PageGenerator;
import io.vavr.collection.Array;
import io.vavr.collection.HashMap;
import io.vavr.collection.Seq;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AllProductsServlet extends HttpServlet {
  private final ProductService productService;
  private final List<String> tokens;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Cookie[] cookies = request.getCookies();
    boolean isValid = cookies != null
        && Array.of(cookies)
        .filter(cookie -> "user-token".equals(cookie.getName()))
        .map(Cookie::getValue)
        .exists(tokens::contains);
    if (!isValid) {
      response.sendRedirect("/login");
    }
    Seq<Product> products = productService.getAll();
    PageGenerator.generatePage(response.getWriter(), "template/products.html", HashMap.of("products", products.asJava()).toJavaMap());
    response.setStatus(HttpServletResponse.SC_OK);
  }
}
