package netgloo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import netgloo.models.Message;
import netgloo.models.MessageDao;
import netgloo.models.User;
import tool.Time;
import netgloo.models.Client;
import netgloo.models.UserDao;


/**
 * A class to test interactions with the MySQL database using the MessageDao class.
 *
 * @author netgloo
 */
@Controller
public class MessageController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  /**
   * /add  --> add a new Message and save it in the database.
   * 
   * @param status Message's status
   * @param info Message's info
   * @param sender Message's sender
   * @param receiver Message's receiver
   * @return A string describing if the Message is succesfully created or not.
   */
  @RequestMapping("/addMessage")
  @ResponseBody
  public String create(short status,String info,long sender,long receiver,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    Message Message = null;
    try {
    	String now=Time.getTime();
    	User u1=UserDao.findOne(sender);
    	User u2=UserDao.findOne(receiver);
      Message = new Message(status,info,now,sender,u1.getName(),receiver,u2.getName());
      MessageDao.save(Message);
    }
    catch (Exception ex) {
      return "Error creating the Message: " + ex.toString();
    }
    return "Message succesfully created! (id = " + Message.getId() + ")";
  }
  
  
  /**
   * /add  --> add a new Message and save it in the database.
   * 
   * @param status Message's status
   * @param info Message's info
   * @param sender Message's sender
   * @param receiver Message's receiver
   * @return A string describing if the Message is succesfully created or not.
   */
  @RequestMapping("/addMessageByName")
  @ResponseBody
  public String createByName(short status,String info,String sender,String receiver,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    Message Message = null;
    try {
    	String now=Time.getTime();
    	User u1=UserDao.findByName(sender);
    	User u2=UserDao.findByName(receiver);
      Message = new Message(status,info,now,u1.getId(),sender,u2.getId(),receiver);
      MessageDao.save(Message);
    }
    catch (Exception ex) {
      return "Error creating the Message: " + ex.toString();
    }
    return "Message succesfully created! (id = " + Message.getId() + ")";
  }
  
  /**
   * /delete  --> Delete the Message having the passed id.
   * 
   * @param id The id of the Message to delete
   * @return A string describing if the Message is succesfully deleted or not.
   */
  @RequestMapping("/deleteMessage")
  @ResponseBody
  public String delete(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    try {
      Message Message = new Message(id);
      MessageDao.delete(Message);
    }
    catch (Exception ex) {
      return "Error deleting the Message: " + ex.toString();
    }
    return "Message succesfully deleted!";
  }
 
  
  
  
   
 
  /**
   * /get-by-sender  --> Return the info for the Message having the passed id.
   * 
   * @param sender The id to search in the database.
   * @return The Message name or a message error if the user is not found.
   */
  @RequestMapping("/getMessageBySenderId")
  @ResponseBody
  public Iterable<Message>getMessageBySenderId(long senderId,HttpServletRequest request) {
	  HttpSession session=request.getSession();
	  Iterable<Message> Message=null;
		if(session.getAttribute("name")==null)return null;
    try {
         Message = MessageDao.findBySender(senderId);
    }
    catch (Exception ex) {
      return null;
    }
    return Message;
    
    
  }
  
  /**
   * /get-by-receiver  --> Return the info for the Message having the passed id.
   * 
   * @param receiver The id to search in the database.
   * @return The Message name or a message error if the user is not found.
   */
  @RequestMapping("/getMessageByReceiverId")
  @ResponseBody
  public Iterable<Message>  getMessageByReceiverId(long receiverId,HttpServletRequest request) {
	  HttpSession session=request.getSession();
	  Iterable<Message> Message=null;
		if(session.getAttribute("name")==null)return null;
    try {
         Message = MessageDao.findByReceiver(receiverId);
    }
    catch (Exception ex) {
      return null;
    }
    return Message;
    
    
  }
  
  /**
   * /get-by-receiver&sender  --> Return the info for the Message having the passed id.
   * 
   * @param receiver The id to search in the database.
   * @return The Message name or a message error if the user is not found.
   */
  @RequestMapping("/getMessageBySenderIdAndReceiverId")
  @ResponseBody
  public Iterable<Message>  getMessageBySenderAndReceiverId(long senderId,long receiverId,HttpServletRequest request) {
	  HttpSession session=request.getSession();
	  Iterable<Message> Message=null;
		if(session.getAttribute("name")==null)return null;
    try {
         Message = MessageDao.findBySenderAndReceiver(senderId,receiverId);
    }
    catch (Exception ex) {
      return null;
    }
    return Message;
    
    
  }
  
  
 
  
  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  @Autowired
  private MessageDao MessageDao;
  @Autowired
  private UserDao UserDao;
  
} // class MessageController


