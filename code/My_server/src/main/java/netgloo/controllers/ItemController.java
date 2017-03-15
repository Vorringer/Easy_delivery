package netgloo.controllers;

import netgloo.models.Item;
import netgloo.models.ItemDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A class to test interactions with the MySQL database using the ItemDao class.
 *
 * @author netgloo
 */
@Controller
public class ItemController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  /**
   * /create  --> Create a new Item and save it in the database.
   * 
   * @param name Item's name
   * @param price Item's price
   * @param shopId Item's shopId
   * @param status Item's status
   * @return A string describing if the Item is succesfully created or not.
   */
  @RequestMapping("/createItem")
  @ResponseBody
  public String create(String name, String price,long shopId,short status,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    Item Item = null;
    try {
      Item = new Item(name,status,price,shopId);
      ItemDao.save(Item);
    }
    catch (Exception ex) {
      return "Error creating the Item: " + ex.toString();
    }
    return "Item succesfully created! (id = " + Item.getId() + ")";
  }
  
  /**
   * /delete  --> Delete the Item having the passed id.
   * 
   * @param id The id of the Item to delete
   * @return A string describing if the Item is succesfully deleted or not.
   */
  @RequestMapping("/deleteItem")
  @ResponseBody
  public String delete(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    try {
      Item Item = new Item(id);
      ItemDao.delete(Item);
    }
    catch (Exception ex) {
      return "Error deleting the Item: " + ex.toString();
    }
    return "Item succesfully deleted!";
  }
  

  
  /**
   * /get-by-shopId  --> Return the info of the Item having the passed shopId.
   * 
   * @param shopId The shopId to search in the database.
   * @return The info of the Item.
   */
  @RequestMapping("/getItemByShopId")
  @ResponseBody
  public Iterable<Item> getNameById(long shopId,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		Iterable<Item> Item=null;
    try {
       Item = ItemDao.findByShopId(shopId);
    }
    catch (Exception ex) {
      return null;
    }
    return Item;
    
    
  }
  

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  @Autowired
  private ItemDao ItemDao;
  
} // class ItemController
