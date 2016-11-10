package io.pivotal.microservices.order;




import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import io.pivotal.microservices.accounts.Account;





public interface MyOrderRepository extends Repository<MyOrder, Long>{
	
	@Query("SELECT count(*) from MyOrder")
	public int countOrders();
	
	public MyOrder findByid(Long orderId);
	
	public List<MyOrder> findByusrId(String userId);
	
	public List<MyOrder> findAll();
	
	public List<Account> findByNameContainingIgnoreCase(String partialName);

}
