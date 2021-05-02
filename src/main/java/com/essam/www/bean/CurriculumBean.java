package com.essam.www.bean;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.experimental.Accessors;

@Alias("curriculumbean")
@Data
@Accessors(chain = true)
public class CurriculumBean {

	private String curNo;           //커리큘럼 일련번호 NVARCHAR2
	private String clsNo;           //클래스 일련번호 NVARCHAR2
	private int curTypeNo;          //커리큘럼 타입번호 NUMBER
	private String fileNo;          //동영상파일 일련번호 NVARCHAR2
	private String curTitle;        //커리큘럼 제목 NVARCHAR2
	private String curStartDate;    //시작일시 DATE
	private String curEndDate;      //종료일시 DATE
	private String curDisc;         //커리큘럼 설명 NCLOB
	private int curIsCancel;        //휴강처리여부 NUMBER
	private String curCancelReason; //휴강사유 NCOLB
	private String clsName;         //클래스명 NVARCHAR 2
}
