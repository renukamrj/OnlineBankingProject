package com.rab3tech.customer.service;



import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.customer.dao.repository.CustomerAccountEnquiryRepository;
import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.CustomerQuestionAnswer;
import com.rab3tech.dao.entity.CustomerSaving;
import com.rab3tech.vo.CustomerAccountInfoVO;

@Service
@Transactional
public class CustomerAccountInfoServiceImpl implements CustomerAccountInfoService {
	@Autowired
	CustomerAccountInfoRepository accountRepository;
	@Autowired
	CustomerAccountEnquiryRepository customerAccountEnquiryRepository;

	@Override
	public CustomerAccountInfoVO createAccount(String logonId) {
		//check account is present or not
		Optional<CustomerAccountInfo> customerAccount= accountRepository.findByCustomerId(logonId);
		CustomerAccountInfo account = new CustomerAccountInfo();
		CustomerAccountInfoVO vo = new CustomerAccountInfoVO();
		if(!customerAccount.isPresent()) {
		CustomerAccountInfo entity=new CustomerAccountInfo();//blank
		//set whole entity
		entity.setCustomerId(logonId);
		entity.setStatusAsOf(new Date());
		entity.setAvBalance(1000);
		entity.setCurrency("USD");
		//call customer_saving_enquiry tbl and get below information
		CustomerSaving customerSaving = customerAccountEnquiryRepository.findByEmail(logonId).get();
		entity.setAccountNumber(customerSaving.getAppref());
		entity.setBranch(customerSaving.getLocation());
		entity.setAccountType(customerSaving.getAccType().getName());
		 account = accountRepository.save(entity);
		vo.setMessage("Congrats your new account has been created");
	}else {
		account = customerAccount.get();
		vo.setMessage("You already have existing account below are the details");
	}
		BeanUtils.copyProperties(account, vo);
		return vo;
	}

}
