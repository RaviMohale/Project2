package com.ravi.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ravi.binding.LoginForm;
import com.ravi.binding.SignUpForm;
import com.ravi.binding.UnlockForm;
import com.ravi.entity.UserDtlsEntity;
import com.ravi.repository.UserDtlsRepo;
import com.ravi.utility.EmailUtils;
import com.ravi.utility.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public String login(LoginForm form) {
		UserDtlsEntity entity = userDtlsRepo.findByUserEmailAndUserPwd(form.getUserEmail(), form.getUserPwd());
		
		if(entity == null) {
			return "Invalid Credentials";
		}if(entity.getAccStatus().equals("Locked")) {
			return "Your Account is Locked";
		}
		
		//create session and store user data in session
		session.setAttribute("userId", entity.getUserId());
		return "success";
	}


	@Override
	public boolean signUp(SignUpForm form) {
		
		UserDtlsEntity user = userDtlsRepo.findByUserEmail(form.getUserEmail());
			if(user != null) {
				return false;
			}

		// TODO: copy data from binding obj to entity obj

		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);

		// TODO: generated random pwd and set to object

		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setUserPwd(tempPwd);

		// TODO: set account status as Locked
		entity.setAccStatus("Locked");

		// TODO: insert the record into table
		userDtlsRepo.save(entity);

		// TODO: send email to unlock the account...
		String to = form.getUserEmail();
		String subject = "Unlock the account";

		StringBuffer body = new StringBuffer("");

		body.append("<h1>User below temp password to unlock your account</h1>");
		
		body.append("tempory password : " + tempPwd);
		
		body.append("<br/>");
		
		body.append("<a href=\"http://localhost:9090/unlock?email="+to+"\">Click here to unlock the account</a>");
		emailUtils.sendEmail(to, subject, body.toString());

		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {
				UserDtlsEntity entity = userDtlsRepo.findByUserEmail(form.getEmail());
				if(entity.getUserPwd().equals(form.getTempPwd())) {
					entity.setUserPwd(form.getNewPwd());
					entity.setAccStatus("Unlocked");
					userDtlsRepo.save(entity);
					return true;
					
				}else {
					return false;
				}
	
	}


	@Override
	public boolean forgetPwd(String email) {
		//check the data with given mail is present in db or not
		
		UserDtlsEntity entity = userDtlsRepo.findByUserEmail(email);
		
		// if record not present with given data bases than return false
			if(entity == null) {
				return false;
			}
		
		//if record available than return true
			String Subject = "Recover Password";
			String body ="Your Password :: " +entity.getUserPwd();
			
			emailUtils.sendEmail(email, Subject, body);
		return true;
	}

	

}
