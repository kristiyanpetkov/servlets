package com.clouway.adapter.http;

import com.clouway.core.User;
import com.clouway.core.UserRepository;
import com.clouway.core.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 27.05.16.
 */
@WebServlet(name = "RegisterController")
public class RegisterController extends HttpServlet {
  private UserRepository userRepository;
  private Validator userValidator;

  public RegisterController(UserRepository userRepository, Validator userValidator) {
    this.userRepository = userRepository;
    this.userValidator = userValidator;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userName = request.getParameter("regname");
    String password = request.getParameter("regpassword");
    String email = request.getParameter("email");

    if (userValidator.isValid(userName, password, email)) {
      if (checkIfExist(email)) {
        response.sendRedirect("/register?errorMsg=Username with such an email already exist!");
      } else {
        userRepository.register(new User(userName, password, email));
        response.sendRedirect("/login?errorMsg=Registration successfull!");
      }
    } else {
      response.sendRedirect("/register?errorMsg=Input data is not in a valid format! Username and password should be between 6-16 characters and can contain only letters and digits.");
    }
  }

  private boolean checkIfExist(String email) {
    User user = userRepository.findByEmail(email);
    if (user != null) {
      return true;
    } else {
      return false;
    }
  }
}
