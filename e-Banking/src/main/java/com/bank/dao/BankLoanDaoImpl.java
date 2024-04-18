package com.bank.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.entity.BankLoanInfo;
import com.bank.repository.BankLoanRepository;

@Component
public class BankLoanDaoImpl implements BankLoanDao{

	@Autowired
	BankLoanRepository bankLoanRepository;
	
	
	@Override
	public BankLoanInfo savebankloanapplication(BankLoanInfo bankLoanInfo) {
		BankLoanInfo save = bankLoanRepository.save(bankLoanInfo);
		return save;
	}

}
