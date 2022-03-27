package com.antonr.webshop.web.filter;

import com.antonr.webshop.entity.Role;
import com.antonr.webshop.entity.Session;
import com.antonr.webshop.service.SecurityService;
import io.vavr.collection.Array;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SecurityFilter implements javax.servlet.Filter {

  private final SecurityService securityService;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    Cookie[] cookies = httpServletRequest.getCookies();
    Session session = getSession(cookies);
    if (!Role.ADMIN.equals(session.getUser().getRole())) {
      httpServletResponse.sendRedirect("/noRights");
      return;
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  private Session getSession(Cookie[] cookies) {
    return securityService.getSessionByToken(getUserToken(cookies));
  }

  private String getUserToken(Cookie[] cookies) {
    return Array.of(cookies)
                .filter(cookie -> "user-token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .collect(Collectors.joining());
  }
}
