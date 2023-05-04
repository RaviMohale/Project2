package com.ravi.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ravi.binding.LoginForm;
import com.ravi.binding.SignUpForm;
import com.ravi.binding.UnlockForm;
import com.ravi.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	 

	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	// method to handle the sign form i.e submit the data

	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form, Model model) {

		boolean status = userService.signUp(form);

		if (status) {
			model.addAttribute("successMsg", "Account Created, Check Your Email");

		} else {
			model.addAttribute("errMsg", "Choose Unique Email");
		}
		return "signup";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {
		UnlockForm unlockObj = new UnlockForm();
		unlockObj.setEmail(email);
		model.addAttribute("unlock", unlockObj);

		return "unlock";
	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {
		System.out.println(unlock);

		if (unlock.getNewPwd().equals(unlock.getComfirmPwd())) {
			boolean status = userService.unlockAccount(unlock);

			if (status) {
				model.addAttribute("succMsg", " Your account unlock Successfully");
			} else {
				model.addAttribute("error", " Given temporary password is incorrect,Check your email..");
			}

			} else {
			model.addAttribute("error", " New pwd and Comfirm Pwd not equal");
		}

		return "unlock";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm")LoginForm loginForm ,Model model) {
		String status = userService.login(loginForm);
		if(status.contains("success")) {
			
			//redirect req to dashboard method
			//return "dashboard";
			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg",status );
		return "login";
	}
		// it loaded empty forget password page..
	@GetMapping("/forgot")
	public String forgotPwdPage() {
		return "forgotPwd";
	}


	@PostMapping("/forgotPwd")
	public String forgotPwd(@RequestParam("email") String email,Model model) {
		System.out.println(email);
		
		boolean status = userService.forgetPwd(email);
		
		if(status) {
			model.addAttribute("succMsg", "Password sent to email");
		}else {
			
			model.addAttribute("errMsg", "Invalid email..");
		}
		return "forgotPwd";
	}

}
