package com.bank.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.dao.BankLoanDao;
import com.bank.dao.StatementDao;
import com.bank.dao.UserDao;
import com.bank.entity.BankLoanInfo;
import com.bank.entity.StatementInfo;
import com.bank.entity.UserInfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class StatementController {

	@Autowired
	StatementDao statementDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	BankLoanDao bankLoanDao;
	
	@RequestMapping("/homepage")
	public String homepage() {
		return "HomePage";
	}
	
	
	@RequestMapping("/debitpage")
	public String debitpage() {
		return "DebitPage";
	}
	
	
	@RequestMapping("/debitamount")
	public String debitamountpage() {
		return "DebitAmount";
	}
	
	@RequestMapping("/checkamountpage")
	public String checkamountpage() {
		return "CheckAmount";
	}
	
	@RequestMapping("/creditamountpage")
	public String creditamountpage() {
		return "CreditAmount";
	}
	
	@RequestMapping("/statementpage")
	public String statementpage() {
		return "StatementPage";
	}
	
	@RequestMapping("/bankloanpage")
	public String bankloanpage() {
		return "BankLoan";
	}
	
	@RequestMapping("/debitamountdetails")
	public String debitamount(@RequestParam("accountnumber") int bankaccountnumber, HttpServletRequest request,Model model) {
		
		HttpSession session = request.getSession();
		int userid = (int) session.getAttribute("userid");
		UserInfo getuserdetailsbyid = userDao.getuserdetailsbyid(userid);
		long mainbalance = getuserdetailsbyid.getBankbalance();
		session.setAttribute("mainbalance", mainbalance);
		int useraccountnumber = getuserdetailsbyid.getBankaccountnumber();
		System.out.println(bankaccountnumber);
		System.out.println(useraccountnumber);
		if (bankaccountnumber == useraccountnumber) {
			
			model.addAttribute("yournumber", "It's Your Account Number..!!");
			System.out.println("It's Your Account Number..!!");
			return "DebitPage";
			
		} else {
			
			session.setAttribute("receiveraccountnumber", bankaccountnumber);

							
				UserInfo getuserdetailsbyaccountnumber = userDao.getuserdetailsbyaccountnumber(bankaccountnumber);
				
				if (getuserdetailsbyaccountnumber!=null) {
					
					long bankbalance = getuserdetailsbyaccountnumber.getBankbalance();
					int accountnumber = getuserdetailsbyaccountnumber.getBankaccountnumber();
					session.setAttribute("amount", bankbalance);
					session.setAttribute("account", accountnumber);
					return "DebitAmount";
					
				} else {
					
					model.addAttribute("notfound", "Account Number Not Found..!!");
					System.out.println("Account Number Not Found..!!");
					return "DebitPage";
					
				}
		}
		
		
	}
	
	@RequestMapping("/callingdebitamountpage")
	public String callingdebitamountpage(@RequestParam("debitamount") long debitamount, HttpServletRequest request,Model model) {
		
		
		HttpSession session = request.getSession();
		long mainbalance = (long) session.getAttribute("mainbalance");
		
		if (debitamount>0) {
			
			if (1000000000000000000l>=debitamount) {
				
				if (debitamount<=100000) {
					
					if (debitamount<=mainbalance) {
						
						long useramount = (long) session.getAttribute("amount");
						int accountnumber = (int) session.getAttribute("account");
						
						long totalamount = useramount+debitamount;
						
						UserInfo getuserdetails = userDao.getuserdetailsbyaccountnumber(accountnumber);
						getuserdetails.setBankbalance(totalamount);
						
						UserInfo savedebitamount = userDao.savedebitamount(getuserdetails);
						
						if (savedebitamount!=null) {
							
							int userid = (int) session.getAttribute("userid");
							
							UserInfo getuserdetailsbyid = userDao.getuserdetailsbyid(userid);
							
							long bankbalance = getuserdetailsbyid.getBankbalance();
							
							long updatedamount = bankbalance-debitamount;
							
							getuserdetailsbyid.setBankbalance(updatedamount);
						
							UserInfo savedebitamount2 = userDao.savedebitamount(getuserdetailsbyid);
							
							StatementInfo statementInfo = new StatementInfo();
							
							Random random = new Random();
							int transaction = random.nextInt(100000);
							if (transaction<10000) {
								transaction = transaction+10000;
							}
							
							statementInfo.setTransactionid(transaction);
							statementInfo.setTransactiondate(LocalDate.now());
							LocalTime now = LocalTime.now();
							statementInfo.setTransactiontime(now.toString());
							statementInfo.setTransactiondebit(debitamount);
							statementInfo.setUserid(getuserdetailsbyid.getUserid());
							
							int receiveraccountnumber = (int) session.getAttribute("receiveraccountnumber");
							
							statementInfo.setTransferaccountnumber(receiveraccountnumber);
							statementInfo.setBankbalance(bankbalance-debitamount);
							
							StatementInfo savedebitamount3 = statementDao.savedebitamount(statementInfo);
							if (savedebitamount3!=null) {
								System.out.println("Debit Credentials Saved Into Database...");
							} else {
								System.out.println("Debit Credentials Not Saved Into Database...");
							}
							
							
							StatementInfo statementInfo2 = new StatementInfo();
							
							int transaction2 = random.nextInt(100000);
							if (transaction2<10000) {
								transaction2 = transaction2+10000;
							}
							
							statementInfo2.setTransactionid(transaction2);
							statementInfo2.setTransactiondate(LocalDate.now());
//							LocalTime now = LocalTime.now();
							statementInfo2.setTransactiontime(now.toString());
							statementInfo2.setTransactioncredit(debitamount);
							statementInfo2.setUserid(getuserdetails.getUserid());
							statementInfo2.setTransferaccountnumber(getuserdetailsbyid.getBankaccountnumber());
							statementInfo2.setBankbalance(totalamount);
							
							StatementInfo savedebitamount4 = statementDao.savedebitamount(statementInfo2);
							if (savedebitamount4!=null) {
								System.out.println("Credit Credentials Saved Into Database...");
							} else {
								System.out.println("Credit Credentials Not Saved Into Database...");
							}
							
							
							
							
							model.addAttribute("sucess", "Transaction Successfull...");
							System.out.println("Transaction Successfull...");
							return "DebitAmount";
							
						} else {
							model.addAttribute("fail", "Transaction Failed..!!");
							System.out.println("Transaction Failed..!!");
							return "DebitAmount";
						}
						
						
					} else {
						
						System.out.println("Invalid Amount..!!");
						model.addAttribute("invalid", "Invalid Amount..!!");
						return "DebitAmount";
						
					}	
					
				} else {
					
					System.out.println("Transaction UpTo 1 Lakh Only..!!");
					model.addAttribute("upto", "Transaction UpTo 1 Lakh Only..!!");
					return "DebitAmount";
					
				}
				
			} else {
				
				System.out.println("Amount Out Of Range..!!");
				model.addAttribute("range", "Amount Out Of Range..!!");
				return "DebitAmount";
			}
		} else {

			System.out.println("Invalid Amount..!!");
			model.addAttribute("invalid", "Invalid Amount..!!");
			return "DebitAmount";
			
		}
		
		
	}	
	
	
	
	
	@RequestMapping("/checkbalancedetails")
	public String checkbalance(@RequestParam("otp") int userotp, HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		int generatedOtp = (int) session.getAttribute("otp");
		
		if (userotp==generatedOtp) {
				
			int userid = (int) session.getAttribute("userid");
			UserInfo checkbankbalance = userDao.checkbankbalance(userid);
			
			if (checkbankbalance!=null) {
				
				model.addAttribute("details", checkbankbalance);
				System.out.println("Bank Balance...");
				return "CheckAmount";
				
			} else {
				
				model.addAttribute("notfound", "No Details Found..!!");
				System.out.println("No Details Found..!!");
				return "CheckAmount";
			}
			
			
		} else {
			
			model.addAttribute("miss", "OTP Missmatch..!!");
			System.out.println("OTP Missmatch..!!");
			return "CheckAmount";
		}
		
	}
	
	@RequestMapping("/generateotp")
	public String generateotp(Model model, HttpServletRequest request) {
		
		Random random = new Random();
		int otp = random.nextInt(10000);
		if (otp<1000) {
			otp+=1000;
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("otp", otp);
		
		model.addAttribute("otp", otp);
		return "CheckAmount";
	}
	
	
	@RequestMapping("/creditamountdetails")
	public String creditamountdetails(@RequestParam("creditamount") long creditamount, HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		int userid = (int) session.getAttribute("userid");
		
		UserInfo getuserdetailsbyid = userDao.getuserdetailsbyid(userid);
		int bankaccountnumber = getuserdetailsbyid.getBankaccountnumber();
		long bankbalance = getuserdetailsbyid.getBankbalance();
		
		if (creditamount>0) {
			
			if (1000000000000000000l>=creditamount) {
				
				if (creditamount<=1000000) {
					
					long totalamount = bankbalance+creditamount;
					
					getuserdetailsbyid.setBankbalance(totalamount);
					
					UserInfo savecreditamount = userDao.savecreditamount(getuserdetailsbyid);
					
					if (savecreditamount!=null) {
						
						
						StatementInfo statementInfo = new StatementInfo();
						
						Random random = new Random();
						int transaction = random.nextInt(100000);
						if (transaction<10000) {
							transaction = transaction+10000;
						}
						
						statementInfo.setTransactionid(transaction);
						statementInfo.setTransactiondate(LocalDate.now());
						LocalTime now = LocalTime.now();
						statementInfo.setTransactiontime(now.toString());
						statementInfo.setTransactioncredit(creditamount);
						statementInfo.setUserid(userid);
						statementInfo.setTransferaccountnumber(bankaccountnumber);
						statementInfo.setBankbalance(totalamount);
						
						StatementInfo savecreditamount2 = statementDao.savecreditamount(statementInfo);
						
						if (savecreditamount2!=null) {
							
							System.out.println("Data Saved Into Database...");
							System.out.println("Amount Successfully Credited...");
							model.addAttribute("success", "Amount Successfully Credited...");
							return "CreditAmount";
							
						} else {
							
							System.out.println("Data Saved Into Database..!!");
							System.out.println("Amount Not Credited..!!");
							model.addAttribute("notcredit", "Amount Not Credited..!!");
							return "CreditAmount";
							
						}
				
						
					} else {
						
						System.out.println("Amount Not Credited..!!");
						model.addAttribute("notcredit", "Amount Not Credited..!!");
						return "CreditAmount";
						
					}
					
				} else {
					
					System.out.println("Credit Amount Must Be LessThan Or Equals To 10 Lakhs..!");
					model.addAttribute("must", "Credit Upto To 10 Lakhs Only..!");
					return "CreditAmount";
					
				}
				
			} else {
				
				System.out.println("Amount Out Of Range..!!");
				model.addAttribute("range", "Amount Out Of Range..!!");
				return "CreditAmount";	
				
			}
				
		} else {

			System.out.println("Invalid Amount..!!");
			model.addAttribute("invalid", "Invalid Amount..!!");
			return "CreditAmount";
			
		}
		
	}
	
	
	
	
	@RequestMapping("/bankstatementpage")
	public String bankstatement(@RequestParam("fromdate") LocalDate fromdate, @RequestParam("todate") LocalDate todate, HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		int userid = (int) session.getAttribute("userid");
		
		List<StatementInfo> getbankstatement = statementDao.getbankstatement(fromdate, todate);
		
		List<StatementInfo>  list = new ArrayList<>();
		
		for (StatementInfo statementDao : getbankstatement) {
			
			int userid2 = statementDao.getUserid();
			
			if (userid==userid2) {
				
				UserInfo getuserdetailsbyid2 = userDao.getuserdetailsbyid(userid);
				
				int bankaccountnumber = getuserdetailsbyid2.getBankaccountnumber();
				int usernumber = getuserdetailsbyid2.getUserid();
				String userusername = getuserdetailsbyid2.getUserusername();
				String usermobilenumber = getuserdetailsbyid2.getUsermobilenumber();
				
				model.addAttribute("accountnumber", bankaccountnumber);
				model.addAttribute("userid", usernumber);
				model.addAttribute("username", userusername);
				model.addAttribute("mobilenumber", usermobilenumber);
				
				String time = statementDao.getTransactiontime();
				String[] parts = time.split(":");
				int hours = Integer.parseInt(parts[0]);
				int minutes = Integer.parseInt(parts[1]);
				String[] parts1 = parts[2].split("\\.");
				int seconds = Integer.parseInt(parts1[0]);
				String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
				statementDao.setTransactiontime(formattedTime);
				
				list.add(statementDao);
				System.out.println(statementDao);
			}
		}

		
		if (list.isEmpty()) {
			
			System.out.println("No Transaction Statements..!!");
			model.addAttribute("notransactions", "No Transaction Statements..!!");
			return "StatementPage";
			
		} else {
			
			System.out.println("List of Transactions : "+list);
			model.addAttribute("listobject", list);
			return "TransactionPage";
		}
		
	}
	
	
	@RequestMapping("/bankloandetails")
	public String bankloandetails(@RequestParam("amount") Long requestedamount, Model model, HttpServletRequest request) {
		
		if (requestedamount>0) {
			
			if (requestedamount<=1000000) {
				
				BankLoanInfo bankLoanInfo = new BankLoanInfo();
				
				Random random = new Random();
				int application = random.nextInt(100000);
				if (application<10000) {
					application = application+10000;
				}
				
				bankLoanInfo.setApplicationid(application);
				bankLoanInfo.setRequestedamount(requestedamount);
				bankLoanInfo.setApplicationdate(LocalDate.now());
				LocalTime now = LocalTime.now();
				bankLoanInfo.setApplicationtime(now.toString());
				
				HttpSession session = request.getSession();
				int userid = (int) session.getAttribute("userid");
				
				UserInfo getuserdetailsbyid = userDao.getuserdetailsbyid(userid);
				
				bankLoanInfo.setUserid(getuserdetailsbyid.getUserid());
				bankLoanInfo.setUserusername(getuserdetailsbyid.getUserusername());
				bankLoanInfo.setUsermobilenumber(getuserdetailsbyid.getUsermobilenumber());
				bankLoanInfo.setBankaccountnumber(getuserdetailsbyid.getBankaccountnumber());
				
				BankLoanInfo savebankloanapplication = bankLoanDao.savebankloanapplication(bankLoanInfo);
				
				if (savebankloanapplication!=null) {
					
					System.out.println("Loan Applied Successfully...");
					model.addAttribute("success", "Loan Applied Successfully...");
					return "BankLoan";
					
				} else {
					
					System.out.println("Application Declined..!!");
					model.addAttribute("decline", "Application Declined..!!");
					return "BankLoan";
					
				}
				
			} else {
				System.out.println("Apply Loan Upto 10 Lakhs Only..!!");
				model.addAttribute("upto", "Apply Loan Upto 10 Lakhs Only..!!");
				return "BankLoan";
			}
			
		} else {
			System.out.println("Insufficient Amount..!!");
			model.addAttribute("insuff", "Insufficient Amount..!!");
			return "BankLoan";
		}
		
	}
	
	
	
}
