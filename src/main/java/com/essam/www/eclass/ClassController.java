package com.essam.www.eclass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.ClassBean;

@Controller
public class ClassController {
	@Autowired
	private ClassMM cm;
	
	// (CM01)공지사항 쓰기, 수정페이지 이동	
	// (CM02)클래스 소개 이동(관계자용)	
	// (CM03+CM04)과제 목록 페이지 이동 + 과제 목록 가져오기		
	// (CM05+CM06)과제 상세 페이지 이동 + 과제 상세 가져오기
	// (CM07)댓글 목록 가져오기(ajax)	
	// (CM08)댓글 등록(ajax)	
	// (CM09)댓글 수정(ajax)	
	// (CM10)댓글 삭제(ajax)	
	// (CM11)과제 수정,등록
	// (CM12)과제 삭제		
	// (CM11)공지사항 수정, 등록	
	// (CM12)공지사항 삭제
	// (CM19+CM04)공지사항 목록 페이지 이동 + 공지사항 목록 가져오기	
	// (CM20+CM06)공지사항 상세 페이지 이동 + 공지사항 상세 가져오기
	
	// (CM13+CM14)출석 현황 이동 + 출석현황 가져오기
	@RequestMapping(value = "/class/attend")
	ModelAndView goAttend(HttpServletRequest request, String clsNo){
		ModelAndView mav = cm.goAttend(request, clsNo);
		return mav;
		}
	
	
	// (CM15+CM16)학생목록 이동 + 학생목록 가져오기	
	@RequestMapping(value = "/class/studentlist")
	ModelAndView goStudentList(String clsNo) {
		ModelAndView mav = cm.goStudentList(clsNo);
		return mav;
		}
		
	// (CM17+CM18)학생정보보기 이동 + 학생정보 가져오기	
	@RequestMapping(value = "/class/studentinfo")
	ModelAndView goStudentInfo(String mbId, String clsNo){
		ModelAndView mav = cm.goStudentInfo(mbId, clsNo); 
		return mav;
		}	
	
	// (CM21)클래스 등록, 수정 페이지 이동
	@RequestMapping(value = "/class/classinfo/write")
	ModelAndView goClassInfoWrite(String clsNo) {
		ModelAndView mav = cm.goClassInfoWrite(clsNo);
		return mav;
		}
	// (CM22)클래스 등록, 수정하기	
	@PostMapping(value = "/class/classinfo/update")
	ModelAndView classClassinfoUpdate(MultipartHttpServletRequest mReq, HttpServletRequest request, ClassBean cb) {
		ModelAndView mav = cm.classClassinfoUpdate(mReq, request, cb);
		return mav;
		}
}
