package netgloo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import netgloo.models.Client;
import netgloo.models.ClientDao;
import netgloo.models.Shop;

/**
 * A class to test interactions with the MySQL database using the ClientDao class.
 *
 * @author netgloo
 */
@Controller
public class ClientController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  /**
   * /create  --> Create a new Client and save it in the database.
   * 
   * @param status Client's status
   * @param info Client's info
   * @return A string describing if the Client is succesfully created or not.
   */
  @RequestMapping("/createClient")
  @ResponseBody
  public String create(short status,String info,String phone,HttpServletRequest request) {
	HttpSession session=request.getSession();
	if(session.getAttribute("name")==null)return "Please login";
	

    Client Client = null;
    try {
      Client = new Client(status,info,phone);
      ClientDao.save(Client);
    }
    catch (Exception ex) {
      return "Error creating the Client: " + ex.toString();
    }
    return "Client succesfully created! (id = " + Client.getId() + ")";
  }
  
  /**
   * /delete  --> Delete the Client having the passed id.
   * 
   * @param id The id of the Client to delete
   * @return A string describing if the Client is succesfully deleted or not.
   */
  @RequestMapping("/deleteClient")
  @ResponseBody
  public String delete(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";
	
    try {
      Client Client = new Client(id);
      ClientDao.delete(Client);
    }
    catch (Exception ex) {
      return "Error deleting the Client: " + ex.toString();
    }
    return "Client succesfully deleted!";
  }
 
  
  /**
   * /update  --> Update the email and the name for the Client in the database 
   * having the passed id.
   * 
   * @param id The id for the Client to update.
   * @param status Client's new status
   * @param info Client's new info
   * @return A string describing if the Client is succesfully updated or not.
   */
  @RequestMapping("/updateClient")
  @ResponseBody
  public String updateClient(long id,short status,String info,HttpServletRequest request) {
	  
	HttpSession session=request.getSession();
	if(session.getAttribute("name")==null)return "Please login";
	
    try {
      Client Client = ClientDao.findOne(id);
      Client.setInfo(info);
      Client.setStatus(status);
      ClientDao.save(Client);
    }
    catch (Exception ex) {
      return "Error updating the Client: " + ex.toString();
    }
    return "Client succesfully updated!";
  }
  
   
  /**
   * /getOne  --> get the Client having the passed id.
   * 
   * @param id The id of the Client to get
   * @return the Client
   */
  @RequestMapping("/getOneClient")
  @ResponseBody
  public Client getOneClient(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;
	
	  Client Client =null;
    try {    
      Client=ClientDao.findOne(id);
    }
    catch (Exception ex) {
    }
	return Client;
  }
  
  /**
   * /get-by-id  --> Return the info for the Client having the passed id.
   * 
   * @param id The id to search in the database.
   * @return The Client name or a message error if the user is not found.
   */
  @RequestMapping("/getClientNameById")
  @ResponseBody
  public String getNameById(long id,HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return "Please login";

    String ClientName=null;
    try {
     Client client = ClientDao.findOne(id);
     ClientName=client.getInfo();
    }
    catch (Exception ex) {
      return "Client not found";
    }
    return ClientName;
    
    
  }
  
  
  /**
   * /getAll  --> get the Client having the passed id.
   * 
   * @return the Client
   */
  @RequestMapping("/getAllClient")
  @ResponseBody
  public Iterable<Client> getAllClient(HttpServletRequest request) {
	  HttpSession session=request.getSession();
		if(session.getAttribute("name")==null)return null;

	  Iterable<Client> Client = null;
    try {    
      Client=ClientDao.findAll();
    }
    catch (Exception ex) {
    }
	return Client;
  }
  
  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  @Autowired
  private ClientDao ClientDao;
  
} // class ClientController



