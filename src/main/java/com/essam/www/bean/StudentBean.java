package com.essam.www.bean;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.experimental.Accessors;

@Alias("studentbean")
@Data
@Accessors(chain = true)
public class StudentBean {
	/*
	regiNo     		NVARCHAR2 
	clsNo      		NVARCHAR2 
	mbId       		NVARCHAR2
	regiStartDate   DATE
	regiEndDate     DATE
	*/
	
	private String regiNo;
	private String clsNo;
	private String mbId;
	private String regiStartDate;
	private String regiEndDate;	

	//추가된 필드(출결현황 관리용)
	private String mbNickName; //닉네임
	private String clsName; //클래스이름
	private int totalDay; //총수업일수
	private int attendDay; //출석일수
	private int absenceDay; //결석일수
	 
	
}
