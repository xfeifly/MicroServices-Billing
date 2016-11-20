package io.pivotal.microservices.order;




import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;







public interface MyOrderRepository extends Repository<MyOrder, Long>{
	
	@Query("SELECT count(*) from MyOrder")
	public int countOrders();
	
	public MyOrder findByid(Long orderId);
	
	public List<MyOrder> findByUsrId(String userId);
	
	public List<MyOrder> findAll();
	
	@Modifying
	@Query("update MyOrder myor set myor.usrId = ?1 where myor.usrId = '100000000'")
	public int setFixedUserIdFor(String usrId);


}
