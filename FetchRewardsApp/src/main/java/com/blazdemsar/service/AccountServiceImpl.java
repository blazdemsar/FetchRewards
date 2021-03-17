package com.blazdemsar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blazdemsar.domain.Account;
import com.blazdemsar.repo.AccountRepository;

/*
 * An implementation of AccountService that provides method bodies for the CRUD operations.
 * It autowires the AccountRepository which then further handles the CRUD operations for
 * database manipulation.
 * 
 */
@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountRepository accountRepository;
	
	/**
	 * Function save(Account a) saves an account "a" into the database.
	 * 
	 * @return an Account after it has been successfully saved into the database.
	 */
	@Override
	public Account save(Account a) {
		return accountRepository.save(a);
	}
	
	/**
	 * Function findById(int id) finds if an account with id "id" exists in the database.
	 * 
	 * @return an Account associated with the paramater id if it exists, else return null.
	 */
	@Override
	public Account findById(int id) {
		
		Optional<Account> optAcc = accountRepository.findById(id);
		
		if (optAcc.isPresent()) {
			return optAcc.get();
		}
		else {
			return null;
		}
		
	}
	
	/**
	 * Function updateById(int id) updates an account with id "id" if it exists in the database.
	 * 
	 * @return an updated Account associated with the paramater id if it exists, else return null.
	 */
	@Override
	public Account updateById(int id) {
		
		Account a = findById(id);
		
		if (a != null) {
			
			/*
			 * TODO if update functionality is needed in the future, provide the content HERE.
			 */
			return save(a);
		}
		else {
			return a;
		}
		
	}
	
	/**
	 * Function deleteById(int id) attempts to delete an account with id "id" if it exists in the database.
	 * 
	 */
	@Override
	public void deleteById(int id) {
		
		if (findById(id) != null) {
			
			accountRepository.deleteById(id);
		}
	}

}
