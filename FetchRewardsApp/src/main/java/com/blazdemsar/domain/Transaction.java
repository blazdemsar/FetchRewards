package com.blazdemsar.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int transactionId;
	
	@ManyToOne
	@JoinColumn(name="accountId")
	private Account account;
	
	private String payer;
	private int points;
	
	@JsonFormat(pattern="yyyy-MM-ddTHH:mm:ssZ")
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private LocalDateTime timestamp;
	
	public Transaction() {
		super();
	}

	public Transaction(Account account, String payer, int points, LocalDateTime timestamp) {
		super();
		this.account = account;
		this.payer = payer;
		this.points = points;
		this.timestamp = timestamp;
	}

	public Transaction(int transactionId, Account account, String payer, int points, LocalDateTime timestamp) {
		super();
		this.transactionId = transactionId;
		this.account = account;
		this.payer = payer;
		this.points = points;
		this.timestamp = timestamp;
	}

	public int getTransactionId() {
		
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		
		this.transactionId = transactionId;
	}

	public String getPayer() {
		
		return payer;
	}

	public void setPayer(String payer) {
		
		this.payer = payer;
	}

	public int getPoints() {
		
		return points;
	}

	public void setPoints(int points) {
		
		this.points = points;
	}

	public LocalDateTime getTimestamp() {
		
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		
		this.timestamp = timestamp;
	}

	public Account getAccount() {
		
		return account;
	}

	public void setAccount(Account account) {
		
		this.account = account;
	}

	@Override
	public String toString() {
		
		return "Transaction [transactionId=" + transactionId + ", account=" + account + ", payer=" + payer + ", points="
				+ points + ", timestamp=" + timestamp + "]";
	}
	
}
