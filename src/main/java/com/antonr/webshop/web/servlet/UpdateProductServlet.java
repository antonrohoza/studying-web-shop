package com.antonr.webshop.web.servlet;

import com.antonr.webshop.entity.Product;
import com.antonr.webshop.service.ProductService;
import com.antonr.webshop.web.util.PageGenerator;
import com.antonr.webshop.web.util.WebUtils;
import io.vavr.collection.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class UpdateProductServlet extends HttpServlet {

  private static final Logger LOG = LoggerFactory.getLogger(UpdateProductServlet.class);
  private final ProductService productService;
  private final PageGenerator pageGenerator;

  @Override
  @SneakyThrows
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    String requestIdStr = req.getPathInfo().substring(1);
    if (!NumberUtils.isCreatable(requestIdStr)) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    Product product = productService.getById(Integer.parseInt(requestIdStr));
    pageGenerator.generatePage(resp.getWriter(), "update.html", HashMap.of("product", product).toJavaMap());
    resp.setStatus(HttpServletResponse.SC_OK);
  }

  @Override
  @SneakyThrows
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
    Product product = WebUtils.getProductFromRequestBody(req);
    if (productService.update(product)) {
      resp.setStatus(HttpServletResponse.SC_OK);
    } else {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }
}
