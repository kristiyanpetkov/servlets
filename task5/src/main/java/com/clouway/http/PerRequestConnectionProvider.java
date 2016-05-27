package com.clouway.http;

import com.clouway.core.ConnectionProvider;

import java.sql.Connection;

/**
 * Created by clouway on 25.05.16.
 */
public class PerRequestConnectionProvider implements ConnectionProvider {
  public Connection get() {
    return ConnectionFilter.getCurrentConnection();
  }
}
