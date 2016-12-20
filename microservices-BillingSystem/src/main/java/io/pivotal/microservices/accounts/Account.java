package io.pivotal.microservices.accounts;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_ACCOUNT")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Long nextId = 0L;

	@Id
	protected Long id;

	protected String idnumber;

	@Column(name = "name")
	protected String owner;

	protected BigDecimal balance;
	
	protected static Long getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}

	protected Account() {
		balance = BigDecimal.ZERO;
	}

	public Account(String number, String owner) {
		id = getNextId();
		this.idnumber = number;
		this.owner = owner;
		balance = BigDecimal.ZERO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void setNextId(Long nextId) {
		Account.nextId = nextId;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalance() {
		return balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	public void withdraw(BigDecimal amount) {
		balance.subtract(amount);
	}

	public void deposit(BigDecimal amount) {
		balance.add(amount);
	}

	@Override
	public String toString() {
		return idnumber + " [" + owner + "]: $" + balance;
	}

}
