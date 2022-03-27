package com.antonr.webshop.web.servlet;

import com.antonr.webshop.entity.Product;
import com.antonr.webshop.service.ProductService;
import com.antonr.webshop.web.util.PageGenerator;
import com.antonr.webshop.web.util.WebUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class AddProductServlet extends HttpServlet {

  private final ProductService productService;
  private final PageGenerator pageGenerator;

  @Override
  @SneakyThrows
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    pageGenerator.generatePage(resp.getWriter(), "add.html");
    resp.setStatus(HttpServletResponse.SC_OK);
  }

  @Override
  @SneakyThrows
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    Product product = WebUtils.getProductByParameters(req);
    boolean success = product != null && productService.save(product);
    resp.setStatus(success ? HttpServletResponse.SC_OK
                           : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    resp.sendRedirect("/products");
  }
}
