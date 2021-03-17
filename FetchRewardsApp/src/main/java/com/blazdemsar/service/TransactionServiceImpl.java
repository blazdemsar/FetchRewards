package com.blazdemsar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blazdemsar.domain.Transaction;
import com.blazdemsar.repo.TransactionRepository;

/*
 * An implementation of TransactionService that provides method bodies for the CRUD operations.
 * It autowires the TransactionRepository which then further handles the CRUD operations for
 * database manipulation.
 * 
 */
@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	TransactionRepository transactionRepository;
	
	/**
	 * Function save(Transaction t) saves a transaction "t" into the database.
	 * 
	 * @return a Transaction after it has been successfully saved into the database.
	 */
	@Override
	public Transaction save(Transaction t) {
		return transactionRepository.save(t);
	}
	
	/**
	 * Function findById(int id) finds if a transaction with id "id" exists in the database.
	 * 
	 * @return a Transaction associated with the paramater id if it exists, else return null.
	 */
	@Override
	public Transaction findById(int id) {
		
		Optional<Transaction> optTrans = transactionRepository.findById(id);
		
		if (optTrans.isPresent()) {
			return optTrans.get();
		}
		else {
			return null;
		}
		
	}
	
	/**
	 * Function updateById(int id) updates an transaction with id "id" if it exists in the database.
	 * 
	 * @return an updated Transaction associated with the paramater id if it exists, else return null.
	 */
	@Override
	public Transaction updateById(int id) {
		
		Transaction t = findById(id);
		
		if (t != null) {
			
			/*
			 * TODO if update functionality is needed in the future, provide the content HERE.
			 */
			return save(t);
		}
		else {
			return t;
		}
		
	}
	
	/**
	 * Function findAll() finds all transactions in the database.
	 * 
	 * @return a List of Transactions that exist in the database.
	 */
	@Override
	public List<Transaction> findAll() {
		return transactionRepository.findAll();
	}
	
	/**
	 * Function deleteById(int id) attempts to delete a transaction with id "id" if it exists in the database.
	 * 
	 */
	@Override
	public void deleteById(int id) {
		
		if (findById(id) != null) {
			
			transactionRepository.deleteById(id);
		}
	}

}
