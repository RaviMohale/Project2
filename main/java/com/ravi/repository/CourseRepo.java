package com.ravi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ravi.entity.CourseEntity;

public interface CourseRepo  extends JpaRepository<CourseEntity, Integer>{

}
