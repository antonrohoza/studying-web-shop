package com.antonr.webshop.web.util;

import com.antonr.webshop.entity.Product;
import com.antonr.webshop.exception.InconsistentDataException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.math.NumberUtils;

@UtilityClass
public class WebUtils {

  public static Product getProductFromRequestBody(HttpServletRequest req) throws IOException {
    JsonObject obj = JsonParser.parseReader(req.getReader()).getAsJsonObject();
    String idStr = obj.get("id").getAsString();
    String nameStr = obj.get("name").getAsString();
    String priceStr = obj.get("price").getAsString();
    String creationDateStr = obj.get("creation_date").getAsString();
    if (!NumberUtils.isCreatable(idStr) || !NumberUtils.isCreatable(priceStr)) {
      throw new InconsistentDataException("Id or price cannot be parsed");
    }
    return Product.builder()
                  .id(Integer.parseInt(idStr))
                  .name(nameStr)
                  .price(Double.parseDouble(priceStr))
                  .creationDate(LocalDateTime.parse(creationDateStr))
                  .build();
  }

  public static Product getProductByParameters(HttpServletRequest req) {
    return Product.builder()
                  .id(Integer.parseInt(req.getParameter("id")))
                  .name(req.getParameter("name"))
                  .price(Double.parseDouble(req.getParameter("price")))
                  .creationDate(LocalDateTime.parse(req.getParameter("creation_date")))
                  .build();
  }
}
