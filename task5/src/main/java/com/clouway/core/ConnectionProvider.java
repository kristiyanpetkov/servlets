package com.clouway.core;

import java.sql.Connection;

/**
 * Created by clouway on 25.05.16.
 */
public interface ConnectionProvider {

  Connection get();
}
