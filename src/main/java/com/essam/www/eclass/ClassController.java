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
	
	//**********고연미**********//
	// (CM01)클래스 소개 이동(관계자용)
	// (CM02+CM03)게시판(공지사항/과제) 목록 페이지 이동 + 게시판 목록 가져오기	
	// (CM04)게시판 목록 내림차순 정렬
	// (CM05)게시판 목록 페이징
	// (CM06+CM07)게시판(공지사항/과제) 상세 페이지 이동 + 과제 상세 가져오기
	// (CM08)조회수 추가
	// (CM09)수정/삭제 버튼 생성
	// (CM10)게시판(공지사항/과제) 등록/수정 페이지 이동
	// (CM11)게시판(공지사항/과제) 등록/수정
	// (CM12)(수정시)Ajax 첨부파일 리스트 가져오기
	// (CM13)(수정시)Ajax 첨부파일 삭제
	// (CM14)게시글(공지사항/과제) 삭제
	// (CM15)게시글 댓글 목록 삭제
	
	//**********임다영**********//
	// (CM16)댓글 목록 가져오기(ajax)	
	// (CM17)댓글 등록(ajax)	
	// (CM18)댓글 수정(ajax)	
	// (CM19)댓글 삭제(ajax)	
	
	
	
	// (CM20+CM21)출석 현황 이동 + 출석현황 가져오기
	@RequestMapping(value = "/class/attend")
	ModelAndView goAttend(HttpServletRequest request, String clsNo){
		ModelAndView mav = cm.goAttend(request, clsNo);
		return mav;
		}
	
	
	// (CM22+CM23)학생목록 이동 + 학생목록 가져오기	
	@RequestMapping(value = "/class/studentlist")
	ModelAndView goStudentList(String clsNo) {
		ModelAndView mav = cm.goStudentList(clsNo);
		return mav;
		}
		
	// (CM24+CM25)학생정보보기 이동 + 학생정보 가져오기	
	@RequestMapping(value = "/class/studentinfo")
	ModelAndView goStudentInfo(String mbId, String clsNo){
		ModelAndView mav = cm.goStudentInfo(mbId, clsNo); 
		return mav;
		}	
	
	// (CM26)클래스 등록, 수정 페이지 이동
	@RequestMapping(value = "/class/classinfo/write")
	ModelAndView goClassInfoWrite(String clsNo) {
		ModelAndView mav = cm.goClassInfoWrite(clsNo);
		return mav;
		}
	// (CM27)클래스 등록, 수정하기	
	@PostMapping(value = "/class/classinfo/update")
	ModelAndView classClassinfoUpdate(MultipartHttpServletRequest mReq, HttpServletRequest request, ClassBean cb) {
		ModelAndView mav = cm.classClassinfoUpdate(mReq, request, cb);
		return mav;
		}
}
