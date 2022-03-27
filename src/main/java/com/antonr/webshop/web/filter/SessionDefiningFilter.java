package com.antonr.webshop.web.filter;

import com.antonr.webshop.entity.Session;
import com.antonr.webshop.service.SecurityService;
import io.vavr.collection.Array;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

public class SessionDefiningFilter implements Filter {

  public static final String SESSION = "session";
  private final SecurityService securityService;

  public SessionDefiningFilter(SecurityService securityService) {
    this.securityService = securityService;
  }

  @Override
  @SneakyThrows
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    Cookie[] cookies = httpServletRequest.getCookies();
    Session session = securityService.getSessionByToken(getUserToken(cookies));
    httpServletRequest.setAttribute(SESSION, session);
    filterChain.doFilter(httpServletRequest, response);
  }

  public String getUserToken(Cookie[] cookies) {
    return Array.of(cookies)
                .filter(cookie -> "user-token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .collect(Collectors.joining());
  }
}
