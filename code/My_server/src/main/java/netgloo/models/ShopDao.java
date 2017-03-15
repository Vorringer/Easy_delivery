package netgloo.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

/**
 * A DAO for the entity Shop is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 * 
 * @author netgloo
 */
@Transactional
public interface ShopDao extends CrudRepository<Shop, Long> {
	
	  /**
		 * Return the shop having the passed id or null if no shop is found.
		 * 
		 * @param id the shop id.
		 */
	    public Shop findById(long id);
} // class OrderDao

