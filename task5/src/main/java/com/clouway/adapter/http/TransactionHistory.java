package com.clouway.adapter.http;

import com.clouway.core.FundsRepository;
import com.clouway.core.Pager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 13.06.16.
 */
@WebServlet(name = "TransactionHistory")
public class TransactionHistory extends HttpServlet {
  private FundsRepository fundsRepository;
  private Integer offset;

  public TransactionHistory(FundsRepository fundsRepository) {
    this.fundsRepository = fundsRepository;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();

    if (session.getAttribute("offset") == null) {
      session.setAttribute("offset", 0);
    }

    offset = (Integer) session.getAttribute("offset");
    PrintWriter out = response.getWriter();
    Integer numberOfRows = fundsRepository.getNumberOfID();
    String paginationDirection = request.getParameter("pagination");

    if (paginationDirection == null) {
      printPage(out);
      return;
    }

    if (paginationDirection.equals("next") && numberOfRows - offset > 20) {
      session.setAttribute("offset", offset += 20);
      printPage(out);
      return;
    }

    if (paginationDirection.equals("previous") && offset != 0) {
      session.setAttribute("offset", offset -= 20);
      printPage(out);
      return;
    }

    if (paginationDirection.equals("previous") && offset == 0) {
      session.setAttribute("offset", 0);
      printPage(out);
      return;
    }

    printPage(out);
  }

  private void printPage(PrintWriter out) {
    Integer limit = 20;
    List<Pager> pagers = fundsRepository.getHistory(limit, offset);
    out.print("<table width=25% border=1>");
    out.print("<center><h1>Transaction History:</h1></center>");
    out.print("<tr>");
    out.print("<th> ID </th>");
    out.print("<th> Date </th>");
    out.print("<th> E-mail </th>");
    out.print("<th> Operation </th>");
    out.print("<th> Amount</th>");
    out.print("</tr>");
    for (Pager pager : pagers) {
      out.print("<tr>");
      out.print("<td>" + pager.ID + "</td>");
      out.print("<td>" + pager.date + "</td>");
      out.print("<td>" + pager.email + "</td>");
      out.print("<td>" + pager.operation + "</td>");
      out.print("<td>" + pager.amount + "</td>");
      out.print("</tr>");
    }
    out.print("</table>");
    out.println("<form action=\"/history\" method=\"get\">");
    out.print("<input type=\"submit\" name=\"pagination\" value=\"previous\">");
    out.print("<input type=\"submit\" name=\"pagination\" value=\"next\">");
    out.println("</form>");
    out.print("<a href=\"/useraccount\">Back</a>");
    out.close();
  }
}
