package com.essam.www.a;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.ClassBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.StudentBean;

@Service
public class AMM {
	@Autowired
	private IADao cDao;

	// 출석 현황 이동 + 출석현황 가져오기
	public ModelAndView goAttend(String mbId) {
		ModelAndView mav = new ModelAndView();
		
		List<StudentBean> attendInfo = null;
		attendInfo = cDao.getAttend();
		// 가져온 정보를 mav에 넣기
		mav.addObject("attendInfo",attendInfo);	
		//class_attend.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("class/class_attend"); // .jsp
		return mav;
	}
	

}
