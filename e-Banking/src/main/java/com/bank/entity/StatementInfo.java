package com.bank.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
public class StatementInfo {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Transaction_Id", unique = true, nullable = false)
	private int transactionid;
	@Column(name = "Transaction_Date", nullable = false)
	private LocalDate transactiondate;
	@Column(name = "Transaction_Time", nullable = false)
	private String transactiontime;
	@Column(name = "Transaction_Credit")
	private double transactioncredit;
	@Column(name = "Transaction_Debit")
	private double transactiondebit;
	@Column(name = "User_Id",nullable = false)
	private int userid;
	@Column(name = "Receiver_Account_Number",nullable = false)
	private int transferaccountnumber;
	@Column(name = "Bank_Balance", nullable = false)
	private long bankbalance;
	
}
