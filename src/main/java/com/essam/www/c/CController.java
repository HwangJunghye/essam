package com.essam.www.c;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.MemberBean;
import com.essam.www.bean.TeacherBean;
import com.essam.www.exception.CommonException;
import com.essam.www.file.FileMM;

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
	ModelAndView goTeacherProfile(HttpSession session, RedirectAttributes rattr) throws CommonException {
		ModelAndView mav = mm.getTeacherProfile(session, rattr);
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
	
	/**
	 * 교사프로필 등록, 수정
	 * @param mReq
	 * @return
	 */
	@PostMapping(value = "/teacher_profile/update")
	ModelAndView teacherProfileUpdate(MultipartHttpServletRequest mReq, TeacherBean tb) {
		ModelAndView mav = mm.teacherProfileUpdate(mReq, tb);
		
		return mav;
	}
	
	
	//교사프로필 삭제하기
	@RequestMapping(value = "/teacher_profile/delete")
	ModelAndView TeacherProfileDelete(HttpSession session, RedirectAttributes rattr) throws CommonException {
		ModelAndView mav = mm.getTeacherProfile(session, rattr);
		return mav; 
	}
	
}
