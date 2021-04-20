package com.essam.www.eclass;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClassController {
	@Autowired
	private ClassMM cm;
	
//	공지사항 쓰기, 수정페이지 이동	
//	클래스 소개 이동(관계자용)	
//	과제 목록 페이지 이동	
//	과제 목록 가져오기	
//	과제 상세 페이지 이동	
//	과제 상세 가져오기	
//	댓글 목록 가져오기(ajax)	
//	댓글 등록(ajax)	
//	댓글 수정(ajax)	
//	댓글 삭제(ajax)	
//	과제 수정,등록	
//	과제 삭제		
//	공지사항 수정, 등록	
//	공지사항 삭제
//	공지사항 목록 페이지 이동	
//	공지사항 목록 가져오기	
//	공지사항 상세 페이지 이동	
//	공지사항 상세 가져오기	
	
	// 출석 현황 이동 + 출석현황 가져오기
	@RequestMapping(value = "/class/attend")
	ModelAndView goAttend(String mbId){
		ModelAndView mav = cm.goAttend(mbId);
		return mav;
		}	
	
	// 학생목록 이동 + 학생목록 가져오기	
	@RequestMapping(value = "/class/studentlist")
	ModelAndView goStudentList(String clsNo) {
		ModelAndView mav = cm.goStudentList(clsNo);
		return mav;
		}
		
	// 학생정보보기 이동 + 학생정보 가져오기	
	@RequestMapping(value = "/class/studentinfo")
	ModelAndView goStudentInfo(String mbId){
		ModelAndView mav = cm.goStudentInfo(mbId);
		return mav;
		}	
	
	// 클래스 등록, 수정 페이지 이동
	@RequestMapping(value = "/class/classinfo/write")
	ModelAndView goClassInfoWrite(String clsNo) {
		ModelAndView mav = cm.goClassInfoWrite(clsNo);
		return mav;
		}
	
//	// 클래스 등록, 수정하기	
//	@RequestMapping(value = "/class/classinfo/update")
//	ModelAndView classClassinfoUpdate(HttpSession session) {
//		ModelAndView mav = cm.classClassinfoUpdate(session);
//		return mav;
//		}
	
}
