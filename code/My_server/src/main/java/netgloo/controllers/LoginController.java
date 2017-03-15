package netgloo.controllers;

import netgloo.models.User;
import netgloo.models.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A class to test interactions with the MySQL database using the UserDao class.
 *
 * @author netgloo
 */
@Controller
public class LoginController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  /**
   * /login  --> the user to login
   * @param name User's name
   * @param password User's password
   * @return A string describing if the user is succesfully created or not.
   */
  @RequestMapping("/login")
  @ResponseBody
  public User login(String name,String password,HttpServletRequest request) {
	HttpSession session=request.getSession();
	session.setAttribute("name", name);
	
    User user = null;
    try {
      user = userDao.findByName(name);
     
    }
    catch (Exception ex) {
      return null;
    }
    String required=user.getPassword();
    if(required.equals(password))return user;
    else return null;
  }
  
  
  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  @Autowired
  private UserDao userDao;
  
} // class LoginController
