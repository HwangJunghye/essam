package com.essam.www.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.experimental.Accessors;

@Alias("teacherbean")
@Data
@Accessors(chain = true)
public class TeacherBean {
	/*
	 mbid	 nvarchar2
	 fileNo	 nvarchar2
	 teacherGrade	 number
	 teacherIntro	 nvarchar2
	 teacherDetail	 nclob
	*/

	private String mbId;
	private String fileNo;	
	private int teacherGrade;
	private String teacherIntro;
	private String teacherDetail;
	private String mbNickName;
}
