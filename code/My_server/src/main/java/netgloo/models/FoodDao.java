package netgloo.models;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

/**
 * A DAO for the entity Food is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 * 
 * @author netgloo
 * 
 */
@Transactional
public interface FoodDao extends CrudRepository<Food, Long> {

  /**
   * Return the Food having the passed shopId or null if no Food is found.
   * 
   * @param shopId the Food shopId.
   */
   public List<Food> findByOrderId(long orderId);
	


} // class FoodDao




