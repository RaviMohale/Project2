package com.ravi.services;

import java.util.List;

import com.ravi.binding.DashboardResponse;
import com.ravi.binding.EnquiryForm;
import com.ravi.binding.EnquirySearchCriteria;
import com.ravi.entity.StudentEnqEntity;

public interface EnquiryService {
	
	
	public List<String> getCourseName();
	
	public List<String> getEnqsStatus();
	
	public boolean saveEnquires(EnquiryForm form);
	
	public DashboardResponse getDashBoard(Integer userId);
	
	public List<StudentEnqEntity> getEnquiries();
	
	public List<StudentEnqEntity> getFilterEnqs(EnquirySearchCriteria criteria,Integer userId);
	
	public List<EnquiryForm> getEnquires(Integer userId);
}
