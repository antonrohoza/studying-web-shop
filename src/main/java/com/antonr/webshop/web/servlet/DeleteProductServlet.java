package com.antonr.webshop.web.servlet;

import com.antonr.webshop.service.ProductService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class DeleteProductServlet extends HttpServlet {

  private final ProductService productService;

  @Override
  @SneakyThrows
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    int id = Integer.parseInt(req.getParameter("id"));
    if (productService.delete(id)) {
      productDeleted(resp);
    } else {
      productWasntDeleted(resp, id);
    }
  }

  private void productWasntDeleted(HttpServletResponse resp, int id) throws IOException {
    resp.getWriter().println("Product by id: " + id + " wasn't deleted!");
    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
  }

  private void productDeleted(HttpServletResponse resp) throws IOException {
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.sendRedirect("/products");
  }
}
