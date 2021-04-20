package com.essam.www.eclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.ClassBean;

@Service
public class ClassMM {
	@Autowired
	private IClassDao cDao;	
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
//	출석 현황 이동	
//	출석현황 가져오기	
//	공지사항 수정, 등록	
//	공지사항 삭제	
//	학생목록 이동하기	
//	학생목록 가져오기	
//	학생 정보보기 이동	
//	학생정보 가져오기	
//	공지사항 목록 페이지 이동	
//	공지사항 목록 가져오기	
//	공지사항 상세 페이지 이동	
//	공지사항 상세 가져오기	
	
	// 클래스 등록, 수정하기 페이지 이동(황정혜)
		public ModelAndView goClassInfoWrite(String clsNo) {
			ModelAndView mav = new ModelAndView();
			ClassBean cb = new ClassBean();
			if(clsNo!=null) {
				cb = cDao.getMyClassList(clsNo);
				mav.addObject("cb",cb);
			}
			mav.setViewName("class/class_write"); // .jsp
			return mav;
		}
	// 클래스 등록, 수정하기(황정혜)		
		
}
