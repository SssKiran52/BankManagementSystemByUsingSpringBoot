package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.BankLoanInfo;

public interface BankLoanRepository extends JpaRepository<BankLoanInfo, Integer>{

}
