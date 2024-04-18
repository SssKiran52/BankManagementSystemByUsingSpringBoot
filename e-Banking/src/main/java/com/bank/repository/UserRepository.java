package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bank.entity.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Integer>{

	@Query("select userinfo from UserInfo userinfo where (userinfo.useremailid=?1 or userinfo.usermobilenumber=?2) and userinfo.userpassword=?3")
	UserInfo findByUseremailidOrUsermobilenumberAndUserpassword(String emailid, String mobilenumber, String password);
	
	UserInfo findByUseremailid(String emailid);
	
	UserInfo findByUsermobilenumber(String mobilenumber);
	
	UserInfo findByUseraadharnumber(String useraadharnumber);
	
	UserInfo findByUserusername(String userusername);
	
	UserInfo findByUserid(int userid);
	
	UserInfo findByBankaccountnumber(int bankaccountnumber);
	
	@Query("select userinfo from UserInfo userinfo where (userinfo.useremailid=?1 or userinfo.usermobilenumber=?2) and userinfo.userdateofbirth=?3")
	UserInfo findByUseremailidOrUsermobilenumberAndUserdateofbirth(String emailid, String mobilenumber, String date);
	
}

