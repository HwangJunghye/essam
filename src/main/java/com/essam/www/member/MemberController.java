package com.essam.www.member;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.MemberBean;

@Controller
public class MemberController {
	@Autowired
	private MemberMM mm;
	
	// (MM07)수강신청 실행	
	// (MM08)비밀번호 변경 실행(ajax)	
	// (MM09)계정관리 이동	
	// (MM10)회원정보 수정 실행	
	// (MM11)회원정보 가져오기        	
	// (MM12+MM13)교사프로필 이동 + 교사프로필 가져오기
	// (MM14)교사프로필 동록, 수정 이동
	// (MM15)교사프로필 등록,수정
	// (MM16)교사프로필 삭제하기

	// (MM01)로그인 이동
	@RequestMapping(value = "/login")
	String goLogin() {
		return "member/login"; // .jsp
	}
	
	// (MM02)회원가입 이동
	@RequestMapping(value = "/join")
	String goJoin() {
		return "member/join"; // .jsp
	}

	// (MM03)이메일 중복체크(JSON 사용시 @ResponseBody 추가 해야 함)
	@RequestMapping(value = "/checkemail")
	@ResponseBody Map<String,String> checkEmail(String mbId){
		return mm.checkEmail(mbId);
	}

	// (MM04)로그아웃
	@PostMapping(value = "/logout")
	ModelAndView logout(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// 1: 기존의 세션 데이터를 모두 삭제
	    session.invalidate();
	    // 2: 로그인 페이지로 이동시킴.
	    mav.setViewName("redirect:/");
		return mav;
	}
	
	// (MM05)로그인 실행
	@PostMapping(value = "/access")
	ModelAndView access(MemberBean mb,HttpServletRequest request, RedirectAttributes rattr) {
		return mm.access(mb, request, rattr);
	}
	
	// (MM06)회원가입 실행
	@PostMapping(value = "/memberjoin")
	ModelAndView memberJoin(MemberBean mb, RedirectAttributes rattr) {
		// System.out.println(mb.getMbId());
		//System.out.println("회원타이ㅃ ====" + mb.getMbType());
		int[] cate1 = mb.getCate1No();
		//System.out.println("관심카테고리1 ===> "+  cate1[0]);
			
		ModelAndView mav = mm.memberJoin(mb, rattr);
		return mav;
	}
	
	// (MM17+MM19)클래스관리이동 + 내 클래스 목록 가져오기
	@RequestMapping(value = "/myclass_t")
	ModelAndView goMyclass_t(HttpSession session) {
		ModelAndView mav = mm.goMyclass_t(session);
		return mav;
	}

	// (MM18+MM19)마이클래스이동 + 내 클래스 목록 가져오기
	@RequestMapping(value = "/myclass_s")
	ModelAndView goMyclass_s(HttpSession session) {
		ModelAndView mav = mm.goMyclass_s(session);
		return mav;
	}

}
