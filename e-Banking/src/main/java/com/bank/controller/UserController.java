package com.bank.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.dao.UserDao;
import com.bank.entity.UserInfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	UserDao userDao;
	
	
	@RequestMapping("/sssbank")
	public String bankoptionspage() {
		return "BankOptions";
	}
	
	@RequestMapping("/registrationpage")
	public String registrationpage() {
		return "RegistrationPage";
	}
	
	@RequestMapping("/loginpage")
	public String loginpage() {
		return "LoginPage";
	}
	
	@RequestMapping("/forgotpasswordpage")
	public String forgotpasswordpage() {
		return "ForgotPassword";
	}
	
	
	@RequestMapping("/registrationdetails")
	public String registration(UserInfo userInfo, Model model) {
		System.out.println("Controller : "+userInfo);
		
		String userfirstname = userInfo.getUserfirstname();
		String userlastname = userInfo.getUserlastname();
		String userdateofbirth = userInfo.getUserdateofbirth();
		String useremailid = userInfo.getUseremailid();
		String usermobilenumber = userInfo.getUsermobilenumber();	
		String useraddress = userInfo.getUseraddress();
		String usergender = userInfo.getUsergender();
		String userage = userInfo.getUserage();
		String useraadharnumber = userInfo.getUseraadharnumber();
		String userusername = userInfo.getUserusername();
		String userpassword = userInfo.getUserpassword();
		long bankbalance = userInfo.getBankbalance();
		String bankname = "SSS";
		
		Random random = new Random();
		
		int bankaccountnumber = random.nextInt(10000000);
		if (bankaccountnumber<1000000) {
			bankaccountnumber = bankaccountnumber+1000000;
		}
		
		
		UserInfo userinfo = new UserInfo();
		
		UserInfo userEmailIdISPresentOrNot = userDao.userEmailIdISPresentOrNot(useremailid);
		if (userEmailIdISPresentOrNot!=null) {
			
			model.addAttribute("email", "Email Id Is Already Exists...!!");
			System.out.println("Email Id Is Already Exists...!!");
			return "RegistrationPage";
			
		} else {
			
			if (usermobilenumber.length()==10) {
				
				UserInfo userMobileNumberIsPresentOrNot = userDao.userMobileNumberIsPresentOrNot(usermobilenumber);
				
				if (userMobileNumberIsPresentOrNot!=null) {
					model.addAttribute("mobile", "Mobile Number Is Already Exists...!!");
					System.out.println("Mobile Number Is Already Exists...!!");
					return "RegistrationPage";
				} else {
					
					if (useraadharnumber.length()==12) {
						
						UserInfo userAadharNumberIsPresentOrNot = userDao.userAadharNumberIsPresentOrNot(useraadharnumber);
						
						if (userAadharNumberIsPresentOrNot!=null) {
							model.addAttribute("aadhar", "Aadhar Number Is Already Exists...!!");
							System.out.println("Aadhar Number Is Already Exists...!!");
							return "RegistrationPage";
						} else {
							
							UserInfo userUserNameIsPresentOrNot = userDao.userUserNameIsPresentOrNot(userusername);
							
							if (userUserNameIsPresentOrNot!=null) {
								
								model.addAttribute("user", "User Name Already Exits..!!");
								System.out.println("User Name Already Exits..!!");
								System.out.println("Kindly Change Your User Name...!");
								return "RegistrationPage";
								
							} else {
									
								
								if (bankbalance>0) {
									
									if (1000000>=bankbalance) {
										
										userinfo.setUserfirstname(userfirstname);
										userinfo.setUserlastname(userlastname);
										userinfo.setUserdateofbirth(userdateofbirth);
										userinfo.setUseremailid(useremailid);
										userinfo.setUsermobilenumber(usermobilenumber);
										userinfo.setUseraddress(useraddress);
										userinfo.setUsergender(usergender);
										userinfo.setUserage(userage);
										userinfo.setUseraadharnumber(useraadharnumber);
										userinfo.setUserusername(userusername);
										userinfo.setUserpassword(userpassword);
										userinfo.setBankname(bankname);
										userinfo.setBankaccountnumber(bankaccountnumber);
										userinfo.setBankbalance(bankbalance);
										
									} else {
										
										System.out.println("Amount UpTo 10 Lakhs Only..!");
										model.addAttribute("upto", "Amount UpTo 10 Lakhs Only..!");
										return "RegistrationPage";
										
									}
									
								} else {
									
									System.out.println("Invalid Amount...!!!");
									model.addAttribute("invalid", "Invalid Amount...!!!");
									return "RegistrationPage";
									
								}
							
							}
							
						}
						
					} else {
						model.addAttribute("aadharsize", "Aadhar Number Must Be 12 Digits..!!");
						System.out.println("Aadhar Number Must Be 12 Digits..!!");
						return "RegistrationPage";
					}
					
				}
				
			} else {
				model.addAttribute("mobilesize", "Mobile Number Must Be 10 Digits..!!");
				System.out.println("Mobile Number Must Be 10 Digits..!!");
				return "RegistrationPage";
			}
			
		}
		
		UserInfo userRegistration = userDao.userRegistration(userinfo);
		if (userRegistration!=null) {
//			return "<center><h1 style=color:green><i>Registration Successfull...</i></h1></center>";
			return "LoginPage";
		} else {
//			return "<center><h1 style=color:red><i>Invalid Registration..!!</i></h1></center>";
			return "RegistrationPage";
		}
		
	}
	
	
	
	
	@RequestMapping("/logindetails")
	public String login(@RequestParam("emailid") String emailid,@RequestParam("password") String password, Model model, HttpServletRequest request) {
		
		UserInfo userInfo = userDao.userLoginByEmailIdOrMobileNumber(emailid, emailid, password);
		if (userInfo!=null) {
			
			HttpSession session = request.getSession();
			session.setAttribute("userid", userInfo.getUserid());
			
			System.out.println(userInfo);
			return "HomePage";
		} else {
			model.addAttribute("msg","Invalid Details..!!");
			return "LoginPage";
		}
		
	}
	
	
	
	@RequestMapping("/forgotpassworddetails")
	public String forgotpassword(@RequestParam("emailid") String emailid,@RequestParam("dob") String date, @RequestParam("password") String password, @RequestParam("conformpassword") String conformpassword, Model model) {
		System.out.println(emailid);
		System.out.println(date);
		System.out.println(password);
		System.out.println(conformpassword);
			UserInfo forgotPassword = userDao.forgotPasswordByUsingEmailIdOrMobileNumberAndDateOfBirth(emailid, emailid, date);
			System.out.println("forgot : "+forgotPassword);
			if (forgotPassword!=null) {
				
				if (password.equals(conformpassword)) {
					
					forgotPassword.setUserpassword(password);
					UserInfo saveForgotPassword = userDao.saveForgotPassword(forgotPassword);
					
					if (saveForgotPassword!=null) {
						
						model.addAttribute("change", "Password Updated Successfully...");
						System.out.println("Password Updated Successfully...");
						return "ForgotPassword";
						
					} else {
						
						model.addAttribute("passnot", "Password Not Updated..!!");
						System.out.println("Password Not Updated..!!");
						return "ForgotPassword";
						
					}
					
				} else {
					
					model.addAttribute("pass", "Password Missmatch..!!");
					System.out.println("Password Missmatch..!!");
					return "ForgotPassword";
					
				}
				
			} else {
				model.addAttribute("user", "User Not Exists...!!");
				System.out.println("User Not Exists...!!");
				return "ForgotPassword";
				
			}
			
	}

	
	
	
	
}
