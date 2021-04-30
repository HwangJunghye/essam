package com.essam.www.c;

import java.util.List;

import com.essam.www.bean.CurriculumBean;
import com.essam.www.bean.TeacherBean;

public interface ICDao {
	// c-mapper 사용
	TeacherBean getTeacherProfile(String mbId);

	boolean teacherProfileUpdate(TeacherBean tb);

	boolean teacherInfoDelete(String mbId);

	List<CurriculumBean> getCurriculumList(String clsNo);

	CurriculumBean getCurriculumRead(String clsNo, String curNo);

}
