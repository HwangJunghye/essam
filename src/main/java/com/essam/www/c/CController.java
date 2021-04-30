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
	 * @throws CommonException
	 */
	@RequestMapping(value = "/teacher_profile")
	ModelAndView goTeacherProfile(HttpSession session) throws CommonException {
		ModelAndView mav = mm.getTeacherProfile(session);
		mav.addObject("navtext", "교사프로필");
		return mav; 
	}
	
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
	



//커리큘럼------------------------------------------------------------------

//클래스 커리큘럼 이동 + 커리큘럼 목록 가져오기
	@RequestMapping(value = "/class/curriculum")
	ModelAndView goClassCurriculum(String clsNo) {
		ModelAndView mav = mm.getClassCurriculumLIst(clsNo);
		mav.addObject("navtext", "커리큘럼");
		return mav; 
	}



//커리큘럼 상세정보 보기 이동
//커리큘럼 상세정보 가져오기
//동영상 페이지 이동
//동영상 제목,시작일,종료일 가져오기
//커리큘럼 등록 이동
//커리큘럼 등록
//커리큘럼 수정 이동
//커리큘럼 수정



}

























