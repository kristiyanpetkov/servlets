package com.clouway.core;

/**
 * Created by clouway on 25.05.16.
 */
public class User {
  private String username;
  private String password;
  private String email;

  public User(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }
}
