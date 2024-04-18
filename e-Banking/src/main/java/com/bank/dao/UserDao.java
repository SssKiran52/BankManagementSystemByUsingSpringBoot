package com.bank.dao;

import com.bank.entity.UserInfo;

public interface UserDao {

	public UserInfo userRegistration(UserInfo userInfo);
	
	public UserInfo userLoginByEmailIdOrMobileNumber(String emailid, String mobilenumber, String password);
	
	public UserInfo userEmailIdISPresentOrNot(String emailid);
	
	public UserInfo userMobileNumberIsPresentOrNot(String mobilenumber);
	
	public UserInfo userAadharNumberIsPresentOrNot(String aadharnumber);
	
	public UserInfo userUserNameIsPresentOrNot(String username);
	
	public UserInfo getuserdetailsbyid(int userid);
	
	public UserInfo getuserdetailsbyaccountnumber(int accountnumber);
	
	public UserInfo savedebitamount(UserInfo userInfo);
	
	public UserInfo forgotPasswordByUsingEmailIdOrMobileNumberAndDateOfBirth(String emailid, String mobilenumber, String date);
	
	public UserInfo saveForgotPassword(UserInfo userInfo);
	
	public UserInfo checkbankbalance(int userid);
	
	public UserInfo savecreditamount(UserInfo userInfo);


}
