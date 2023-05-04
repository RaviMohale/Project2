package com.ravi.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ravi.entity.CourseEntity;
import com.ravi.entity.EnqStatusEntity;
import com.ravi.repository.CourseRepo;
import com.ravi.repository.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo enqRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		courseRepo.deleteAll();
		
		CourseEntity c1 = new CourseEntity();
		c1.setCourseName("Java");
		
		CourseEntity c2 = new CourseEntity();
		c2.setCourseName("Python");
		
		CourseEntity c3 = new CourseEntity();
		c3.setCourseName("AWS");
		
		CourseEntity c4 = new CourseEntity();
		c4.setCourseName("DevOps");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		
		enqRepo.deleteAll();
		
		EnqStatusEntity s1 = new EnqStatusEntity();
		s1.setEnquryStatus("New");
		
		EnqStatusEntity s2 = new EnqStatusEntity();
		s2.setEnquryStatus("Enrolled");
		
		EnqStatusEntity s3 = new EnqStatusEntity();
		s3.setEnquryStatus("Lost");
		
		enqRepo.saveAll(Arrays.asList(s1,s2,s3));
		
		
		
	
		
	}

}
