package com.antonr.webshop.web.servlet;

import com.antonr.webshop.ServiceLocator;
import com.antonr.webshop.service.ProductService;
import com.antonr.webshop.web.util.PageGenerator;
import io.vavr.collection.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class AllProductsServlet extends HttpServlet {

  private final ProductService productService = ServiceLocator.get(ProductService.class);
  private final PageGenerator pageGenerator = ServiceLocator.get(PageGenerator.class);

  @Override
  @SneakyThrows
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    pageGenerator.generatePage(resp.getWriter(), "products.html",
                               HashMap.of("products", productService.getAll().asJava())
                                      .toJavaMap());
    resp.setStatus(HttpServletResponse.SC_OK);
  }

}
