package com.essam.www.d;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.MemberBean;

@Controller
public class DController {
	@Autowired
	private DMM dm;
	
	//계정관리 이동하기+회원정보 가져오기
	@RequestMapping(value = " /mypage")
	ModelAndView goMypage(String mbId) {
		ModelAndView mav = dm.goMypage(mbId);
		return mav;
	}	
	
//	회원정보 수정 실행
	
	@RequestMapping(value = "/memberupdate")
	String memberUpdate(MemberBean mb) {
		ModelAndView memberUpdate = dm.memberUpdate();
		
		return "redirect:/mypage";
	}
}		








