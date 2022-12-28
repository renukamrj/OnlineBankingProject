package com.rab3tech.customer.service;

import java.util.List;

import com.rab3tech.vo.PayeeInfoVO;
import com.rab3tech.vo.TransactionVO;

public interface TransactionService {
	public String fundTransfer(TransactionVO vo);
	public List<TransactionVO>findAllAccount(String customerId);

}
