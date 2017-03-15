package netgloo.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

/**
 * A DAO for the entity Order is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 * 
 * @author netgloo
 */
@Transactional
public interface OrderDao extends CrudRepository<Order, Long> {
	/**
	 * Return the order having the passed shopId or null if no order is found.
	 * 
	 * @param shopId the order shopId.
	 */
	public Iterable<Order> findByShopId(long shopId);
	
	/**
	 * Return the order having the passed clientId or null if no order is found.
	 * 
	 * @param clientId the order clientId.
	 */
	public Iterable<Order> findByClientId(long clientId);
	
	/**
	 * Return the order having the passed status or null if no order is found.
	 * 
	 * @param status the order status.
	 */
	public Iterable<Order> findByStatus(short status);
	
	/**
	 * Return the order having the passed userId or null if no order is found.
	 * 
	 * @param userId the order userId.
	 * @param status the order status.
	 */
	public Iterable<Order> findByUserIdAndStatus(long userId,short status);
	
	
	/**
	 * Return the order having the passed shopId or null if no order is found.
	 * 
	 * @param shopId the order shopId.
	 * @param status the order status.
	 */
	public Iterable<Order> findByShopIdAndStatus(long shopId,short status);
	
	/**
	 * Return the order having the passed clientId or null if no order is found.
	 * 
	 * @param clientId the order clientId.
	 * @param status the order status.
	 */
	public Iterable<Order> findByClientIdAndStatus(long clientId,short status);
	
	
	/**
	 * Return the order having the passed userId or null if no order is found.
	 * 
	 * @param userId the order userId.
	 */
	public Iterable<Order> findByUserId(long userId);
} // class OrderDao

