package com.ravi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ravi.binding.DashboardResponse;
import com.ravi.binding.EnquiryForm;
import com.ravi.binding.EnquirySearchCriteria;
import com.ravi.entity.CourseEntity;
import com.ravi.entity.EnqStatusEntity;
import com.ravi.entity.StudentEnqEntity;
import com.ravi.entity.UserDtlsEntity;
import com.ravi.repository.CourseRepo;
import com.ravi.repository.EnqStatusRepo;
import com.ravi.repository.StudentEnqRepo;
import com.ravi.repository.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private StudentEnqRepo enqRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnqStatusRepo enqStatusRepo;

	@Autowired
	private HttpSession session;

	@Override
	public DashboardResponse getDashBoard(Integer userId) {

		DashboardResponse response = new DashboardResponse();

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {

			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();

			Integer totalCnt = enquiries.size();

			Integer enrolledCnt = enquiries.stream().filter(e -> e.getEnquryStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			Integer lostCnt = enquiries.stream().filter(e -> e.getEnquryStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			response.setTotalEnquery(totalCnt);
			response.setEnrolled(enrolledCnt);
			response.setLostCnt(lostCnt);
		}
		return response;
	}

	// course name dropDown data
	@Override
	public List<String> getCourseName() {
		List<CourseEntity> findAll = courseRepo.findAll();
		List<String> names = new ArrayList<>();
		for (CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}
		return names;
	}

	// enquiry status drop down data
	@Override
	public List<String> getEnqsStatus() {
		List<EnqStatusEntity> findAll = enqStatusRepo.findAll();

		List<String> statusList = new ArrayList<>();

		for (EnqStatusEntity entity : findAll) {

			statusList.add(entity.getEnquryStatus());
		}
		return statusList;
	}

	

	@Override
	public boolean saveEnquires(EnquiryForm form) {
		// TODO Auto-generated method stub
		StudentEnqEntity studEntity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, studEntity);
		Integer userId = (Integer) session.getAttribute("userId");

		UserDtlsEntity userEntity = userDtlsRepo.findById(userId).get();
		studEntity.setUser(userEntity);
		enqRepo.save(studEntity);
		return true;
	}

	@Override
	public List<StudentEnqEntity> getEnquiries() {
		// TODO Auto-generated method stub
		Integer userId = (Integer) session.getAttribute("userId");
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			return enquiries;
		}

		return null;
	}

	@Override
	public List<StudentEnqEntity> getFilterEnqs(EnquirySearchCriteria criteria, Integer userId) {

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();

			// filter logic
			if (null != criteria.getCourseName() & !"".equals(criteria.getCourseName())) {

				enquiries = enquiries.stream().filter(e -> e.getCourseName().equals(criteria.getCourseName()))
						.collect(Collectors.toList());
			}

			if (null != criteria.getEnquryStatus() & !"".equals(criteria.getEnquryStatus())) {

				enquiries.stream().filter(e -> e.getEnquryStatus().equals(criteria.getEnquryStatus()))
						.collect(Collectors.toList());
			}

			if (null != criteria.getClassMode() & !"".equals(criteria.getClassMode())) {

				enquiries.stream().filter(e -> e.getClassMode().equals(criteria.getClassMode()))
						.collect(Collectors.toList());
			}

			return enquiries;
		}

		return null;
	}
	@Override
	public List<EnquiryForm> getEnquires(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
