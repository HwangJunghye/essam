package com.essam.www.d;

import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.MemberBean;

public interface IDDao {

	MemberBean getMemberInfo(String mbId);

	ModelAndView memberUpdate(MemberBean mb);
	
}
