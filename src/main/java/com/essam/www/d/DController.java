package com.essam.www.d;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.MemberBean;
import com.essam.www.member.MemberMM;

@Controller
public class DController {
	@Autowired
	private MemberMM mm;
	
	// 회원가입 이동
	@RequestMapping(value = "/join")
	String goJoin() {
		return "member/join"; // .jsp
	}
	
	// 회원가입 실행
	@PostMapping(value = "/memberjoin")
	ModelAndView memberJoin(MemberBean mb, RedirectAttributes rattr) {
		// System.out.println(mb.getMbId());
		// System.out.println(mb.getMbPwd());
		// System.out.println(mb.getMbBirth());
		ModelAndView mav = mm.memberJoin(mb, rattr);
		return mav;
	}

	// 로그인 이동
	@RequestMapping(value = "/login")
	String goLogin() {
		return "member/login"; // .jsp
	}

	// 로그인 실행
	@PostMapping(value = "/access")
	ModelAndView access(MemberBean mb,HttpServletRequest request, RedirectAttributes rattr) {
		return mm.access(mb, request, rattr);
	}
	
	// 이메일 중복체크(JSON 사용시 @ResponseBody 추가 해야 함)
	@RequestMapping(value = "/checkemail")
	@ResponseBody Map<String,String> checkEmail(String mbId){
		return mm.checkEmail(mbId);
	}
	
	
	
	
	
}
