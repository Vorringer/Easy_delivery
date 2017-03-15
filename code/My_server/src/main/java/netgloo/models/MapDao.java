package netgloo.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

/**
 * A DAO for the entity Map is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 * 
 * @author netgloo
 */
@Transactional
public interface MapDao extends CrudRepository<Map, Long> {
	/**
	 * Return the Map having the passed shopId or null if no Map is found.
	 * 
	 * @param shopId the Map shopId.
	 */
	public Iterable<Map> findByShopId(long shopId);
	
	/**
	 * Return the Map having the passed clientId or null if no Map is found.
	 * 
	 * @param clientId the Map clientId.
	 */
	public Iterable<Map> findByClientId(long clientId);
	
	/**
	 * Return the Map having the passed status or null if no Map is found.
	 * 
	 * @param status the Map status.
	 */
	public Iterable<Map> findByStatus(short status);
	
	/**
	 * Return the Map having the passed userId or null if no Map is found.
	 * 
	 * @param userId the Map userId.
	 * @param status the Map status.
	 */
	public Iterable<Map> findByUserIdAndStatus(long userId,short status);
	
	
	/**
	 * Return the Map having the passed shopId or null if no Map is found.
	 * 
	 * @param shopId the Map shopId.
	 * @param status the Map status.
	 */
	public Iterable<Map> findByShopIdAndStatus(long shopId,short status);
	
	/**
	 * Return the Map having the passed clientId or null if no Map is found.
	 * 
	 * @param clientId the Map clientId.
	 * @param status the Map status.
	 */
	public Iterable<Map> findByClientIdAndStatus(long clientId,short status);
	
	
	/**
	 * Return the Map having the passed userId or null if no Map is found.
	 * 
	 * @param userId the Map userId.
	 */
	public Iterable<Map> findByUserId(long userId);
} // class MapDao

