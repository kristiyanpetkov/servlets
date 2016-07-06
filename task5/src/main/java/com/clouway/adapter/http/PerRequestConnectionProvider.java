package com.clouway.adapter.http;

import com.clouway.core.ConnectionProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.sql.Connection;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 25.05.16.
 */
@Singleton
public class PerRequestConnectionProvider implements ConnectionProvider {
  @Inject
  public Connection get() {
    return ConnectionFilter.getCurrentConnection();
  }
}
