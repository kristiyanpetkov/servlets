package com.clouway.adapter.http;

import com.clouway.core.User;
import com.clouway.core.UserRepository;
import com.clouway.core.Validator;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 06.06.16.
 */
public class RegisterControllerTest {
  private RegisterController registerController;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  UserRepository userRepo;

  @Mock
  Validator userValidator;

  @Before
  public void setUp() {
    registerController = new RegisterController(userRepo, userValidator);
  }

  @Test
  public void happyPath() throws Exception {
    final User user = new User("GlobalAdmin", "admin123456", "admin@clouwaybank.com");
    final String email = user.getEmail();
    final String password = user.getPassword();
    final String username = user.getUsername();

    context.checking(new Expectations() {{
      oneOf(request).getParameter("regname");
      will(returnValue(username));
      oneOf(request).getParameter("regpassword");
      will(returnValue(password));
      oneOf(request).getParameter("email");
      will(returnValue(email));

      oneOf(userValidator).isValid(username, password, email);
      will(returnValue(true));

      oneOf(userRepo).findByEmail(email);
      will(returnValue(null));

      oneOf(userRepo).register(user);

      oneOf(response).sendRedirect("/login?errorMsg=Registration successfull!");
    }});

    registerController.doPost(request, response);
  }

  @Test
  public void insertInvalidData() throws Exception {
    final User user = new User("Admin", "admin123456", "admin;';'clouwaybank.com");
    final String email = user.getEmail();
    final String password = user.getPassword();
    final String username = user.getUsername();

    context.checking(new Expectations() {{
      oneOf(request).getParameter("regname");
      will(returnValue(username));
      oneOf(request).getParameter("regpassword");
      will(returnValue(password));
      oneOf(request).getParameter("email");
      will(returnValue(email));

      oneOf(userValidator).isValid(username, password, email);
      will(returnValue(false));

      oneOf(response).sendRedirect("/register?errorMsg=Input data is not in a valid format! Username and password should be between 6-16 characters and can contain only letters and digits.");
    }});

    registerController.doPost(request, response);
  }

  @Test
  public void registerAlreadyExistingUser() throws Exception {
    final User user = new User("GlobalAdmin", "admin123456", "admin@clouwaybank.com");
    final String email = user.getEmail();
    final String password = user.getPassword();
    final String username = user.getUsername();

    context.checking(new Expectations() {{
      oneOf(request).getParameter("regname");
      will(returnValue(username));
      oneOf(request).getParameter("regpassword");
      will(returnValue(password));
      oneOf(request).getParameter("email");
      will(returnValue(email));

      oneOf(userValidator).isValid(username, password, email);
      will(returnValue(true));

      oneOf(userRepo).findByEmail(email);
      will(returnValue(user));

      oneOf(response).sendRedirect("/register?errorMsg=Username with such an email already exist!");
    }});
    registerController.doPost(request, response);
  }
}
