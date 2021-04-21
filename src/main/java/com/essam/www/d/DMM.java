package com.essam.www.d;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.MemberBean;

@Service
public class DMM {
	@Autowired
	private IDDao DDao;
	
	//계정관리 이동하기+회원정보 가져오기
	public ModelAndView goMypage(String mbId) {
		ModelAndView mav = new ModelAndView();
		MemberBean mb = DDao.getMemberInfo(mbId);
		
		return mav;
	}	
//	회원정보 수정 실행

	public ModelAndView memberUpdate() {
		MemberBean mb = new MemberBean();
		ModelAndView mav = new ModelAndView();
		mav.addObject(mav);
		mav  = DDao.memberUpdate(mb);
		return mav;
	}

}
