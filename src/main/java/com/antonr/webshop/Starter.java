package com.antonr.webshop;

import com.antonr.webshop.config.ConnectionFactory;
import com.antonr.webshop.config.PropertiesReader;
import com.antonr.webshop.dao.ProductDao;
import com.antonr.webshop.dao.jdbc.JdbcProductDao;
import com.antonr.webshop.service.ProductService;
import com.antonr.webshop.web.LoginServlet;
import com.antonr.webshop.web.ProductAddServlet;
import com.antonr.webshop.web.ProductDeleteServlet;
import com.antonr.webshop.web.ProductUpdateServlet;
import com.antonr.webshop.web.AllProductsServlet;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {

        PropertiesReader propertiesReader = new PropertiesReader("/properties/application.properties");
        Properties properties = propertiesReader.getProperties();
        ConnectionFactory connectionFactory = new ConnectionFactory(properties);

        List<String> tokens = new ArrayList<>();
        ProductDao productDao = new JdbcProductDao(connectionFactory);
        ProductService productService = new ProductService(productDao);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(new AllProductsServlet(productService, tokens)), "/");
        contextHandler.addServlet(new ServletHolder(new ProductAddServlet(productService)), "/add");
        contextHandler.addServlet(new ServletHolder(new ProductUpdateServlet(productService)), "/update/*");
        contextHandler.addServlet(new ServletHolder(new ProductDeleteServlet(productService)), "/delete");
        contextHandler.addServlet(new ServletHolder(new LoginServlet(tokens)), "/login");

        Server server = new Server(8080);
        server.setHandler(contextHandler);
        server.start();
    }
}
