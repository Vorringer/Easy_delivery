package netgloo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import netgloo.models.Comment;
import netgloo.models.CommentDao;
import netgloo.models.User;
import tool.Time;
import netgloo.models.Client;
import netgloo.models.ClientDao;


/**
 * A class to test interactions with the MySQL database using the CommentDao class.
 *
 * @author netgloo
 */
@Controller
public class CommentController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  /**
   * /add  --> add a new Comment and save it in the database.
   * 
   * @param status Comment's status
   * @param info Comment's info
   * @return A string describing if the Comment is succesfully created or not.
   */
  @RequestMapping("/addComment")
  @ResponseBody
  public String create(short status,String info,long shopId,long clientId,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return session.getAttribute("name").toString();
    Comment Comment = null;
    try {
    	String now=Time.getTime();
    	Client Client=ClientDao.findOne(clientId);
      Comment = new Comment(status,info,now,Client.getInfo(),shopId,clientId);
      CommentDao.save(Comment);
    }
    catch (Exception ex) {
      return "Error creating the Comment: " + ex.toString();
    }
    return "Comment succesfully created! (id = " + Comment.getId() + ")";
  }
  
  /**
   * /delete  --> Delete the Comment having the passed id.
   * 
   * @param id The id of the Comment to delete
   * @return A string describing if the Comment is succesfully deleted or not.
   */
  @RequestMapping("/deleteComment")
  @ResponseBody
  public String delete(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    try {
      Comment Comment = new Comment(id);
      CommentDao.delete(Comment);
    }
    catch (Exception ex) {
      return "Error deleting the Comment: " + ex.toString();
    }
    return "Comment succesfully deleted!";
  }
 
  
  
  
   
 
  /**
   * /get-by-id  --> Return the info for the Comment having the passed id.
   * 
   * @param id The id to search in the database.
   * @return The Comment name or a message error if the user is not found.
   */
  @RequestMapping("/getCommentByShopId")
  @ResponseBody
  public Iterable<Comment>  getNameById(long shopId,HttpServletRequest request) {
	  HttpSession session=request.getSession();
	  Iterable<Comment> Comment=null;
		if(session.getAttribute("name")==null)return null;
    try {
         Comment = CommentDao.findByShopId(shopId);
    }
    catch (Exception ex) {
      return null;
    }
    return Comment;
    
    
  }
  
  
  /**
   * /getAll  --> get the Comment having the passed id.
   * 
   * @return the Comment
   */
  @RequestMapping("/getAllComment")
  @ResponseBody
  public Iterable<Comment> getAllComment(HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
	  Iterable<Comment> Comment = null;
    try {    
      Comment=CommentDao.findAll();
    }
    catch (Exception ex) {
    }
	return Comment;
  }
  
  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  @Autowired
  private CommentDao CommentDao;
  @Autowired
  private ClientDao ClientDao;
  
} // class CommentController


