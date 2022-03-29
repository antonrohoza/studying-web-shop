package com.antonr.webshop;

import com.antonr.webshop.config.ConnectionFactory;
import com.antonr.webshop.config.PropertiesReader;
import com.antonr.webshop.dao.ProductDao;
import com.antonr.webshop.dao.jdbc.JdbcProductDao;
import com.antonr.webshop.service.ProductService;
import com.antonr.webshop.web.util.PageGenerator;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceLocator {

  private static final Map<Class<?>, Object> CONTEXT = new HashMap<>();

  static {
    PropertiesReader propertiesReader = new PropertiesReader("/properties/application.properties");
    Properties properties = propertiesReader.getProperties();
    ConnectionFactory connectionFactory = new ConnectionFactory(properties);

    ProductDao productDao = new JdbcProductDao(connectionFactory);

    PageGenerator pageGenerator = new PageGenerator();
    ProductService productService = new ProductService(productDao);

    CONTEXT.put(PageGenerator.class, pageGenerator);
    CONTEXT.put(ProductService.class, productService);
  }

  public static <T> T get(Class<T> clazz) {
    return clazz.cast(CONTEXT.get(clazz));
  }

}
