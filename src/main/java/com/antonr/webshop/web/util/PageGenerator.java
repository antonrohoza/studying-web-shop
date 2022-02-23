package com.antonr.webshop.web.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import org.apache.log4j.Logger;

public final class PageGenerator {

  private static final Logger LOG = Logger.getLogger(PageGenerator.class);
  private static final String RESOURCES_PATH = PageGenerator.class.getResource(File.separator).getPath();
  private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_31);

  private PageGenerator() {
  }

  static {
    try {
      CONFIGURATION.setTemplateLoader(new FileTemplateLoader(new File(RESOURCES_PATH)));
    } catch (IOException e) {
      LOG.error("Failed to set configuration", e);
      throw new RuntimeException(e);
    }
  }

  public static void generatePage(PrintWriter writer, String filename, Map<String, ?> data) {
    try {
      Template template = CONFIGURATION.getTemplate(filename);
      template.process(data, writer);
    } catch (TemplateException | IOException e) {
      LOG.error("Failed to generate Page", e);
      throw new RuntimeException(e);
    }
  }
}