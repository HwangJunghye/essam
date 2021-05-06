package com.essam.www.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.experimental.Accessors;

@Alias("adminbean")
@Data
@Accessors(chain = true)
public class AdminBean {
	private String startDate;	 //조회시작일	
	private String endDate;		 //조회종료일	
	private String searchTarget; //조회대상(클래스/강사/수강생)			
	private String filter;		 //필터(전체/신규)		
	
}
