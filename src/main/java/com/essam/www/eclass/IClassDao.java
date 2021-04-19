package com.essam.www.eclass;

import com.essam.www.bean.ClassBean;

public interface IClassDao {
	// class-mapper.xml 사용
	ClassBean getMyClassList(String clsNo);
	
}
