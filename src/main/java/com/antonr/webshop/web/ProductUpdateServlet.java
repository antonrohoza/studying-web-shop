package com.antonr.webshop.web;

import com.antonr.webshop.entity.Product;
import com.antonr.webshop.service.ProductService;
import com.antonr.webshop.web.util.PageGenerator;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import io.vavr.collection.HashMap;
import java.text.ParseException;
import java.time.LocalDate;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

@AllArgsConstructor
public class ProductUpdateServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ProductUpdateServlet.class);
    private final ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        Product product = productService.getById(id);
        PageGenerator.generatePage(resp.getWriter(), "template/update.html", HashMap.of("product", product).toJavaMap());
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Product product;
        try {
            product = getProductFromRequestBody(req);
        } catch (ParseException e) {
            LOG.error("Failed to get product", e);
            throw new RuntimeException(e);
        }
        if (productService.update(product) > 0) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private static Product getProductFromRequestBody(HttpServletRequest req)
        throws IOException, ParseException {
        JsonObject obj = JsonParser.parseReader(req.getReader()).getAsJsonObject();
        return Product.builder()
            .id(Integer.parseInt(obj.get("id").getAsString()))
            .name(obj.get("name").getAsString())
            .price(Double.parseDouble(obj.get("price").getAsString()))
            .creationDate(LocalDate.parse(obj.get("creation_date").getAsString()))
            .build();
    }
}
