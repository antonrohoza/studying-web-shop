package com.antonr.webshop.web.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageGenerator {

  private static final Logger LOG = LoggerFactory.getLogger(PageGenerator.class);
  private static final String RESOURCES_PATH = PageGenerator.class.getResource("/template")
                                                                  .getPath();
  private static final Configuration CONFIGURATION = new Configuration(
      Configuration.VERSION_2_3_31);

  public PageGenerator() {
    try {
      CONFIGURATION.setTemplateLoader(new FileTemplateLoader(new File(RESOURCES_PATH)));
    } catch (IOException e) {
      LOG.error("Failed to set configuration", e);
      throw new RuntimeException(e);
    }
  }

  public void generatePage(PrintWriter writer, String filename, Map<String, ?> data) {
    try {
      Template template = CONFIGURATION.getTemplate(filename);
      template.process(data, writer);
    } catch (TemplateException | IOException e) {
      LOG.error("Failed to generate Page", e);
      throw new RuntimeException(e);
    }
  }

  public void generatePage(PrintWriter printWriter, String filename) {
    generatePage(printWriter, filename, Collections.emptyMap());
  }
}