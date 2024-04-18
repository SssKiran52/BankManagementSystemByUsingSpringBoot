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
public class BankLoanInfo {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Application_Id", nullable = false)
	private int applicationid;
	@Column(name = "Requested_Amount", nullable = false)
	private Long requestedamount;
	@Column(name = "Application_Date", nullable = false)
	private LocalDate applicationdate;
	@Column(name = "Application_Time", nullable = false)
	private String applicationtime;
	@Column(name = "User_Id", nullable = false)
	private int userid;
	@Column(name = "User_Name", nullable = false)
	private String userusername;
	@Column(name = "User_Mobile_Number", nullable = false)
	private String usermobilenumber;
	@Column(name = "Bank_Account_Number", nullable = false)
	private int bankaccountnumber;
	
}
