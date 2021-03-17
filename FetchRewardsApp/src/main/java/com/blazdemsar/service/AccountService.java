package com.blazdemsar.service;

import com.blazdemsar.domain.Account;

/*
 * An interface for performing CRUD operations with Account objects to manipulate
 * the database.
 */
public interface AccountService {
	
	public Account save(Account a);
	public Account findById(int id);
	public Account updateById(int id);
	public void deleteById(int id);
	
}
