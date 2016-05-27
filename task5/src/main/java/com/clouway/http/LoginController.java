package com.clouway.http;

import com.clouway.core.User;
import com.clouway.core.UserRepository;
import com.clouway.core.UserValidator;
import com.clouway.persistence.UserJdbcImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by clouway on 27.05.16.
 */
@WebServlet(name = "LoginController")
public class LoginController extends HttpServlet {
  private UserRepository userRepository;
  private RequestDispatcher rd;

  public LoginController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    UserValidator userValidator = new UserValidator();
    rd = request.getRequestDispatcher("/login");

    String email = request.getParameter("email");
    String password = request.getParameter("password");

    if (userValidator.isValid(email, password)) {
      authorize(email, password, response, request);
    } else {
      request.setAttribute("errorMsg", "<h2 style='color:red'>Username or password invalid format!</h2>");
      rd.forward(request, response);
    }
  }

  private void authorize(String email, String password, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
    User user = userRepository.findByEmail(email);
    if (user != null) {
      if (user.getPassword().equals(password)) {
        Cookie cookie = new Cookie("sessionId", email);
        response.addCookie(cookie);
        response.sendRedirect("/welcomehomepage");
      } else {
        request.setAttribute("errorMsg", "<h2 style='color:red'>Wrong password!</h2>");
        rd.forward(request, response);
      }
    } else {
      request.setAttribute("errorMsg", "<h2 style='color:red'>No such username!</h2");
      rd.forward(request, response);
    }
  }


  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  }
}
