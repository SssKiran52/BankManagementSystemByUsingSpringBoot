package com.bank.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.entity.StatementInfo;
import com.bank.repository.StatementRepository;

@Component
public class StatementDaoImpl implements StatementDao{

	@Autowired
	StatementRepository statementRepository;

	@Override
	public StatementInfo savedebitamount(StatementInfo statementInfo) {
		StatementInfo save = statementRepository.save(statementInfo);
		return save;
	}

	@Override
	public StatementInfo getdetailsbyid(int userid) {
		StatementInfo findByUserid = statementRepository.findByUserid(userid);
		return findByUserid;
	}

	@Override
	public StatementInfo savecreditamount(StatementInfo statementInfo) {
		StatementInfo save = statementRepository.save(statementInfo);
		return save;
	}

//	@Override
//	public List<StatementInfo> getuserdetailsbyid(int userid) {
//		List<StatementInfo> getByUserid = statementRepository.getByUserid(userid);
//		return getByUserid;
//	}

	@Override
	public List<StatementInfo> getbankstatement(LocalDate fromdate, LocalDate todate) {
		List<StatementInfo> findByTransactiondateBetween = statementRepository.findByTransactiondateBetween(fromdate, todate);
		return findByTransactiondateBetween;
	}

	
	
	
}
