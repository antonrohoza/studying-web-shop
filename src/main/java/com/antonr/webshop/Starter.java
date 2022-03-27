package com.antonr.webshop;

import com.antonr.webshop.config.ConnectionFactory;
import com.antonr.webshop.config.PropertiesReader;
import com.antonr.webshop.dao.ProductDao;
import com.antonr.webshop.dao.UserDao;
import com.antonr.webshop.dao.jdbc.JdbcProductDao;
import com.antonr.webshop.dao.jdbc.JdbcUserDao;
import com.antonr.webshop.service.CartService;
import com.antonr.webshop.service.ProductService;
import com.antonr.webshop.service.SecurityService;
import com.antonr.webshop.service.UserService;
import com.antonr.webshop.web.filter.SecurityFilter;
import com.antonr.webshop.web.filter.SessionDefiningFilter;
import com.antonr.webshop.web.servlet.CartAddServlet;
import com.antonr.webshop.web.servlet.CartDeleteServlet;
import com.antonr.webshop.web.servlet.CartServlet;
import com.antonr.webshop.web.servlet.LoginServlet;
import com.antonr.webshop.web.servlet.AddProductServlet;
import com.antonr.webshop.web.servlet.DeleteProductServlet;
import com.antonr.webshop.web.servlet.NoRightsServlet;
import com.antonr.webshop.web.servlet.RegisterServlet;
import com.antonr.webshop.web.servlet.UpdateProductServlet;
import com.antonr.webshop.web.servlet.AllProductsServlet;
import com.antonr.webshop.web.util.PageGenerator;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {

        PropertiesReader propertiesReader = new PropertiesReader("/properties/application.properties");
        Properties properties = propertiesReader.getProperties();
        ConnectionFactory connectionFactory = new ConnectionFactory(properties);

        ProductDao productDao = new JdbcProductDao(connectionFactory);
        UserDao userDao = new JdbcUserDao(connectionFactory);

        ProductService productService = new ProductService(productDao);
        UserService userService = new UserService(userDao);
        SecurityService securityService = new SecurityService(userService);
        CartService cartService = new CartService(productService);

        SecurityFilter securityFilter = new SecurityFilter(securityService);
        SessionDefiningFilter sessionDefiningFilter = new SessionDefiningFilter(securityService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        PageGenerator pageGenerator = new PageGenerator();
        contextHandler.addServlet(new ServletHolder(new AllProductsServlet(productService, pageGenerator)), "/products");
        contextHandler.addServlet(new ServletHolder(new AddProductServlet(productService, pageGenerator)), "/product/add");
        contextHandler.addServlet(new ServletHolder(new UpdateProductServlet(productService, pageGenerator)), "/product/update/*");
        contextHandler.addServlet(new ServletHolder(new DeleteProductServlet(productService)), "/product/delete");
        contextHandler.addServlet(new ServletHolder(new LoginServlet(pageGenerator, securityService)), "/login");
        contextHandler.addServlet(new ServletHolder(new RegisterServlet(securityService, userService)), "/register");
        contextHandler.addServlet(new ServletHolder(new CartServlet()), "/cart");
        contextHandler.addServlet(new ServletHolder(new CartAddServlet(cartService)), "/cart/add");
        contextHandler.addServlet(new ServletHolder(new CartDeleteServlet(cartService)), "/cart/delete");
        contextHandler.addServlet(new ServletHolder(new NoRightsServlet(pageGenerator)), "/noRights");

        contextHandler.addFilter(new FilterHolder(securityFilter), "/product/*", EnumSet.of(DispatcherType.REQUEST));
        // TODO: how to define session for request one time(when trying to add product to cart session == null)
        contextHandler.addFilter(new FilterHolder(sessionDefiningFilter), "/cart/*", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter(new FilterHolder(sessionDefiningFilter), "/products", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(contextHandler);
        server.start();
    }
}
