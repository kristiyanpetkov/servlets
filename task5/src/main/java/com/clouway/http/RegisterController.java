package com.clouway.http;

import com.clouway.core.User;
import com.clouway.core.UserRepository;
import com.clouway.core.UserValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by clouway on 27.05.16.
 */
@WebServlet(name = "RegisterController")
public class RegisterController extends HttpServlet {
  private UserRepository userRepository;
  private RequestDispatcher rd;

  public RegisterController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    UserValidator userValidator = new UserValidator();
    rd = request.getRequestDispatcher("/register");

    String userName = request.getParameter("regname");
    String password = request.getParameter("regpassword");
    String email = request.getParameter("email");

    if (userValidator.isValid(userName, password, email)) {
      if (checkIfExist(email)) {
        request.setAttribute("errorMsg", "<h2 style='color:red'>Username with such an email already exist!</h2>");
        rd.forward(request, response);
      } else {
        userRepository.register(new User(userName, password, email));
        rd = request.getRequestDispatcher("/login");
        request.setAttribute("errorMsg", "<h2 style='color:green'>Registration successfull!</h2>");
        rd.forward(request, response);
      }
    } else {
      request.setAttribute("errorMsg", "<h2 style='color:red'>Input data not in valid format! Username and password should be between 6-16 characters and can contain only letters and digits.</h2>");
      rd.forward(request, response);
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

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  }
}
