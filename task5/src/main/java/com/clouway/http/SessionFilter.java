package com.clouway.http;

import com.clouway.core.User;
import com.clouway.core.UserRepository;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by clouway on 19.05.16.
 */
public class SessionFilter implements Filter {

  private UserRepository userRepository;

  public SessionFilter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  //  private Validator userValidator = getUserValidator();
//  private Validator passwordValidator = getPasswordValidator();
//  private userStore = DependencyManager.findByEmail(UserStore.class);

  public void init(FilterConfig filterConfig) throws ServletException {

  }

  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    Cookie[] cookies = request.getCookies();
    String email = "";
    for (int i = 0; i < cookies.length; i++) {
      if (cookies[i].getName().equals("sessionId")) {
        email = cookies[i].getValue();
      }
    }
    User user = userRepository.findByEmail(email);
    if (user != null) {
      filterChain.doFilter(request, response);
    } else {
      response.sendRedirect("/login");
    }
  }


  public void destroy() {

  }
}
