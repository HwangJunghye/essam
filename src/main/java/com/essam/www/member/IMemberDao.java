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

	//@Author 고연미
	int hasClassJoin(@Param("clsNo") String clsNo, @Param("mbId") String mbId);
	
	boolean classJoin(@Param("clsNo") String clsNo, @Param("mbId") String mbId);

	boolean memberUpdate(MemberBean mb);

	void deleteInterCate(@Param("mbId") String mbId, @Param ("tablenm")String tablenm);

	int[] getinterCate(@Param("mbId") String mbId, @Param("tablenm")String tablenm);

	int getLoginMb(String mbId);

	int getLoginMbS(String socketId);

	boolean updateLoginMb(@Param("mbId") String mbId, @Param("socketId") String socketId);
	
	boolean setLoginMb(@Param("mbId") String mbId, @Param("socketId") String socketId);

	boolean delLoginMb(String socketId);
	
}
