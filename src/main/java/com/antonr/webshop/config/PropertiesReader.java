package com.antonr.webshop.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;
import java.util.Properties;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class PropertiesReader {

  private static final Logger LOG = LoggerFactory.getLogger(PropertiesReader.class);
  private final Properties properties;

  public PropertiesReader(String url) {
    properties = new Properties();
    loadProperties(url);
  }

  private void loadProperties(String url) {
    try (InputStream inputStream = Objects.requireNonNull(this.getClass().getResourceAsStream(url));
         Reader reader = new InputStreamReader(inputStream)) {
      properties.load(reader);
    } catch (IOException e) {
      LOG.error("Can't set connection by using url:" + url + "!", e);
      throw new RuntimeException(e);
    }
  }
}
