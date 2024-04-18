package com.bank.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bank.entity.StatementInfo;

public interface StatementRepository extends JpaRepository<StatementInfo, Integer>{

	StatementInfo findByUserid(int userid);
	
	@Query("select statementinfo from StatementInfo statementinfo where statementinfo.transactiondate between ?1 and ?2")
	List<StatementInfo> findByTransactiondateBetween(LocalDate fromdate, LocalDate todate);
	
//	List<StatementInfo> getByUserid(int userid);
}
