package com.essam.www.c;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.MemberBean;

@Controller
public class CController {
	@Autowired
	private CMM mm;
	
	/**
	 * 교사프로필 이동 + 교사프로필 가져오기 goTeacherProfile()
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/teacher_profile")
	ModelAndView goTeacherProfile(HttpSession session, HttpServletRequest request) {
		ModelAndView mav = mm.getTeacherProfile(session, request);
		return mav; 
	}
	
	/**
	 * 교사프로필 등록, 수정 이동하기 goTeacherProfileWrite()
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/teacher_profile/write")
	String goTeacherProfileWrite(HttpSession session) {
		//ModelAndView mav = mm.getTeacherProfile(session);
		return "member/teacher_profile_write"; //.jsp
	}
	
	//교사프로필 등록, 수정
	
	
	//교사프로필 삭제하기
	
	
}
