package com.clouway.core;

import com.clouway.adapter.http.PerRequestConnectionProvider;
import com.clouway.adapter.persistence.PersistenceModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 06.07.16.
 */
public class ApplicationModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new PersistenceModule());
  }

  @Provides
  public ConnectionProvider getConnection() {
    return new PerRequestConnectionProvider();
  }

  @Provides
  public CookieFinder getCookie() {
    return new CookieFinderImpl();
  }

  @Provides
  public RandomGenerator getUUID() {
    return new RandomGeneratorImpl();
  }

  @Provides
  public Validator getValidator() {
    return new DataValidator();
  }
}
