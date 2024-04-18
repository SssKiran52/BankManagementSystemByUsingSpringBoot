package com.bank.dao;

import java.time.LocalDate;
import java.util.List;

import com.bank.entity.StatementInfo;

public interface StatementDao {

	StatementInfo savedebitamount(StatementInfo statementInfo);
	
	StatementInfo getdetailsbyid(int userid);
	
	StatementInfo savecreditamount(StatementInfo statementInfo);
	
//	List<StatementInfo> getuserdetailsbyid(int userid);
	
	List<StatementInfo> getbankstatement(LocalDate fromdate, LocalDate todate);
	
	
}
