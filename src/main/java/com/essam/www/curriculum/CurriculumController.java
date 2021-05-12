package com.essam.www.curriculum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.CurriculumBean;
import com.essam.www.exception.CommonException;

@Controller
public class CurriculumController {
	@Autowired
	private CurriculumMM crmm;

	// (CR01+CR02)클래스 커리큘럼 이동 + 커리큘럼 목록 가져오기
	@RequestMapping(value = "/class/curriculum")
	ModelAndView goClassCurriculum(String clsNo, Integer pageNum, HttpServletRequest request) {
		ModelAndView mav = crmm.getClassCurriculumList(clsNo, pageNum, request);
		return mav; 
	}
	// (CR03+CR04)커리큘럼 상세보기 이동 + 커리큘럼 상세가져오기
	@RequestMapping(value = "/class/curriculum/read")
	ModelAndView goClassCurriculumRead(String clsNo, String curNo, Integer pageNum) {
		ModelAndView mav = crmm.getClassCurriculumRead(clsNo, curNo, pageNum);
		return mav; 
	}
	// (CR05+CR06)동영상 페이지 이동 + 동영상 제목, 시작일, 종료일 가져오기	
	
	// (CR07)커리큘럼 등록 이동	
	@RequestMapping(value = "/class/curriculum/write")
	ModelAndView goClassCurriculumWrite(String clsNo, Integer pageNum) {
		ModelAndView mav = crmm.goClassCurriculumWrite(clsNo, pageNum);
		return mav; //.jsp
	}
	// (CR08)커리큘럼 등록하기
	@RequestMapping(value = "/class/curriculum/add")
	ModelAndView classCurriculumAdd(MultipartHttpServletRequest mReq, CurriculumBean cb, Integer pageNum, HttpServletRequest request, RedirectAttributes rattr) throws CommonException {
		ModelAndView mav = crmm.ClassCurriculumAdd(mReq, cb, request, rattr, pageNum);
		return mav; //.jsp
	}
	// (CR09)커리큘럼 수정 이동
	@RequestMapping(value = "/class/curriculum/update")
	ModelAndView goClassCurriculumUpdate(String clsNo, String curNo, Integer pageNum) {
		ModelAndView mav = crmm.goClassCurriculumUpdate(clsNo, curNo, pageNum);
		return mav; //.jsp
	}
	// (CR10)커리큘럼 수정하기
	@RequestMapping(value = "/class/curriculum/update_server")
	ModelAndView classCurriculumUpdateServer(MultipartHttpServletRequest mReq, CurriculumBean cb, HttpServletRequest request, RedirectAttributes rattr) throws CommonException {
		ModelAndView mav = crmm.ClassCurriculumUpdateServer(mReq, cb, request, rattr);
		return mav; //.jsp
	}
	// (CR11)커리큘럼 삭제하기
	@RequestMapping(value = "/class/curriculum/delete")
	ModelAndView classCurriculumDelete(String clsNo, String curNo, HttpServletRequest request, RedirectAttributes rattr) throws CommonException {
		ModelAndView mav = crmm.classCurriculumDelete(clsNo, curNo, request, rattr);
		return mav; 
	}
	//동영상 페이지 이동/참여 + 동영상 제목,시작일,종료일 가져오기
	@RequestMapping(value = "/class/videoplay")
	ModelAndView goClassVideoPlay(String clsNo, String curNo, HttpSession session, Integer pageNum) {
		ModelAndView mav = crmm.goClassVideoPlay(clsNo, curNo, session, pageNum);
		return mav; //.jsp
	}
	//줌링크 페이지 이동
	@RequestMapping(value = "/class/zoomlink")
	ModelAndView goClassZoomLink(String clsNo, String curNo, HttpSession session) {
		ModelAndView mav = crmm.goClassZoomLink(clsNo, curNo, session);
		return mav; //.jsp
	}
	
}
