package netgloo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import netgloo.models.Shop;
import netgloo.models.ShopDao;
import netgloo.models.User;

/**
 * A class to test interactions with the MySQL database using the ShopDao class.
 *
 * @author netgloo
 */
@Controller
public class ShopController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  /**
   * /create  --> Create a new Shop and save it in the database.
   * 
   * @param status Shop's status
   * @param info Shop's info
   * @return A string describing if the Shop is succesfully created or not.
   */
  @RequestMapping("/createShop")
  @ResponseBody
  public String create(short status,String info,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    Shop Shop = null;
    try {
      Shop = new Shop(status,info);
      ShopDao.save(Shop);
    }
    catch (Exception ex) {
      return "Error creating the Shop: " + ex.toString();
    }
    return "Shop succesfully created! (id = " + Shop.getId() + ")";
  }
  
  /**
   * /delete  --> Delete the Shop having the passed id.
   * 
   * @param id The id of the Shop to delete
   * @return A string describing if the Shop is succesfully deleted or not.
   */
  @RequestMapping("/deleteShop")
  @ResponseBody
  public String delete(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    try {
      Shop Shop = new Shop(id);
      ShopDao.delete(Shop);
    }
    catch (Exception ex) {
      return "Error deleting the Shop: " + ex.toString();
    }
    return "Shop succesfully deleted!";
  }
 
  
  /**
   * /update  --> Update the email and the name for the Shop in the database 
   * having the passed id.
   * 
   * @param id The id for the Shop to update.
   * @param status Shop's new status
   * @param info Shop's new info
   * @return A string describing if the Shop is succesfully updated or not.
   */
  @RequestMapping("/updateShop")
  @ResponseBody
  public String updateShop(long id,short status,String info,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    try {
      Shop Shop = ShopDao.findOne(id);
      Shop.setInfo(info);
      Shop.setStatus(status);
      ShopDao.save(Shop);
    }
    catch (Exception ex) {
      return "Error updating the Shop: " + ex.toString();
    }
    return "Shop succesfully updated!";
  }
  
   
  /**
   * /getOne  --> get the Shop having the passed id.
   * 
   * @param id The id of the Shop to get
   * @return the Shop
   */
  @RequestMapping("/getOneShop")
  @ResponseBody
  public Shop getOneShop(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
	  Shop Shop =null;
    try {    
      Shop=ShopDao.findOne(id);
    }
    catch (Exception ex) {
    }
	return Shop;
  }
  /**
   * /get-by-id  --> Return the info for the shop having the passed id.
   * 
   * @param id The id to search in the database.
   * @return The shop name or a message error if the user is not found.
   */
  @RequestMapping("/getShopNameById")
  @ResponseBody
  public String getNameById(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    String ShopName=null;
    try {
      Shop shop = ShopDao.findOne(id);
      ShopName=shop.getInfo();
    }
    catch (Exception ex) {
      return "Shop not found";
    }
    return ShopName;
    
    
  }
  
  
  /**
   * /getAll  --> get the Shop having the passed id.
   * 
   * @return the Shop
   */
  @RequestMapping("/getAllShop")
  @ResponseBody
  public Iterable<Shop> getAllShop(HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
	  Iterable<Shop> Shop = null;
    try {    
      Shop=ShopDao.findAll();
    }
    catch (Exception ex) {
    }
	return Shop;
  }
  
  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  @Autowired
  private ShopDao ShopDao;
  
} // class ShopController


