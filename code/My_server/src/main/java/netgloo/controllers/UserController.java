package netgloo.controllers;

import netgloo.models.User;
import netgloo.models.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A class to test interactions with the MySQL database using the UserDao class.
 *
 * @author netgloo
 */
@Controller
public class UserController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  /**
   * /create  --> Create a new user and save it in the database.
   * 
   * @param phone User's phone
   * @param name User's name
   * @param shopId User's shopId
   * @param password User's password
   * @param status User's status
   * @return A string describing if the user is succesfully created or not.
   */
  @RequestMapping("/createUser")
  @ResponseBody
  public String create(String phone, String name,long shopId,String password,short status,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    User user = null;
    try {
      user = new User(phone, name,shopId,password,status);
      userDao.save(user);
    }
    catch (Exception ex) {
      return "Error creating the user: " + ex.toString();
    }
    return "User succesfully created! (id = " + user.getId() + ")";
  }
  
  /**
   * /delete  --> Delete the user having the passed id.
   * 
   * @param id The id of the user to delete
   * @return A string describing if the user is succesfully deleted or not.
   */
  @RequestMapping("/deleteUser")
  @ResponseBody
  public String delete(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    try {
      User user = new User(id);
      userDao.delete(user);
    }
    catch (Exception ex) {
      return "Error deleting the user: " + ex.toString();
    }
    return "User succesfully deleted!";
  }
  
  /**
   * /get-by-Name  --> Return the id for the user having the passed email.
   * 
   * @param name The name to search in the database.
   * @return The user id or a message error if the user is not found.
   */
  @RequestMapping("/getUserByName")
  @ResponseBody
  public String getByName(String name,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    String userId;
    try {
      User user = userDao.findByName(name);
      userId = String.valueOf(user.getId());
    }
    catch (Exception ex) {
      return "User not found";
    }
    return "The user id is: " + userId;
    
    
  }
  
  /**
   * /get-by-id  --> Return the id for the user having the passed email.
   * 
   * @param id The id to search in the database.
   * @return The user id or a message error if the user is not found.
   */
  @RequestMapping("/getUserNameById")
  @ResponseBody
  public String getNameById(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    String UserName=null;
    try {
      User user = userDao.findOne(id);
      UserName=user.getName();
    }
    catch (Exception ex) {
      return "User not found";
    }
    return UserName;
    
    
  }
  /**
   * /getUserName  --> Return the name for the user having the passed email.
   * 
   * @return The user name or a message error if the user is not found.
   */
  @RequestMapping("/getUserName")
  @ResponseBody
  public String getUserName(HttpServletRequest request) {
	  HttpSession session=request.getSession();
	  String answer="";
	  if(session.getAttribute("name")==null)return answer;
      return session.getAttribute("name").toString();
  }
  
  
  
  /**
   * /update  --> Update the email and the name for the user in the database 
   * having the passed id.
   * 
   * @param id The id for the user to update.
   * @param phone The new email.
   * @param name The new name.
   * @return A string describing if the user is succesfully updated or not.
   */
  @RequestMapping("/updateUser")
  @ResponseBody
  public String updateUser(long id, String phone, String name,long shopId,String password,short status,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    try {
      User user = userDao.findOne(id);
      user.setPhone(phone);
      user.setName(name);
      user.setShopId(shopId);
      user.setPassword(password);
      user.setStatus(status);
      userDao.save(user);
    }
    catch (Exception ex) {
      return "Error updating the user: " + ex.toString();
    }
    return "User succesfully updated!";
  }

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  @Autowired
  private UserDao userDao;
  
} // class UserController
