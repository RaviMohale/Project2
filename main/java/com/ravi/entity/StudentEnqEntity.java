package com.ravi.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name ="AIT_STUDENT_ENQURIES")
public class StudentEnqEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enquryId;
	private String studName;
	private Long phone;
	private String classMode;
	private String courseName;
	private String enquryStatus;
	@CreationTimestamp
	private LocalDate createDate;
	@UpdateTimestamp
	private LocalDate updateDate;
	@ManyToOne
	@JoinColumn(name= "user_Id")
	private UserDtlsEntity user;
	

}
