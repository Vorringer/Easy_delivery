package netgloo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import netgloo.models.Map;
import netgloo.models.MapDao;

/**
 * A class to test interactions with the MySQL database using the MapDao class.
 *
 * @author netgloo
 */
@Controller
public class MapController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
 

/**
   * /create  --> Create a new Map and save it in the database.
   * 
   * @param clientId Map's clientId
   * @param shopId Map's shopId
   * @param userId Map's userId
   * @param longitude Map's longitude
   * @param latitude Map's latitude
   * @param status Map's status
   * @return A string describing if the Map is succesfully created or not.
   */
  @RequestMapping("/createMap")
  @ResponseBody
  public String create(long clientId, long shopId,long userId,double longitude,double latitude,short status,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
	  Map Map = null;
    try {
      Map = new Map(clientId, shopId,userId,longitude,latitude,status);
      MapDao.save(Map);
    }
    catch (Exception ex) {
      return "Error creating the Map: " + ex.toString();
    }
    return "Map succesfully created! (id = " + Map.getId() + ")";
  }
  
  /**
   * /delete  --> Delete the Map having the passed id.
   * 
   * @param id The id of the Map to delete
   * @return A string describing if the Map is succesfully deleted or not.
   */
  @RequestMapping("/deleteMap")
  @ResponseBody
  public String delete(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
    try {
      Map Map = new Map(id);
      MapDao.delete(Map);
    }
    catch (Exception ex) {
      return "Error deleting the Map: " + ex.toString();
    }
    return "Map succesfully deleted!";
  }
 
  
  /**
   * /update  --> Update the email and the name for the Map in the database 
   * having the passed id.
   * 
   * @param id The id for the Map to update.
   * @param clientId Map's new clientId
   * @param shopId Map's new shopId
   * @param userId Map's new userId
   * @param longitude Map's  new longitude
   * @param latitude Map's new  latitude
   * @param status Map's new status
   * @return A string describing if the Map is succesfully updated or not.
   */
  @RequestMapping("/updateMap")
  @ResponseBody
  public String updateMap(long id,long clientId, long shopId,long userId,double longitude,double latitude,short status,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
	  try {
      Map Map = MapDao.findOne(id);
      Map.setClient(clientId);
      Map.setShop(shopId);
      Map.setUser(userId);
      Map.setLongitude(longitude);
      Map.setLatitude(latitude);
      Map.setStatus(status);
      MapDao.save(Map);
    }
    catch (Exception ex) {
      return "Error updating the Map: " + ex.toString();
    }
    return "Map succesfully updated!";
  }
  
   
  /**
   * /getOneMap  --> get one Map having the passed id.
   * 
   * @param id The id of the Map to get
   * @return the Map
   */
  @RequestMapping("/getOneMap")
  @ResponseBody
  public Map getOneMap(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
	  Map Map =null;
    try {    
      Map=MapDao.findOne(id);
    }
    catch (Exception ex) {
    }
	return Map;
  }
  
  /**
   * /getMapByUser  --> get one Map having the passed id.
   * 
   * @param id The id of the user to get
   * @return the Map
   */
  @RequestMapping("/getMapByUser")
  @ResponseBody
  public Iterable<Map>getMapByUser(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		Iterable<Map>Map=null;
    try {    
    	 Map=MapDao.findByUserId(id);
    }
    catch (Exception ex) {
    }
	return Map;
  }
  
  /**
   * /getMapByShop  --> get one Map having the passed id.
   * 
   * @param id The id of the shop to get
   * @return the Map
   */
  @RequestMapping("/getMapByShop")
  @ResponseBody
  public Iterable<Map>getMapByShop(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		Iterable<Map>Map=null;
    try {    
    	Map=MapDao.findByShopId(id);
    }
    catch (Exception ex) {
    }
	return Map;
  }
  
  /**
   * /getMapByClient  --> get one Map having the passed id.
   * 
   * @param id The id of the Client to get
   * @return the Map
   */
  @RequestMapping("/getMapByClient")
  @ResponseBody
  public Iterable<Map>getMapByClient(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		Iterable<Map>Map=null;
    try {    
    	Map=MapDao.findByClientId(id);
    }
    catch (Exception ex) {
    }
	return Map;
  }
  
  /**
   * /getNewMapByUser  --> get one Map having the passed id.
   * 
   * @param id The id of the user to get
   * @return the Map
   */
  @RequestMapping("/getNewMapByUser")
  @ResponseBody
  public Iterable<Map>getNewMapByUser(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		Iterable<Map>Map=null;
		short status=0;
    try {    
    	Map=MapDao.findByUserIdAndStatus(id,status);
    }
    catch (Exception ex) {
    }
	return Map;
  }
  
  /**
   * /getNewMapByShop  --> get one Map having the passed id.
   * 
   * @param id The id of the shop to get
   * @return the Map
   */
  @RequestMapping("/getNewMapByShop")
  @ResponseBody
  public Iterable<Map>getNewMapByShop(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		short status=0;
		Iterable<Map>Map=null;
    try {    
    	Map=MapDao.findByShopIdAndStatus(id,status);
    }
    catch (Exception ex) {
    }
	return Map;
  }
  
  /**
   * /getNewMapByClient  --> get one Map having the passed id.
   * 
   * @param id The id of the Client to get
   * @return the Map
   */
  @RequestMapping("/getNewMapByClient")
  @ResponseBody
  public Iterable<Map>getNewMapByClient(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
		Iterable<Map>Map=null;
		short status=0;
    try {    
    	Map=MapDao.findByClientIdAndStatus(id,status);
    }
    catch (Exception ex) {
    }
	return Map;
  }
  
  
  /**
   * /getAllMap  --> get all the Map having the passed id.
   * 
   * @return the Map
   */
  @RequestMapping("/getAllNewMap")
  @ResponseBody
  public Iterable<Map> getAllNewMap(HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
	  Iterable<Map> Map = null;
	  short status=0;
    try {    
      Map=MapDao.findByStatus(status);
    }
    catch (Exception ex) {
    }
	return Map;
  }
  
  /**
   * /getAllMap  --> get all the Map having the passed id.
   * 
   * @return the Map
   */
  @RequestMapping("/getAllMap")
  @ResponseBody
  public Iterable<Map> getAllMap(HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
	  Iterable<Map> Map = null;
    try {    
      Map=MapDao.findAll();
    }
    catch (Exception ex) {
    }
	return Map;
  }
  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  @Autowired
  private MapDao MapDao;
  
} // class MapController


