package netgloo.controllers;

import netgloo.models.Food;
import netgloo.models.FoodDao;
import netgloo.models.Item;
import netgloo.models.ItemDao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A class to test interactions with the MySQL database using the FoodDao class.
 *
 * @author netgloo
 */
@Controller
public class FoodController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  /**
   * /create  --> Create a new Food and save it in the database.
   * 
   * @param orderId Food's orderId
   * @param itemId Food's itemId
   * @return A string describing if the Food is succesfully created or not.
   */
  @RequestMapping("/createFood")
  @ResponseBody
  public String create(long orderId,long itemId,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    Food Food = null;
    try {
      Food = new Food(orderId,itemId);
      FoodDao.save(Food);
    }
    catch (Exception ex) {
      return "Error creating the Food: " + ex.toString();
    }
    return "Food succesfully created! (id = " + Food.getId() + ")";
  }
  
  /**
   * /delete  --> Delete the Food having the passed id.
   * 
   * @param id The id of the Food to delete
   * @return A string describing if the Food is succesfully deleted or not.
   */
  @RequestMapping("/deleteFood")
  @ResponseBody
  public String delete(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    try {
      Food Food = new Food(id);
      FoodDao.delete(Food);
    }
    catch (Exception ex) {
      return "Error deleting the Food: " + ex.toString();
    }
    return "Food succesfully deleted!";
  }
  

  
  /**
   * /get-by-orderId  --> Return the info of the Food having the passed shopId.
   * 
   * @param orderId The shopId to search in the database.
   * @return The info of the Food.
   */
  @RequestMapping("/getFoodByOrderId")
  @ResponseBody
  public List<Item> getNameById(long orderId,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		List<Food> Food=null;
		List<Item> items=new ArrayList();
    try {
       Food = FoodDao.findByOrderId(orderId);
       
       for(int i=0;i<Food.size();i++)
       {
    	   Food foo=Food.get(i);
    	   Item tmp=ItemDao.findOne(foo.getItemId());
    	   items.add(tmp);
       }
       
 
       
    }
    catch (Exception ex) {
      return null;
    }
    return items;
    
    
  }
  

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  @Autowired
  private FoodDao FoodDao;
  @Autowired
  private ItemDao ItemDao;
  
} // class FoodController

