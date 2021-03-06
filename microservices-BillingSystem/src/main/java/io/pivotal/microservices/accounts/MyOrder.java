package io.pivotal.microservices.accounts;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("MyOrder")
public class MyOrder implements Serializable{
	private static final long serialVersionUID = 1L;

	public static Long nextId = 0L;
	
	
	private Long id;
	
	private String usrId;
	
	private String name;
	
	private BigDecimal price;
	
	private String payStatus;//"unpaid" "paid" "processing"
	
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




