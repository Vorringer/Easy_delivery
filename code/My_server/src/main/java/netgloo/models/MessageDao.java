package netgloo.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

/**
 * A DAO for the entity Message is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 * 
 * @author netgloo
 */
@Transactional
public interface MessageDao extends CrudRepository<Message, Long> {
	
	  /**
		 * Return the Message having the passed id or null if no Message is found.
		 * 
		 * @param id the Message id.
		 */
	    public Message findById(long id);
	    
	    public Iterable<Message> findBySender(long sender);
	    
	    public Iterable<Message> findByReceiver(long receiver);
	    
	    public Iterable<Message> findBySenderAndReceiver(long sender,long receiver);
} // class OrderDao

