package com.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "User_Id", unique = true, nullable = false)
	private int userid;
	@Column(name = "User_First_Name", nullable = false)
	private String userfirstname;
	@Column(name = "User_Last_Name", nullable = false)
	private String userlastname;
	@Column(name = "User_Date_Of_Birth", nullable = false)
	private String userdateofbirth;
	@Column(name = "User_Email_Id", unique = true, nullable = false)
	private String useremailid;
	@Column(name = "User_Address", nullable = false)
	private String useraddress;
	@Column(name = "User_Gender", nullable = false)
	private String usergender;
	@Column(name = "User_Age", nullable = false)
	private String userage;
	@Column(name = "User_Aadhar_Number", nullable = false)
	private String useraadharnumber;
	@Column(name = "User_User_Name", unique = true, nullable = false)
	private String userusername;
	@Column(name = "User_Password", nullable = false)
	private String userpassword;
	@Column(name = "User_Mobile_Number", unique = true, nullable = false)
	private String usermobilenumber;
	
	@Column(name = "Bank_Name", nullable = false)
	private String bankname;
	@Column(name = "Bank_Account_Number", unique = true, nullable = false)
	private int bankaccountnumber;
	@Column(name = "Bank_Balance", nullable = false)
	private long bankbalance;

}
