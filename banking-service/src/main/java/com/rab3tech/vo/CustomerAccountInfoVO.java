package com.rab3tech.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class CustomerAccountInfoVO {
	private long id;
	private String customerId;
	private String accountNumber;
	private String currency;
	private String branch;
	private float tavBalance;
	private float avBalance;
	private Date StatusAsOf;
	private String accountType;
	private String message;

	@Column(length=20)
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	

	public float getTavBalance() {
		return tavBalance;
	}

	public void setTavBalance(float tavBalance) {
		this.tavBalance = tavBalance;
	}

	public float getAvBalance() {
		return avBalance;
	}

	public void setAvBalance(float avBalance) {
		this.avBalance = avBalance;
	}

	public Date getStatusAsOf() {
		return StatusAsOf;
	}

	public void setStatusAsOf(Date statusAsOf) {
		StatusAsOf = statusAsOf;
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	

}




