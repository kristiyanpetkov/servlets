package com.clouway.server;

/**
 * Created by clouway on 26.05.16.
 */
public class App {
  public static void main(String[] args) {
    Jetty jetty = new Jetty(8080);
    jetty.start();
  }
}
