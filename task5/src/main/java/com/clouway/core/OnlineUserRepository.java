package com.clouway.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by clouway on 26.05.16.
 */
public class OnlineUserRepository {
  public static Set<String> loggedUserEmails = new HashSet<String>();

  public static void userLogin(String email) {
    loggedUserEmails.add(email);
  }

  public static void userLogout(String email) {
    loggedUserEmails.remove(email);
  }

  public static int currentUsersOnline() {
    return loggedUserEmails.size();
  }

}
