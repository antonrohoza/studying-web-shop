package com.antonr.webshop.config;

import com.antonr.webshop.web.util.PageGenerator;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import lombok.Getter;
import org.apache.log4j.Logger;

@Getter
public class PropertiesReader {
    private static final Logger LOG = Logger.getLogger(PropertiesReader.class);
    private final Properties properties;

    public PropertiesReader(String url) {
        properties = new Properties();
        loadProperties(url);
    }

    private void loadProperties(String url) {
        try (Reader reader = new InputStreamReader(this.getClass().getResourceAsStream(url))) {
            properties.load(reader);
        } catch (IOException e) {
            LOG.error("Can't set connection by using url:" + url + "!", e);
            throw new RuntimeException(e);
        }
    }
}
