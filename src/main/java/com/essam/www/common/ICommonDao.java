package com.essam.www.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.essam.www.bean.ClassBean;

public interface ICommonDao {
	// common-mapper.xml 사용

	List<ClassBean> getSearchList(@Param("pageNo") Integer pageNo, @Param("cate1No") Integer cate1No,
			@Param("cate2No") Integer cate2No, @Param("keyword") String keyword);
}
