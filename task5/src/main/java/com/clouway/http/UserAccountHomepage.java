package com.clouway.http;

import com.clouway.core.OnlineUserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by clouway on 19.05.16.
 */
@WebServlet(name = "UserAccountHomepage")
public class UserAccountHomepage extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logout(request, response);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    printPage(out);
  }

  private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Cookie[] cookies = request.getCookies();
    for (int i = 0; i < cookies.length; i++) {
      if (cookies[i].getName().equals("sessionId")) {
        cookies[i].setMaxAge(0);
        OnlineUserRepository.userLogout(cookies[i].getValue());
        response.addCookie(cookies[i]);
        response.sendRedirect("/login");
      }
    }
  }

  public void printPage(PrintWriter out) {
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
    out.println("<title>LoginPage Form</title></head><body>");
    out.println("<h1> Welcome to our bank! </h1>");
    out.print("<form action=\"/welcomehomepage\" method=\"post\">");
    out.print("<input type=\"submit\" value=\"Logout\">");
    out.println("</body></html>");
    out.flush();
    out.close();
  }
}
