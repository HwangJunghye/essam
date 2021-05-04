package com.essam.www.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.essam.www.bean.ClassBean;

public interface ICommonDao {
	// common-mapper.xml 사용

	//@Author 고연미
	List<ClassBean> getClassListNew();
	
	List<ClassBean> getClassListHot(); 

	List<ClassBean> getClassListMy(String mbId);

	ClassBean getClassInfo(String clsNo);
	
	boolean addClsView(@Param("clsNo") String clsNo, @Param("sessionId") String sessionId, @Param("mbId") String mbId);

	int getClsViewCnt(@Param("clsNo") String clsNo, @Param("sessionId") String sessionId);

	
	//@Author 박호근
	List<ClassBean> getSearchList(@Param("pageSize") Integer pageSize, @Param("pageNo") Integer pageNo, @Param("cate1No") Integer cate1No,
			@Param("cate2No") Integer cate2No, @Param("keywords") String[] keywords);
}
