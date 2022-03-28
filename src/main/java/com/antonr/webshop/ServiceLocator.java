package com.antonr.webshop;

import com.antonr.webshop.config.ConnectionFactory;
import com.antonr.webshop.config.PropertiesReader;
import com.antonr.webshop.dao.ProductDao;
import com.antonr.webshop.dao.UserDao;
import com.antonr.webshop.dao.jdbc.JdbcProductDao;
import com.antonr.webshop.dao.jdbc.JdbcUserDao;
import com.antonr.webshop.service.ProductService;
import com.antonr.webshop.service.SecurityService;
import com.antonr.webshop.service.UserService;
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


    //config dao
    ProductDao productDao = new JdbcProductDao(connectionFactory);
    UserDao userDao = new JdbcUserDao(connectionFactory);

    //config service
    PageGenerator pageGenerator = new PageGenerator();
    ProductService productService = new ProductService(productDao);
    UserService userService = new UserService(userDao);
    SecurityService securityService = new SecurityService(userService);

    CONTEXT.put(PageGenerator.class, pageGenerator);
    CONTEXT.put(ProductService.class, productService);
    CONTEXT.put(UserService.class, userService);
    CONTEXT.put(SecurityService.class, securityService);
  }

  public static <T> T get(Class<T> clazz) {
    return clazz.cast(CONTEXT.get(clazz));
  }

}
