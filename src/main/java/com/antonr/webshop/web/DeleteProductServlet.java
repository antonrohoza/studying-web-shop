package com.antonr.webshop.web;

import com.antonr.webshop.service.ProductService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteProductServlet extends HttpServlet {

  private final ProductService productService;

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    int id = Integer.parseInt(req.getParameter("id"));
    if (productService.delete(id)) {
      resp.setStatus(HttpServletResponse.SC_OK);
      getServletContext().getRequestDispatcher("/").forward(req, resp);
    } else {
      resp.getWriter().println("Product by id: " + id + " wasn't deleted!");
    }
  }
}
