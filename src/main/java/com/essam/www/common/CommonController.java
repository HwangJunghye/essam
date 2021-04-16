package com.essam.www.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController {
	@Autowired
	private CommonMM comm;
//	메인페이지로 이동
	@RequestMapping(value = "/")
	ModelAndView goIndex(HttpServletRequest request) {
		return comm.goIndex(request); // .jsp
	}
//	new/hot 클래스목록 가져오기	
//	검색페이지 이동	
//	검색 결과 가져오기(ajax)	
//	클래스 소개 이동	
//	클래스정보가져오기	
}
