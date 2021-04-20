package com.essam.www.a;

import java.util.List;

import com.essam.www.bean.MemberBean;

public interface IADao {

	List<MemberBean> getStudentList(String clsNo);
	//SELECT * FROM STUDENT WHERE 

	MemberBean getStudentInfo(String mbId);
	
}
