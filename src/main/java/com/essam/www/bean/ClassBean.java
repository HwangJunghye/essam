package com.essam.www.bean;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.experimental.Accessors;

@Alias("classbean")
@Data
@Accessors(chain = true)
public class ClassBean {

	private String clsNo;     //클래스일련번호 NVARCHAR2
	private String clsName;   //클래스명 NVARCHAR2
	private String mbId;      //교사아이디(이메일) NVARCHAR2
	private String fileNo;    //클래스이미지파일 일련번호 NVARCHAR2
	private int cate1No;      //카테고리1번호 NUMBER
	private int cate2No;      //카테고리2번호 NUMBER
	private int clsLimit;     //클래스 정원 NUMBER
	private String clsIntro;  //한줄 소개 NVARCHAR2
	private String clsDesc;   //상세 소개 NVARCHAR2
	private int clsPrice;     //가격 NUMBER
	private String clsKeyword;//키워드 NVARCHAR2
	private String zoomLink;  //ZOOM링크 NVARCHAR2
	private String zoomPwd;   //ZOOM비밀번호 NVARCHAR2
	private String clsOpenDate;//ZOOM비밀번호 NVARCHAR2
	
	//추가된 필드
	private String mbNickName;//닉네임 NVARCHAR2
	private String cate1Name; //카테고리1명 NVARCHAR2
	private String cate2Name; //카테고리2명 NVARCHAR2
	private int clsRegiCnt;   //클래스 등록인원수
	
	
}
