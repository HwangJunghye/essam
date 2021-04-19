package com.essam.www.bean;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.experimental.Accessors;

@Alias("memberbean")
@Data
@Accessors(chain = true)
public class MemberBean {
	/*
	 mbId	 NVARCHAR2
	 mbType	 NUMBER
	 mbName	 NVARCHAR2
	 mbPwd	NVARCHAR2
	 mbNickName	NVARCHAR2
	 mbTel	NVARCHAR2
	 mbAddr	NVARCHAR2
	 mbBirth	 DATE
	 mbJoinDate	 DATE
	 mbGender	 NVARCHAR2
	*/
	private String mbId;
	private int mbType;
	private String mbName;
	private String mbPwd;
	private String mbNickName;
	private String mbTel;
	private String mbAddr;
	private String mbBirth;
	private String mbJoinDate;
	private String mbGender;
	private int[] cate1No;		
	private int[] cate2No;
}
