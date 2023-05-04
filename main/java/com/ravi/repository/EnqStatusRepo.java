package com.ravi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ravi.entity.EnqStatusEntity;

public interface EnqStatusRepo  extends JpaRepository<EnqStatusEntity, Integer>{

}
