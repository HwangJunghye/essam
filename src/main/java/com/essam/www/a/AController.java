package com.essam.www.a;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.eclass.ClassMM;

@Controller
public class AController {
	@Autowired
	private AMM cm;
	
	
	// 학생목록 이동 + 학생목록 가져오기	
	@RequestMapping(value = "/class/studentlist")
	ModelAndView goStudentList(String clsNo) {
		ModelAndView mav = cm.goStudentList(clsNo);
		return mav;
	}
	
	// 학생정보보기 이동 + 학생정보 가져오기	
	@RequestMapping(value = "/class/studentinfo")
	ModelAndView goStudentInfo(String mbId){
		ModelAndView mav = cm.goStudentInfo(mbId);
		return mav;
	}	
}
