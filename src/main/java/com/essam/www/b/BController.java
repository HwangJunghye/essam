package com.essam.www.b;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BController {
	@Autowired
	private BMM bm;
	
	/**
	 * 클래스소개 이동
	 */
	@RequestMapping(value = "/classinfo")
	ModelAndView goClassInfo(String clsNo) {
		return bm.goClassInfo(clsNo);
	}
}
