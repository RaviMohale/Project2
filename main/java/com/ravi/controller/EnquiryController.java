package com.ravi.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ravi.binding.DashboardResponse;
import com.ravi.binding.EnquiryForm;
import com.ravi.binding.EnquirySearchCriteria;
import com.ravi.entity.StudentEnqEntity;
import com.ravi.repository.StudentEnqRepo;
import com.ravi.services.EnquiryService;

@Controller
public class EnquiryController {
	
	@Autowired
	private EnquiryService enqService;
	
	@Autowired
	private StudentEnqRepo enqRepo;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/logout")
	private String logout() {
		session.invalidate();
		return "index";
	}
	
	private void initForm(Model model) {
		List<String> courseName = enqService.getCourseName();
		List<String> enqsStatus = enqService.getEnqsStatus();
		EnquiryForm formObj = new EnquiryForm();
		model.addAttribute("courseName", courseName);
		model.addAttribute("enqsStatus", enqsStatus);
		model.addAttribute("formObj", formObj);
		
	}
	
	@GetMapping("/dashboard")
	public String dashBoardPage(Model model) {
		// System.out.println("dashboard method called");
		
		Integer userId = (Integer)session.getAttribute("userId");
		
		DashboardResponse dashBoardData = enqService.getDashBoard(userId);
		model.addAttribute("dashBoardData", dashBoardData);
		return "dashboard";
	}
	
	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj")EnquiryForm formObj ,Model model) {
		System.out.println(formObj);
		//TODO : save the data
		boolean status = enqService.saveEnquires(formObj);
		
		if(status) {
			model.addAttribute("succMsg", "Enquiry Added");
			
		}else {
			model.addAttribute("errMsg", "Problem Occured");
		}
		
		return "addEnquiry";
	}
	
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		
		//get  courses from drop down
		List<String> course = enqService.getCourseName();
		
		//get enq status from  drop down
		List<String> enqsStatus = enqService.getEnqsStatus();
		
		
		//create binding class object
		EnquiryForm formObj =  new EnquiryForm();
		
		// set data in model object..
		model.addAttribute("courseName", course);
		model.addAttribute("enqsStatusName", enqsStatus);
		model.addAttribute("formObj", formObj);
		
		return "addEnquiry";
	}
	
	@GetMapping("/enquires")
	public String viewEnquiryPage(Model model) {
		initForm(model);
	     List<StudentEnqEntity> enquiries = enqService.getEnquiries();
		model.addAttribute("enquiries", enquiries);
	return "viewEnquiry";
	}
	
	@GetMapping("/filter-enquiries")
	public String getFilteredEnq(@RequestParam("cname") String cname,
			@RequestParam("status") String status,@RequestParam("mode") String mode,Model model) {
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setEnquryStatus(status);
		criteria.setClassMode(mode);
		
		Integer userId = (Integer)session.getAttribute("userId");
		
		List<StudentEnqEntity> filterEnqs = enqService.getFilterEnqs(criteria, userId);
		model.addAttribute("enquiries", filterEnqs);
		
		return "filter-Enquiry-page";
	}
	
}
