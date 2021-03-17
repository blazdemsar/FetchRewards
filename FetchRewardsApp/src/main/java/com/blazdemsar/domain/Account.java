package com.blazdemsar.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int accountId;
	
	@JsonBackReference
	@OneToMany(mappedBy="account")
	private List<Transaction> accountTransactions;
	
	public Account() {
		super();
	}

	public Account(List<Transaction> accountTransactions) {
		super();
		this.accountTransactions = accountTransactions;
	}

	public Account(int accountId, List<Transaction> accountTransactions) {
		super();
		this.accountId = accountId;
		this.accountTransactions = accountTransactions;
	}

	public int getAccountId() {
		
		return accountId;
	}

	public void setAccountId(int accountId) {
		
		this.accountId = accountId;
	}

	public List<Transaction> getAccountTransactions() {
		
		return accountTransactions;
	}

	public void setAccountTransactions(List<Transaction> accountTransactions) {
		
		this.accountTransactions = accountTransactions;
	}
	
	/**
	 * Function getBalance() used by the RestController to fetch the account balance grouped by
	 * "payer" in each transaction as key and accumulated points as value in the Map.
	 *
	 * @return getBalance() returns a Map<String, Integer> where:
	 * 		
	 * 		key 	-> references the payer
	 * 		value 	-> references the total points per payer
	 * 
	 */
	public Map<String, Integer> getBalance() {
		
		Map<String, Integer> result = new HashMap<>();
		
		for (Transaction t : accountTransactions) {
			
			String payer = t.getPayer();
			
			if (result.containsKey(payer)) {
				
				int points = t.getPoints();
				int currPoints = result.get(payer);
				int total = points+currPoints;
				
				result.replace(payer, total);
			}
			else {
				
				result.put(payer, t.getPoints());
			}
		}
		
		return result;
	}
	
	/**
	 * Function spendPoints(int pointsToSpend) returns a mapping of Date based points that are
	 * supposed to be spent first.
	 * 
	 * @param pointsToSpend -> number of points that are to be spent to evaluate if available
	 * 		  balance is sufficient for the request
	 * 
	 * @return spendPoints() function returns a Map<LocalDate, Map<String, Integer>> where:
	 * 		
	 * 		key 	-> references LocalDate to group transactions based on the date
	 * 		value 	-> references a Map<String, Integer> of points per payer where:
	 * 					
	 * 					key		-> references a payer
	 * 					value	-> references points per payer on a given date
	 * 
	 */
	public Map<LocalDate, Map<String, Integer>> spendPoints(int pointsToSpend) {
		
		Map<String, Integer> balance = getBalance();
		Map<LocalDate, Map<String, Integer>> oldestPointsAvailable = new HashMap<>();
		
		int totalBalance = 0;
		
		for (String key : balance.keySet()) {
			
			totalBalance += balance.get(key);
		}
		
		System.out.println("Available balance: " + totalBalance);
		
		if (totalBalance < pointsToSpend) {
			
			return null;
		}
		else {
			
			for (Transaction t : accountTransactions) {
				
				LocalDate date = t.getTimestamp().toLocalDate();
				String payer = t.getPayer();
				
				if (oldestPointsAvailable.containsKey(date)) {
					
					Map<String, Integer> datesAndPoints = oldestPointsAvailable.get(date);
					
					if (datesAndPoints.containsKey(payer)) {
						
						int currPts = datesAndPoints.get(payer);
						datesAndPoints.replace(payer, t.getPoints() + currPts );
					}
					else {
						
						datesAndPoints.put(payer, t.getPoints());
					}
					
					oldestPointsAvailable.replace(date, datesAndPoints);
				}
				else {
					
					Map<String, Integer> datesAndPoints = new HashMap<>();
					datesAndPoints.put(payer, t.getPoints());
					
					oldestPointsAvailable.put(date, datesAndPoints);
				}
			}
						
			return oldestPointsAvailable;
			
		}
		
	}
}
