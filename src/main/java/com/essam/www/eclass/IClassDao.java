package com.essam.www.eclass;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.essam.www.bean.ClassBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.StudentBean;

public interface IClassDao {
	// class-mapper.xml 사용
	ClassBean getMyClassList(String clsNo);
	
	List<StudentBean> getStudentList(String clsNo);

	StudentBean getStudentInfo(@Param("clsNo")String clsNo, @Param("mbId")String mbId);

	boolean classClassinfoUpdate(ClassBean cb);

	boolean classClassinfoInsert(ClassBean cb);

	String getFileNo(String clsNo);

}
