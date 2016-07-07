package com.clouway.server;


import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.util.EnumSet;


/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 26.05.16.
 */
public class Jetty {

  private final Server server;

  public Jetty(int port) {
    this.server = new Server(port);
  }

  public void start() {
    ServletContextHandler contextHandler = new ServletContextHandler(server,"/",ServletContextHandler.SESSIONS);
    contextHandler.addFilter(GuiceFilter.class,"/*", EnumSet.allOf(DispatcherType.class));
    contextHandler.addServlet(DefaultServlet.class,"/");
    contextHandler.setContextPath("/");
    contextHandler.addEventListener(new AppConfig());
    server.setHandler(contextHandler);
    try {
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
