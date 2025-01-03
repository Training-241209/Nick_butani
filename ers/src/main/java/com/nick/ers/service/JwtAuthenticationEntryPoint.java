package com.nick.ers.service;

import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.*;

@Component
public class JwtAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, IOException {

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.getWriter().write("{ \"message\": \"" + authException.getMessage() + "\" }");
  }

  @Override
  public void afterPropertiesSet() {
    setRealmName("JWT Authentication");
    super.afterPropertiesSet();
  }
}