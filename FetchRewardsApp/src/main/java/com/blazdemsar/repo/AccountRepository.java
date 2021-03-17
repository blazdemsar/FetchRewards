package com.blazdemsar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blazdemsar.domain.Account;

/*
 * A simple extension of JpaRepository to handle the Account data storing
 * into the database.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
