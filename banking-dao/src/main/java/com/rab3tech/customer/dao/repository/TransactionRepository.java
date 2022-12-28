package com.rab3tech.customer.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rab3tech.dao.entity.Transaction;
@Repository
public interface TransactionRepository extends JpaRepository <Transaction, Integer> {
	List<Transaction>findByCustomerIdOrPayeeAccountNo(String custId,String accNo);

}
