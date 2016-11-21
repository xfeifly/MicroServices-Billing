package io.pivotal.microservices.order;




import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;







public interface MyOrderRepository extends Repository<MyOrder, Long>{
	
	@Query("SELECT count(*) from MyOrder")
	public int countOrders();
	
	public MyOrder findByid(Long orderId);
	
	public List<MyOrder> findByUsrId(String userId);
	
	public List<MyOrder> findAll();
	
	@Modifying
	@Query("update MyOrder myor set myor.usrId = ?1 where myor.usrId = '100000000'")
	public int setFixedUserIdFor(String usrId);
	
	@Modifying
	@Transactional
	//should use the origin name in the class definition rather than the column name
    @Query("update MyOrder myor set myor.payStatus = 'Paid' WHERE myor.id = ?1")
    public void updateStatusToPaid(Long orderId);

	
	@Modifying
	@Transactional
    @Query("UPDATE MyOrder myor set myor.payStatus = 'Unsucc' WHERE myor.id = ?1")
    public void updateStatusToUnsuccessful(Long orderId);
	
	
}
