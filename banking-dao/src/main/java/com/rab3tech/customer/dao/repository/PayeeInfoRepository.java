package com.rab3tech.customer.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.PayeeInfo;

public interface PayeeInfoRepository extends JpaRepository<PayeeInfo, Integer> {
	List<PayeeInfo> findByCustomerId(String customerId);

Optional<PayeeInfo> findByCustomerIdAndPayeeAccountNo(String custId, String accNo);

}
