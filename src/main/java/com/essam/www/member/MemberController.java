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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.MemberBean;
import com.essam.www.bean.TeacherBean;
import com.essam.www.exception.CommonException;

@Controller
public class MemberController {
	@Autowired
	private MemberMM mm;
	//**********고연미**********//
	// (MM07)수강신청 실행
	//**********임다영**********//
	// (MM09)계정관리 이동	
	// (MM10)회원정보 수정 실행	
	// (MM11)회원정보 가져오기   


	// (MM01)로그인 이동
	@RequestMapping(value = "/login")
	ModelAndView goLogin() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("navtext", "로그인");
		mav.setViewName("member/login");
		return mav; // .jsp
	}
	
	// (MM02)회원가입 이동
	@RequestMapping(value = "/join")
	ModelAndView goJoin() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("navtext", "회원가입");
		mav.setViewName("member/join");
		return mav; // .jsp
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
	
	// (MM08)비밀번호 변경 실행(ajax)
	@PostMapping(value = "/changepassword")
	@ResponseBody Map<String,String> changePassword(HttpSession session, String mbPwd, String newMbPwd){
		return mm.changePassword(session,mbPwd,newMbPwd);
	}
	
	
	
	
	// (MM12+MM13)교사프로필 이동 + 교사프로필 가져오기
	/**
	 * 교사프로필 이동 + 교사프로필 가져오기 goTeacherProfile()
	 * @param session
	 * @return ModelAndView
	 * @throws CommonException
	 */
	@RequestMapping(value = "/teacher_profile")
	ModelAndView goTeacherProfile(HttpSession session) throws CommonException {
		ModelAndView mav = mm.getTeacherProfile(session);
		mav.addObject("navtext", "교사프로필");
		return mav; 
	}
	
	// (MM14)교사프로필 동록, 수정 이동
	/**
	 * 교사프로필 등록, 수정 이동하기 goTeacherProfileWrite()
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/teacher_profile/write")
	ModelAndView goTeacherProfileWrite(HttpSession session) {
		ModelAndView mav = mm.getTeacherProfileWrite(session);
		mav.addObject("navtext", "교사프로필 등록/수정");
		return mav; //.jsp
	}
	
	// (MM15)교사프로필 등록,수정
	/**
	 * 교사프로필 등록, 수정 teacherProfileUpdate()
	 * @param mReq
	 * @return ModelAndView
	 */
	@PostMapping(value = "/teacher_profile/update")
	ModelAndView teacherProfileUpdate(MultipartHttpServletRequest mReq, TeacherBean tb, HttpServletRequest request) {
		ModelAndView mav = mm.teacherProfileUpdate(mReq, tb, request);
		return mav;
	}
	
	// (MM16)교사프로필 삭제하기
	/**
	 * 교사프로필 삭제하기 teacherProfileDelete
	 * @param HttpSession
	 * @param HttpServletRequest
	 * @return ModelAndView
	 * @throws CommonException
	 */
	@RequestMapping(value = "/teacher_profile/delete")
	ModelAndView teacherProfileDelete(HttpSession session, HttpServletRequest request) throws CommonException {
		ModelAndView mav = mm.teacherProfileDelete(session, request);
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
