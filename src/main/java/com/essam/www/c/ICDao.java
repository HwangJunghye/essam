package com.essam.www.c;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.essam.www.bean.CurriculumBean;
import com.essam.www.bean.TeacherBean;

public interface ICDao {
	// c-mapper 사용
	TeacherBean getTeacherProfile(String mbId);

	boolean teacherProfileUpdate(TeacherBean tb);

	boolean teacherInfoDelete(String mbId);

	List<CurriculumBean> getCurriculumList(@Param("clsNo") String clsNo, @Param("pageNum") Integer pageNum);

	CurriculumBean getCurriculumRead(@Param("clsNo")String clsNo, @Param("curNo")String curNo);

	boolean classCurriculumAdd(CurriculumBean cb);

	boolean curFileInsert(@Param("curNo") String curNo,@Param("fileNo") String fileNo);

	String getRegiNo(@Param("clsNo") String clsNo, @Param("mbId") String mbId);

	boolean isCurriTime(String curNo);

	boolean isAttendAlready(@Param("curNo") String curNo, @Param("regiNo") String regiNo);

	void addAttend(@Param("curNo") String curNo, @Param("regiNo") String regiNo);

	String getZoomLink(String clsNo);

	boolean classCurriculumUpdateServer(CurriculumBean cb);

	boolean classCurriculumUpdateServerSil(CurriculumBean cb);

	int getCurriculumCount(String clsNo);

}
