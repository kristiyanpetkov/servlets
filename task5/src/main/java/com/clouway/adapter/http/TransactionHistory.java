package com.clouway.adapter.http;

import com.clouway.core.ConnectionProvider;
import com.clouway.core.FundsRepository;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 13.06.16.
 */
@WebServlet(name = "TransactionHistory")
public class TransactionHistory extends HttpServlet {
  private Integer offset = 0;
  private FundsRepository fundsRepository;

  public TransactionHistory(FundsRepository fundsRepository) {
    this.fundsRepository = fundsRepository;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    Integer numberOfRows = fundsRepository.getNumberOfID();
    String paginationDirection = request.getParameter("pagination");

    if (paginationDirection == null) {
      printPage(out);
      return;
    }

    if (paginationDirection.equals("next") && numberOfRows - offset > 20) {
      offset += 20;
      printPage(out);
      return;
    }

    if (paginationDirection.equals("previous") && offset != 0) {
      offset -= 20;
      printPage(out);
      return;
    }

    if (paginationDirection.equals("previous") && offset == 0) {
      offset = 0;
      printPage(out);
    }

    printPage(out);
  }


  private void printPage(PrintWriter out) {
    Integer limit = 20;
    fundsRepository.getHistory(out, limit, offset);
    out.println("<form action=\"/history\" method=\"get\">");
    out.print("<input type=\"submit\" name=\"pagination\" value=\"previous\">");
    out.print("<input type=\"submit\" name=\"pagination\" value=\"next\">");
    out.println("</form>");
    out.print("<a href=\"/useraccount\">Back</a>");
    out.close();
  }
}
