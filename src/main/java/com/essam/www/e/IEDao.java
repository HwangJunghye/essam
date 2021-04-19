package com.essam.www.e;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.essam.www.bean.ClassBean;

public interface IEDao {

	List<ClassBean> getSearchList(
			@Param("pageNo") Integer pageNo, 
			@Param("cate1No") Integer cate1No,
			@Param("cate2No") Integer cate2No, 
			@Param("keyword") String keyword);

}
