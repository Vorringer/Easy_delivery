package netgloo.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

/**
 * A DAO for the entity Item is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 * 
 * @author netgloo
 * 
 */
@Transactional
public interface ItemDao extends CrudRepository<Item, Long> {

  /**
   * Return the Item having the passed shopId or null if no Item is found.
   * 
   * @param shopId the Item shopId.
   */
   public Iterable<Item> findByShopId(long shopId);
	


} // class ItemDao



