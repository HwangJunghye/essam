package com.essam.www.c;

import java.util.List;

import com.essam.www.bean.TeacherBean;

public interface ICDao {
	// c-mapper 사용
	List<TeacherBean> getTeacherProfile(String mbId);

}
