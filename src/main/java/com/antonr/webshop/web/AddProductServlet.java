package com.antonr.webshop.web;

import com.antonr.webshop.entity.Product;
import com.antonr.webshop.service.ProductService;
import com.antonr.webshop.web.util.PageGenerator;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

@AllArgsConstructor
public class AddProductServlet extends HttpServlet {

  private static final Logger LOG = Logger.getLogger(UpdateProductServlet.class);
  private final ProductService productService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    PageGenerator.generatePage(resp.getWriter(), "template/add.html", new HashMap<>());
    resp.setStatus(HttpServletResponse.SC_OK);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Product product;
    try {
      product = getProduct(req);
    } catch (ParseException e) {
      LOG.error("Failed to get product", e);
      throw new RuntimeException(e);
    }
    boolean success = product != null && productService.save(product);
    resp.setStatus(success ? HttpServletResponse.SC_OK
        : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    resp.sendRedirect("/");
  }

  private static Product getProduct(HttpServletRequest req) throws ParseException {
    return Product.builder()
        .id(Integer.parseInt(req.getParameter("id")))
        .name(req.getParameter("name"))
        .price(Double.parseDouble(req.getParameter("price")))
        .creationDate(LocalDate.parse(req.getParameter("creation_date")))
        .build();
  }
}
