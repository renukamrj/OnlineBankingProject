package com.rab3tech.customer.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.CustomerRepository;
import com.rab3tech.customer.dao.repository.TransactionRepository;
import com.rab3tech.customer.service.TransactionService;
import com.rab3tech.dao.entity.Customer;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.PayeeInfo;
import com.rab3tech.dao.entity.Transaction;
import com.rab3tech.vo.PayeeInfoVO;
import com.rab3tech.vo.TransactionVO;
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
	private static final String PayeeAccountNo = null;
	@Autowired
	CustomerAccountInfoRepository accountRepo;
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public String fundTransfer(TransactionVO vo) {
		//senderAccount validation
		Optional<CustomerAccountInfo> senderAccount = accountRepo.findByCustomerId(vo.getCustomerId());
		if (!senderAccount.isPresent()) {
			return "Payee cannot be added as sender is not having valid account";
		} else {
			String accType = senderAccount.get().getAccountType();
			if (!accType.equals("SAVING")) {
				return "Payee cannot be added as sender is not having valid SAVING account";
			}
		}
		
		CustomerAccountInfo sender=senderAccount.get();
		//fundbalance validation
		float senderBalance=sender.getAvBalance();
		if(senderBalance<vo.getAmount()) {
			return"AMount cannot be transffered as sernder is nao thaving sufficient funds";
			
		}
		//receiverAccount validation
		Optional<CustomerAccountInfo> receiverAccount = accountRepo.findByAccountNumber(vo.getPayeeAccountNo());
		if (!receiverAccount.isPresent()) {
			return "Payee cannot be added as receiver is not having valid account";
		} else {
			if (!receiverAccount.get().getAccountType().equals("SAVING")) {
				return "Payee cannot be added as receiver is not having valid SAVING account";
			}
		}
		CustomerAccountInfo receiver=receiverAccount.get();
		//deduct sender's amount
		sender.setAvBalance(senderBalance-vo.getAmount());
		sender.setTavBalance(senderBalance-vo.getAmount());
		sender.setStatusAsOf(new Date());
	//Add receiver's amount
		receiver.setAvBalance(receiver.getAvBalance() + vo.getAmount());
		receiver.setTavBalance(receiver.getAvBalance() + vo.getAmount());
		receiver.setStatusAsOf(new Date());
		
		Transaction transaction = new Transaction();
		BeanUtils.copyProperties(vo, transaction);
		transaction.setDoe(new Timestamp(new Date().getTime()));
		transactionRepository.save(transaction);
		return "Amount has been transferred sucessfully";
	
	
	}

	@Override
	public List<TransactionVO> findAllAccount(String customerId ) {
		//check if customer has a valid saving account
		Optional<CustomerAccountInfo> userAccount=accountRepo.findByCustomerId(customerId);
			List<TransactionVO> voList=new ArrayList<TransactionVO>();
			if(userAccount.isPresent()) {
				CustomerAccountInfo customerInfo = userAccount.get();
				float balance=customerInfo.getTavBalance();
				Optional<Customer> customer=customerRepository.findByEmail(customerId);			
				List <Transaction> transactionEntity = transactionRepository.findByCustomerIdOrPayeeAccountNo(customerInfo.getCustomerId(), customerInfo.getAccountNumber());
				for(Transaction transaction: transactionEntity) {
					TransactionVO vo = new TransactionVO();
					vo.setBalance(balance);
					BeanUtils.copyProperties(transaction, vo);
					if(transaction.getCustomerId().equals(customerId)) {
						vo.setTransactionType("Debit");
						vo.setName(customer.get().getName());
					}else {
						vo.setTransactionType("Credit");
						Optional<Customer> senderInfo=customerRepository.findByEmail(transaction.getCustomerId());	
						vo.setName(senderInfo.get().getName());
					}
					voList.add(vo);
				}
			
			}		
			return voList;
			
		}
}
				
				
				
			