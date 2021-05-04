package com.essam.www.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.essam.www.bean.ClassBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.TeacherBean;

public interface IMemberDao {
	// member-mapper 사용
	boolean memberJoin(MemberBean mb);

	MemberBean getMemberInfo(String mbId);

	boolean checkEmail(String mbId);

	List<ClassBean> getMyClassList(@Param("mbid") String mbId, @Param("mbtype") int mbType);

	void putInterCate (@Param("cateno") int cateno, @Param("tablenm") String tablenm, @Param("mbid") String mbid);

	void putTeacher(String mbId);
	
	boolean changePassword(@Param("mbId") String mbId, @Param("mbPwd") String mbPwd);
	
	TeacherBean getTeacherProfile(String mbId);

	boolean teacherProfileUpdate(TeacherBean tb);

	boolean teacherInfoDelete(String mbId);

	
}
