package com.clouway.core;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 09.06.16.
 */
public interface FundsRepository {

  void deposit(double amount, String email);

  boolean withdraw(Double amount, String email);

  double getBalance(String email);
}
