package com.essam.www.b;

import java.util.List;

import com.essam.www.bean.ClassBean;

public interface IBDao {

	List<ClassBean> getClassListNew();

	List<ClassBean> getClassListMy(String mbId);

	ClassBean getClassInfo(String clsNo);

}
