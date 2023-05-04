package com.ravi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ravi.entity.UserDtlsEntity;

public interface UserDtlsRepo  extends JpaRepository<UserDtlsEntity, Integer>{
	
	public UserDtlsEntity  findByUserEmail(String userEmail);
	
	public UserDtlsEntity  findByUserEmailAndUserPwd(String userEmail, String userPwd);

}
