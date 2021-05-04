package com.essam.www.d;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.MemberBean;
import com.essam.www.bean.ReplyBean;

@Controller
public class DController {
	@Autowired
	private DMM mm;
	
	//계정관리 이동하기+회원정보 가져오기
	@RequestMapping(value = "/mypage")
	ModelAndView goMypage(HttpSession session) {
		ModelAndView mav = mm.goMypage(session);
		return mav;
	}	
	
	//	회원정보 수정 실행
	@RequestMapping(value = "/memberupdate")
	ModelAndView memberUpdate(MemberBean mb, HttpSession session) {
		ModelAndView mav = mm.memberUpdate(mb, session);	
		return mav;
	}
}		








