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

import com.essam.www.bean.CurriculumBean;
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
	@RequestMapping(value = "/teacher_profile1")
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
	@RequestMapping(value = "/teacher_profile/write1")
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
	@PostMapping(value = "/teacher_profile/update1")
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
	@RequestMapping(value = "/teacher_profile/delete1")
	ModelAndView teacherProfileDelete(HttpSession session, HttpServletRequest request) throws CommonException {
		ModelAndView mav = mm.teacherProfileDelete(session, request);
		return mav; 
	}
	



//커리큘럼------------------------------------------------------------------

	//클래스 커리큘럼 이동 + 커리큘럼 목록 가져오기
	@RequestMapping(value = "/class/curriculum")
	ModelAndView goClassCurriculum(String clsNo, Integer pageNum, HttpServletRequest request) {
		ModelAndView mav = mm.getClassCurriculumList(clsNo, pageNum, request);
		return mav; 
	}

	//커리큘럼 상세정보 보기 이동 + 커리큘럼 상세정보 가져오기
	@RequestMapping(value = "/class/curriculum/read")
	ModelAndView goClassCurriculumRead(String clsNo, String curNo, Integer pageNum) {
		ModelAndView mav = mm.getClassCurriculumRead(clsNo, curNo, pageNum);
		return mav; 
	}
	
	//커리큘럼 등록 이동하기 
	@RequestMapping(value = "/class/curriculum/write")
	ModelAndView goClassCurriculumWrite(String clsNo, Integer pageNum) {
		ModelAndView mav = mm.goClassCurriculumWrite(clsNo, pageNum);
		return mav; //.jsp
	}
	
	//커리큘럼 등록
	@RequestMapping(value = "/class/curriculum/add")
	ModelAndView classCurriculumAdd(MultipartHttpServletRequest mReq, CurriculumBean cb, Integer pageNum, HttpServletRequest request, RedirectAttributes rattr) throws CommonException {
		ModelAndView mav = mm.ClassCurriculumAdd(mReq, cb, request, rattr, pageNum);
		return mav; //.jsp
	}
	
	//동영상 페이지 이동/참여 + 동영상 제목,시작일,종료일 가져오기
	@RequestMapping(value = "/class/videoplay")
	ModelAndView goClassVideoPlay(String clsNo, String curNo, HttpSession session, Integer pageNum) {
		ModelAndView mav = mm.goClassVideoPlay(clsNo, curNo, session, pageNum);
		return mav; //.jsp
	}
	
	//줌링크 페이지 이동
	@RequestMapping(value = "/class/zoomlink")
	ModelAndView goClassZoomLink(String clsNo, String curNo, HttpSession session) {
		ModelAndView mav = mm.goClassZoomLink(clsNo, curNo, session);
		return mav; //.jsp
	}
	
	//커리큘럼 수정 이동하기 
	@RequestMapping(value = "/class/curriculum/update")
	ModelAndView goClassCurriculumUpdate(String clsNo, String curNo, Integer pageNum) {
		ModelAndView mav = mm.goClassCurriculumUpdate(clsNo, curNo, pageNum);
		return mav; //.jsp
	}
	
	//커리큘럼 수정
	@RequestMapping(value = "/class/curriculum/update_server")
	ModelAndView classCurriculumUpdateServer(MultipartHttpServletRequest mReq, CurriculumBean cb, HttpServletRequest request, RedirectAttributes rattr) throws CommonException {
		ModelAndView mav = mm.ClassCurriculumUpdateServer(mReq, cb, request, rattr);
		return mav; //.jsp
	}
	
	
	//커리큘럼 삭제
	@RequestMapping(value = "/class/curriculum/delete")
	ModelAndView classCurriculumDelete(String clsNo, String curNo, HttpServletRequest request, RedirectAttributes rattr) throws CommonException {
		ModelAndView mav = mm.classCurriculumDelete(clsNo, curNo, request, rattr);
		return mav; 
	}
	
	
	
	

	
	








}

























