package netgloo.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

/**
 * A DAO for the entity Comment is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 * 
 * @author netgloo
 */
@Transactional
public interface CommentDao extends CrudRepository<Comment, Long> {
	
	  /**
		 * Return the Comment having the passed id or null if no Comment is found.
		 * 
		 * @param id the Comment id.
		 */
	    public Comment findById(long id);
	    
	    public Iterable<Comment> findByShopId(long shopId);
} // class OrderDao

