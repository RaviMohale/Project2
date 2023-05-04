package com.ravi.services;

import com.ravi.binding.LoginForm;
import com.ravi.binding.SignUpForm;
import com.ravi.binding.UnlockForm;

public interface UserService {

	public String login(LoginForm form);

	public boolean signUp(SignUpForm form);

	public boolean unlockAccount(UnlockForm form);

	 public boolean forgetPwd(String email);

}
