package com.essam.www.eclass;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.AttendBean;
import com.essam.www.bean.ClassBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.StudentBean;

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
//	공지사항 수정, 등록	
//	공지사항 삭제
//	공지사항 목록 페이지 이동	
//	공지사항 목록 가져오기	
//	공지사항 상세 페이지 이동	
//	공지사항 상세 가져오기	
	
		// 출석 현황 이동 + 출석현황 가져오기
		public ModelAndView goAttend(String mbId) {
			ModelAndView mav = new ModelAndView();
			AttendBean attendInfo = new AttendBean();
			attendInfo = cDao.getAttend();
			// 가져온 정보를 mav에 넣기
			mav.addObject("attendInfo",attendInfo);	
			//class_attend.jsp로 이동하기 위해 viewname 지정
			mav.setViewName("class/class_attend"); // .jsp
			return mav;
		}
	
		// 클래스 등록, 수정하기 페이지 이동
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
		// 클래스 등록, 수정하기
		
		
		/**
		 * 학생목록 페이지로 이동<br>
		 * 학생목록 가져오기 getStudentList()
		 */
		public ModelAndView goStudentList(String clsNo) {
			ModelAndView mav = new ModelAndView();
			List<StudentBean> sList = null;
			sList = cDao.getStudentList(clsNo);
			// 가져온 정보를 mav에 넣기
			mav.addObject("sList",sList);	
			// class_studentinfo.jsp로 이동하기 위해 viewname 지정
			mav.setViewName("class/class_studentinfo"); // .jsp
			return mav;
		}
		
		/**
		 * 학생정보보기 페이지로 이동<br>
		 * 학생정보 가져오기 getStudentInfo()
		 */
		public ModelAndView goStudentInfo(String mbId) {
			ModelAndView mav = new ModelAndView();
			MemberBean mInfo = new MemberBean();
			mInfo = cDao.getStudentInfo(mbId);
			// 가져온 정보를 mav에 넣기
			mav.addObject("mInfo",mInfo);	
			// class_studentinfo_read.jsp로 이동하기 위해 viewname 지정
			mav.setViewName("class/class_studentinfo_read"); // .jsp
			return mav;
		}

}
