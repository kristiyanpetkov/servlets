package com.clouway.adapter.http;

import com.clouway.core.ApplicationModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 06.07.16.
 */
public class MyGuiceServletConfig extends GuiceServletContextListener {
  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new MyServletModule(), new ApplicationModule());
  }
}
