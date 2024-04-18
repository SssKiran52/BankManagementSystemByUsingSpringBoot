package com.bank.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.entity.UserInfo;
import com.bank.repository.UserRepository;

@Component
public class UserDaoImpl implements UserDao{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserInfo userRegistration(UserInfo userInfo) {
		System.out.println("Registration : "+userInfo);
		return userRepository.save(userInfo);
	}

	@ResponseBody
	public UserInfo userLoginByEmailIdOrMobileNumber(String emailid, String username, String password) {
		
		UserInfo userInfo = userRepository.findByUseremailidOrUsermobilenumberAndUserpassword(emailid, username, password);
		return userInfo;
		
	}

	@Override
	public UserInfo userEmailIdISPresentOrNot(String emailid) {
		
		UserInfo findByUseremailid = userRepository.findByUseremailid(emailid);
		return findByUseremailid;
	}

	@Override
	public UserInfo userMobileNumberIsPresentOrNot(String mobilenumber) {
		UserInfo findByUsermobilenumber = userRepository.findByUsermobilenumber(mobilenumber);
		return findByUsermobilenumber;
	}

	@Override
	public UserInfo userAadharNumberIsPresentOrNot(String aadharnumber) {
		UserInfo findByUseraadharnumber = userRepository.findByUseraadharnumber(aadharnumber);
		return findByUseraadharnumber;
	}

	@Override
	public UserInfo userUserNameIsPresentOrNot(String username) {
		UserInfo findByUserusername = userRepository.findByUserusername(username);
		return findByUserusername;
	}

	@Override
	public UserInfo getuserdetailsbyid(int userid) {
		UserInfo userInfo = userRepository.findById(userid).get();
		return userInfo;
	}

	@Override
	public UserInfo getuserdetailsbyaccountnumber(int accountnumber) {
		UserInfo findByBankaccountnumber = userRepository.findByBankaccountnumber(accountnumber);
		return findByBankaccountnumber;
	}

	@Override
	public UserInfo savedebitamount(UserInfo userInfo) {
		UserInfo save = userRepository.save(userInfo);
		return save;
	}

	@Override
	public UserInfo forgotPasswordByUsingEmailIdOrMobileNumberAndDateOfBirth(String emailid, String mobilenumber, String date) {
		UserInfo userInfo = userRepository.findByUseremailidOrUsermobilenumberAndUserdateofbirth(emailid, mobilenumber, date);
		return userInfo;
	}

	@Override
	public UserInfo saveForgotPassword(UserInfo userInfo) {
		UserInfo save = userRepository.save(userInfo);
		return save;
	}

	@Override
	public UserInfo checkbankbalance(int userid) {
		UserInfo findByUserid = userRepository.findByUserid(userid);
		return findByUserid;
	}

	@Override
	public UserInfo savecreditamount(UserInfo userInfo) {
		UserInfo save = userRepository.save(userInfo);
		return save;
	}

}
