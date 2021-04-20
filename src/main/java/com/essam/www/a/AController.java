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
	
	// 출석 현황 이동 + 출석현황 가져오기
	@RequestMapping(value = "/class/attend")
	ModelAndView goAttend(String mbId){
		ModelAndView mav = cm.goAttend(mbId);
		return mav;
		}	
	
}
