package com.essam.www.a;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.ClassBean;
import com.essam.www.bean.MemberBean;

@Service
public class AMM {
	@Autowired
	private IADao mDao;
	
	/**
	 * 학생목록 페이지로 이동<br>
	 * 학생목록 가져오기 getStudentList()
	 */
	public ModelAndView goStudentList(String clsNo) {
		ModelAndView mav = new ModelAndView();
		List<MemberBean> mList = null;
		mList = mDao.getStudentList(clsNo);
		// 가져온 정보를 mav에 넣기
		mav.addObject("mList",mList);	
		// myclass_t.jsp로 이동하기 위해 viewname 지정
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
		mInfo = mDao.getStudentInfo(mbId);
		// 가져온 정보를 mav에 넣기
		mav.addObject("mInfo",mInfo);	
		// myclass_t.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("class/class_studentinfo_read"); // .jsp
		return mav;
	}


}
