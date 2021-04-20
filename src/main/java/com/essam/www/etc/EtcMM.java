package com.essam.www.etc;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.ClassBean;

@Service
public class EtcMM {
	@Autowired
	private IEtcDao eDao;

	// 관리자 페이지 이동	
	public ModelAndView goAdmin(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// 세션에서 mbType을 가져옴 --> 관리자 회원(mbType==3)인지 확인하기
		if(session.getAttribute("mbType").toString().equals("3")){ //관리자라면
			mav.setViewName("etc/admin"); // .jsp
		}else { //관리자가 아니라면 홈화면으로
			mav.setViewName("./"); // .jsp
		}
		return mav;
	}
	
	// 통계 가져오기(ajax)	
}
