package io.pivotal.microservices.order;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Persistent account entity with JPA markup. Accounts are stored in an H2
 * relational database.
 * 
 */
@Entity
@Table(name = "T_MYORDER")
public class MyOrder implements Serializable{
	private static final long serialVersionUID = 1L;

	public static Long nextId = 0L;
	
	@Id
	private Long id;
	
	@Column(name = "userid")
	private String usrId;
	
	@Column(name = "productname")
	private String name;
	
	private BigDecimal price;
	
	private String payStatus;//"unpaid" "paid" "processing"
	
	/**
	 * This is a very simple, and non-scalable solution to generating unique
	 * ids. Not recommended for a real application. Consider using the
	 * <tt>@GeneratedValue</tt> annotation and a sequence to generate ids.
	 * 
	 * @return The next available id.
	 */
	protected static Long getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}

	/**
	 * Default constructor for JPA only.
	 */
	protected MyOrder() {//?
		price = BigDecimal.ZERO;
		this.payStatus = "unpaid";
	}

	public MyOrder(String name, BigDecimal price) {
		id = getNextId();
		this.name = name;
		this.usrId = null;
		this.price = price;
		this.payStatus = "unpaid";
	}
	
	
	public MyOrder(String usrId, String name, BigDecimal price) {
		id = getNextId();
		this.name = name;
		this.usrId = usrId;
		this.price = price;
		this.payStatus = "unpaid";
	}
	
	
	@Override
	public String toString() {
		return id + " contians "+ name + ", price:" + price + ", status:" + payStatus;
	}


	
	

	public Long getId() {
		return id;
	}
	/**
	 * Set JPA id - for testing and JPA only. Not intended for normal use.
	 * 
	 * @param id
	 *            The new id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPirce(BigDecimal amount) {
		this.price = amount;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	
	
	
}




