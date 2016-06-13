package com.clouway.core;

import java.io.PrintWriter;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 09.06.16.
 */
public interface FundsRepository {

  void deposit(double amount, String email);

  boolean withdraw(Double amount, String email);

  double getBalance(String email);

  void updateHistory(String date, String email, String operation, Double amount);

  void getHistory(PrintWriter out, Integer limit, Integer offset);

  Integer getNumberOfID();
}
