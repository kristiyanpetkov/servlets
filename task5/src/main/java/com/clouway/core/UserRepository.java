package com.clouway.core;

/**
 * Created by clouway on 25.05.16.
 */
public interface UserRepository {

  void register(User user);

  User findByEmail(String email);
}
