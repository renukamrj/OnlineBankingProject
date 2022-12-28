package com.rab3tech.customer.service;

import java.util.List;
import java.util.Optional;

import com.rab3tech.dao.entity.PayeeInfo;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.PayeeInfoVO;

public interface PayeeInfoService {
	public String addPayee(PayeeInfoVO payeeVO);
	public List<PayeeInfoVO>findAllPayee(String customerId);
	

}
