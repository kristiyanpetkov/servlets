package com.clouway.http;

import com.clouway.persistence.UserJdbcImpl;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.EnumSet;

/**
 * Created by clouway on 26.05.16.
 */
public class HttpServletContextListener implements ServletContextListener {
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    ServletContext servletContext = servletContextEvent.getServletContext();
    servletContext.addFilter("ConnectionFilter", new ConnectionFilter()).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    servletContext.addFilter("SessionFilter", new SessionFilter(new UserJdbcImpl(new PerRequestConnectionProvider()))).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/welcomehomepage");
    servletContext.addServlet("login", new LoginPage()).addMapping("/login");
    servletContext.addServlet("logincontroller", new LoginController(new UserJdbcImpl(new PerRequestConnectionProvider()))).addMapping("/logincontroller");
    servletContext.addServlet("registercontroller", new RegisterController(new UserJdbcImpl(new PerRequestConnectionProvider()))).addMapping("/registercontroller");
    servletContext.addServlet("register", new RegisterPage(new UserJdbcImpl(new PerRequestConnectionProvider()))).addMapping("/register");
    servletContext.addServlet("welcomehomepage", new UserAccountHomepage()).addMapping("/welcomehomepage");
  }

  public void contextDestroyed(ServletContextEvent servletContextEvent) {

  }
}
