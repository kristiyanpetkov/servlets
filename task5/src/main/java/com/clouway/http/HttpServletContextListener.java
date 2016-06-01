package com.clouway.http;

import com.clouway.persistence.PersistentUserRepository;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.EnumSet;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 26.05.16.
 */
public class HttpServletContextListener implements ServletContextListener {
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    ServletContext servletContext = servletContextEvent.getServletContext();
    servletContext.addFilter("ConnectionFilter", new ConnectionFilter()).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    servletContext.addFilter("SecurityFilter", new SecurityFilter(new PersistentUserRepository(new PerRequestConnectionProvider()))).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/welcomehomepage");
    servletContext.addServlet("login", new LoginPage()).addMapping("/login");
    servletContext.addServlet("logincontroller", new LoginController(new PersistentUserRepository(new PerRequestConnectionProvider()))).addMapping("/logincontroller");
    servletContext.addServlet("registercontroller", new RegisterController(new PersistentUserRepository(new PerRequestConnectionProvider()))).addMapping("/registercontroller");
    servletContext.addServlet("register", new RegisterPage()).addMapping("/register");
    servletContext.addServlet("welcomehomepage", new UserAccount()).addMapping("/welcomehomepage");
  }

  public void contextDestroyed(ServletContextEvent servletContextEvent) {

  }
}
