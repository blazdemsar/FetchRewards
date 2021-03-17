package com.blazdemsar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blazdemsar.domain.Transaction;

/*
 * A simple extension of JpaRepository to handle the Transaction data storing
 * into the database.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
