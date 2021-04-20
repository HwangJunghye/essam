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
}
