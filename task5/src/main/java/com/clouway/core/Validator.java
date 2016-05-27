package com.clouway.core;

/**
 * Created by clouway on 27.05.16.
 */
public interface Validator {
  boolean isValid(String email, String password);

  boolean isValid(String username, String password, String email);
}
