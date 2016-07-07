package com.clouway.adapter.http;

import com.google.inject.servlet.ServletModule;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 06.07.16.
 */
public class MyServletModule extends ServletModule {
  @Override
  protected void configureServlets() {
    filter("/*").through(ConnectionFilter.class);
    filter("/*").through(SecurityFilter.class);
    filter("/login").through(LoginFilter.class);

    serve("/login").with(LoginPage.class);
    serve("/bankoperationhandler").with(BankOperationHandler.class);
    serve("/register").with(RegisterPage.class);
    serve("/history").with(TransactionHistory.class);
    serve("/useraccount").with(UserAccount.class);
    serve("/logincontroller").with(LoginController.class);
    serve("/registercontroller").with(RegisterController.class);
    serve("/logoutcontroller").with(LogoutController.class);
  }
}
