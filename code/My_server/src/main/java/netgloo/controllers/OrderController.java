package netgloo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import netgloo.models.Order;
import netgloo.models.OrderDao;
import netgloo.models.Client;
import netgloo.models.ClientDao;
import netgloo.models.User;
import netgloo.models.UserDao;
import tool.Time;
import tool.Inform;

/**
 * A class to test interactions with the MySQL database using the OrderDao class.
 *
 * @author netgloo
 */
@Controller
public class OrderController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
 

/**
   * /create  --> Create a new Order and save it in the database.
   * 
   * @param clientId Order's clientId
   * @param shopId Order's shopId
   * @param userId Order's userId
   * @param longitude Order's longitude
   * @param latitude Order's latitude
   * @param status Order's status
   * @return A string describing if the Order is succesfully created or not.
   */
  @RequestMapping("/createOrder")
  @ResponseBody
  public String create(long clientId, long shopId,long userId,double longitude,double latitude,short status,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
	  Order Order = null;
    try {
    	String now=Time.getTime();
      Order = new Order(clientId, shopId,userId,longitude,latitude,status,now);
      OrderDao.save(Order);
    }
    catch (Exception ex) {
      return "Error creating the Order: " + ex.toString();
    }
    return "Order succesfully created! (id = " + Order.getId() + ")";
  }
  
  /**
   * /delete  --> Delete the Order having the passed id.
   * 
   * @param id The id of the Order to delete
   * @return A string describing if the Order is succesfully deleted or not.
   */
  @RequestMapping("/deleteOrder")
  @ResponseBody
  public String delete(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    try {
      Order Order = new Order(id);
      OrderDao.delete(Order);
    }
    catch (Exception ex) {
      return "Error deleting the Order: " + ex.toString();
    }
    return "Order succesfully deleted!";
  }
 
  
  /**
   * /update  --> Update the email and the name for the Order in the database 
   * having the passed id.
   * 
   * @param id The id for the Order to update.
   * @param clientId Order's new clientId
   * @param shopId Order's new shopId
   * @param userId Order's new userId
   * @param longitude Order's  new longitude
   * @param latitude Order's new  latitude
   * @param status Order's new status
   * @return A string describing if the Order is succesfully updated or not.
   */
  @RequestMapping("/updateOrder")
  @ResponseBody
  public String updateOrder(long id,long clientId, long shopId,long userId,double longitude,double latitude,short status,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
	  try {
      Order Order = OrderDao.findOne(id);
      Order.setClient(clientId);
      Order.setShop(shopId);
      Order.setUser(userId);
      Order.setLongitude(longitude);
      Order.setLatitude(latitude);
      Order.setStatus(status);
      OrderDao.save(Order);
    }
    catch (Exception ex) {
      return "Error updating the Order: " + ex.toString();
    }
    return "Order succesfully updated!";
  }
  
  /**
   * /setOrderStatus  --> Update the email and the name for the Order in the database 
   * having the passed id.
   * @param id The id for the Order to update.
   * @param status Order's new status
   * @return A string describing if the Order is succesfully updated or not.
   */
  @RequestMapping("/setOrderStatus")
  @ResponseBody
  public String updateOrder(long id,short status,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
		Client client=null;
		long clientId=-1;
	  try {
      Order Order = OrderDao.findOne(id);
      Order.setStatus(status);
      OrderDao.save(Order);
     clientId=Order.getClient();
      client = ClientDao.findOne(clientId);
      if(status==-1)
      {
    	  System.out.println("start to send message to"+client.getPhone());
    	  Inform.send(client.getPhone());  	  
      }
    }
    catch (Exception ex) {
      return "Error updating the Order: " +ex.toString();
    }
    return "Order succesfully updated and send message";
  }
  
   
  /**
   * /getOneOrder  --> get one Order having the passed id.
   * 
   * @param id The id of the Order to get
   * @return the order
   */
  @RequestMapping("/getOneOrder")
  @ResponseBody
  public Order getOneOrder(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
	  Order order =null;
    try {    
      order=OrderDao.findOne(id);
    }
    catch (Exception ex) {
    }
	return order;
  }
  
  /**
   * /getOrderByUser  --> get one Order having the passed id.
   * 
   * @param id The id of the user to get
   * @return the order
   */
  @RequestMapping("/getOrderByUser")
  @ResponseBody
  public Iterable<Order>getOrderByUser(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		Iterable<Order>Order=null;
    try {    
    	 Order=OrderDao.findByUserId(id);
    }
    catch (Exception ex) {
    }
	return Order;
  }
  
  /**
   * /getOrderByShop  --> get one Order having the passed id.
   * 
   * @param id The id of the shop to get
   * @return the order
   */
  @RequestMapping("/getOrderByShop")
  @ResponseBody
  public Iterable<Order>getOrderByShop(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		Iterable<Order>Order=null;
    try {    
    	Order=OrderDao.findByShopId(id);
    }
    catch (Exception ex) {
    }
	return Order;
  }
  
  /**
   * /getOrderByClient  --> get one Order having the passed id.
   * 
   * @param id The id of the Client to get
   * @return the order
   */
  @RequestMapping("/getOrderByClient")
  @ResponseBody
  public Iterable<Order>getOrderByClient(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		Iterable<Order>Order=null;
    try {    
    	Order=OrderDao.findByClientId(id);
    }
    catch (Exception ex) {
    }
	return Order;
  }
  
  /**
   * /getNewOrderByUser  --> get one Order having the passed id.
   * 
   * @param id The id of the user to get
   * @return the order
   */
  @RequestMapping("/getNewOrderByUser")
  @ResponseBody
  public Iterable<Order>getNewOrderByUser(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		Iterable<Order>Order=null;
		short status=0;
    try {    
    	Order=OrderDao.findByUserIdAndStatus(id,status);
    }
    catch (Exception ex) {
    }
	return Order;
  }
  
  /**
   * /getNewOrderByShop  --> get one Order having the passed id.
   * 
   * @param id The id of the shop to get
   * @return the order
   */
  @RequestMapping("/getNewOrderByShop")
  @ResponseBody
  public Iterable<Order>getNewOrderByShop(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		short status=0;
		Iterable<Order>Order=null;
    try {    
    	Order=OrderDao.findByShopIdAndStatus(id,status);
    }
    catch (Exception ex) {
    }
	return Order;
  }
  
  /**
   * /getNewOrderByClient  --> get one Order having the passed id.
   * 
   * @param id The id of the Client to get
   * @return the order
   */
  @RequestMapping("/getNewOrderByClient")
  @ResponseBody
  public Iterable<Order>getNewOrderByClient(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		Iterable<Order>Order=null;
		short status=0;
    try {    
    	Order=OrderDao.findByClientIdAndStatus(id,status);
    }
    catch (Exception ex) {
    }
	return Order;
  }
  
  
  /**
   * /getAllOrder  --> get all the Order having the passed id.
   * 
   * @return the order
   */
  @RequestMapping("/getAllNewOrder")
  @ResponseBody
  public Iterable<Order> getAllNewOrder(HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
	  Iterable<Order> order = null;
	  short status=0;
    try {    
      order=OrderDao.findByStatus(status);
    }
    catch (Exception ex) {
    }
	return order;
  }
  
  /**
   * /getAllOrder  --> get all the Order having the passed id.
   * 
   * @return the order
   */
  @RequestMapping("/getAllOrder")
  @ResponseBody
  public Iterable<Order> getAllOrder(HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
	  Iterable<Order> order = null;
	  User user=null;
    try {    
    	user=UserDao.findByName(session.getAttribute("name").toString());
    	if(user.getStatus()==0)
    	{
    		order=OrderDao.findAll();
    	}
    	else if(user.getStatus()==2)
    	{
    		order=OrderDao.findByUserId(user.getId());
    	}
    	else if(user.getStatus()==1)
    	{
    		order=OrderDao.findByClientId(user.getId());
    	}
    	else
    	{
    		order=null;
    	}
    }
    catch (Exception ex) {
    }
	return order;
  }
  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  @Autowired
  private OrderDao OrderDao;
  @Autowired
  private ClientDao ClientDao;
  @Autowired
  private UserDao UserDao;
  
} // class OrderController

