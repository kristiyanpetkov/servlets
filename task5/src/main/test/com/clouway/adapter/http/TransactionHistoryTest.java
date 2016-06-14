package com.clouway.adapter.http;

import com.clouway.core.FundsRepository;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 13.06.16.
 */
public class TransactionHistoryTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  FundsRepository fundsRepository;

  @Mock
  HttpSession session;

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Test
  public void firstPage() throws Exception {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    TransactionHistory transactionHistory = new TransactionHistory(fundsRepository);

    context.checking(new Expectations() {{
      oneOf(request).getSession();
      will(returnValue(session));

      oneOf(session).getAttribute("offset");
      will(returnValue(null));
      oneOf(session).setAttribute("offset", 0);
      oneOf(session).getAttribute("offset");
      will(returnValue(0));

      oneOf(response).getWriter();
      will(returnValue(new PrintWriter(out)));

      oneOf(fundsRepository).getNumberOfID();
      will(returnValue(63));

      oneOf(request).getParameter("pagination");
      will(returnValue(null));

      oneOf(fundsRepository).getHistory(20,0);
    }});

    transactionHistory.doGet(request, response);
  }
}
