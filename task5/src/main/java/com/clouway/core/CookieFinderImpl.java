package com.clouway.core;

import com.google.inject.Singleton;

import javax.servlet.http.Cookie;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 06.06.16.
 */
@Singleton
public class CookieFinderImpl implements CookieFinder {

  public Cookie find(Cookie[] cookies) {
    Cookie cookie = null;
    for (int i = 0; i < cookies.length; i++) {
      if (cookies[i].getName().equals("sessionId")) {
        cookie = cookies[i];
      }
    }
    return cookie;
  }
}
