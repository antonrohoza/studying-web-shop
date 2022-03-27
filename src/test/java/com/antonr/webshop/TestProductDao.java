package com.antonr.webshop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.antonr.webshop.dao.jdbc.JdbcProductDao;
import com.antonr.webshop.entity.Product;
import com.antonr.webshop.service.ProductService;
import io.vavr.collection.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class TestProductDao {

  static final Product PRODUCT_1 = Product.builder().id(1)
      .name("A")
      .price(1)
      .creationDate(LocalDateTime.MAX)
      .build();
  static final Product PRODUCT_2 = Product.builder().id(2)
      .name("B")
      .price(2)
      .creationDate(LocalDateTime.MIN)
      .build();
  JdbcProductDao mockJDBCProductDao = mock(JdbcProductDao.class);
  ProductService productService = new ProductService(mockJDBCProductDao);

  @Test
  void getAll() {
    when(mockJDBCProductDao.findAll()).thenReturn(List.of(PRODUCT_1, PRODUCT_2));
    assertEquals(2, productService.getAll().size());
  }

  @Test
  void findById() {
    when(mockJDBCProductDao.findById(PRODUCT_2.getId())).thenReturn(Optional.of(PRODUCT_2));
    assertEquals("B", productService.getById(PRODUCT_2.getId()).getName());
  }

  @Test
  void save() {
    when(mockJDBCProductDao.save(PRODUCT_1)).thenReturn(true);
    assertTrue(productService.save(PRODUCT_1));
  }

  @Test
  void delete() {
    when(mockJDBCProductDao.delete(PRODUCT_1.getId())).thenReturn(true);
    assertTrue(productService.delete(PRODUCT_1.getId()));
  }
}
