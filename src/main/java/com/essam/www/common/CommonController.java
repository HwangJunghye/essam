package com.essam.www.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
	@Autowired
	private CommonMM comm;
//	메인페이지로 이동
	@RequestMapping(value = "/")
	String goIndex() {
		return "home"; // .jsp
	}
//	new/hot 클래스목록 가져오기	
//	검색페이지 이동	
//	검색 결과 가져오기(ajax)	
//	클래스 소개 이동	
//	클래스정보가져오기	
}
