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
	private String curNo; //커리큘럼일련번호
	private String regiNo; //수강신청일련번호
	private int curTypeNo; //커리큘럼타입번호
	private String curEndDate; //커리큘럼 종료일시
	 
}
