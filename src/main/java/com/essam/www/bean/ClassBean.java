package com.essam.www.bean;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.experimental.Accessors;

@Alias("classbean")
@Data
@Accessors(chain = true)
public class ClassBean {
	/*
	clsNo       NVARCHAR2
	mbId        NVARCHAR2
	fileNo      NVARCHAR2 
	cate1No     NUMBER 
	cate2No     NUMBER
	clsName     NVARCHAR2 
	clsLimit    NUMBER 
	clsIntro    NVARCHAR2
	clsDesc     NCLOB  
	clsPrice    NUMBER 
	clsKeyword  NVARCHAR2 
	zoomLink    NVARCHAR2 
	zoomPwd     NVARCHAR2 
	*/

	private String clsNo;
	private String clsName;
	private String mbId;
	private String fileNo;
	private int cate1No;
	private int cate2No;
	private int clsLimit;
	private String clsIntro;
	private String clsDesc;
	private int clsPrice;
	private String clsKeyword;
	private String zoomLink;
	private String zoomPwd;
	//추가된 필드
	private String mbNickName;
	private String cate1Name;
	private String cate2Name;
	private int clsRegiCnt;
	
	
}
