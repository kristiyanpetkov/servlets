package com.clouway.http;

import com.clouway.core.User;
import com.clouway.core.UserRepository;
import com.clouway.core.UserValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 27.05.16.
 */
@WebServlet(name = "LoginController")
public class LoginController extends HttpServlet {
  private UserRepository userRepository;

  public LoginController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    UserValidator userValidator = new UserValidator();

    String email = request.getParameter("email");
    String password = request.getParameter("password");

    if (userValidator.isValid(email, password)) {
      authorize(email, password, response);
    } else {
      response.sendRedirect("/login?errorMsg=<h2 style='color:red'>Username or password invalid format!</h2>");
    }
  }

  private void authorize(String email, String password, HttpServletResponse response) throws IOException, ServletException {
    User user = userRepository.findByEmail(email);
    if (user != null) {
      if (user.getPassword().equals(password)) {
        Cookie cookie = new Cookie("sessionId", email);
        response.addCookie(cookie);
        response.sendRedirect("/welcomehomepage");
      } else {
        response.sendRedirect("/login?errorMsg=<h2 style='color:red'>Wrong password!</h2");
      }
    } else {
      response.sendRedirect("/login?errorMsg=<h2 style='color:red'>No such username!</h2");
    }
  }
}
