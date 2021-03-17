package com.blazdemsar.service;

import java.util.List;

import com.blazdemsar.domain.Transaction;

/*
 * An interface for performing CRUD operations with Transaction objects to manipulate
 * the database.
 */
public interface TransactionService {
	
	public Transaction save(Transaction t);
	public Transaction findById(int id);
	public Transaction updateById(int id);
	public List<Transaction> findAll();
	public void deleteById(int id);
	
}
