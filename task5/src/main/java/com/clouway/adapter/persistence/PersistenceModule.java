package com.clouway.adapter.persistence;

import com.clouway.core.ConnectionProvider;
import com.clouway.core.FundsRepository;
import com.clouway.core.SessionRepository;
import com.clouway.core.UserRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 06.07.16.
 */
public class PersistenceModule extends AbstractModule {
  @Override
  protected void configure() {

  }

  @Provides
  public FundsRepository getFundsRepository(ConnectionProvider connectionProvider) {
    return new PersistentFundsRepository(connectionProvider);
  }

  @Provides
  public SessionRepository getSessionRepository(ConnectionProvider connectionProvider) {
    return new PersistentSessionRepository(connectionProvider);
  }

  @Provides
  public UserRepository getUserRepository(ConnectionProvider connectionProvider) {
    return new PersistentUserRepository(connectionProvider);
  }
}
