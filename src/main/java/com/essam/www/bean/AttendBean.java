package com.essam.www.bean;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.experimental.Accessors;

@Alias("attendbean")
@Data
@Accessors(chain = true)
public class AttendBean {
	
	private String mbId; //아이디
	private String mbNickName; //닉네임
	private String clsName; //클래스이름
	private String regiNo; //수강신청일련번호
	private int attendDay; //출석일수
	private int absenceDay; //결석일수
	 
}
