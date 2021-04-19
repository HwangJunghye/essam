package com.essam.www.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.b.BMM;
import com.essam.www.bean.ClassBean;

@Service
public class CommonMM {
	@Autowired
	private BMM bm;	//b service class:고연미
	@Autowired
	private ICommonDao coDao;
//	메인페이지로 이동	
//	new/hot 클래스목록 가져오기	
//	검색페이지 이동	
//	검색 결과 가져오기(ajax)	
//	클래스 소개 이동	
//	클래스정보가져오기	

	
	/**
	 * 인덱스페이지로 이동<br>
	 * 학생회원인 경우 마이클래스도 가져오기<br>
	 * 공통적으로 new,hot 클래스 정보 가져오기
	 * */
	public ModelAndView goIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		List<ClassBean> cList = null;
		
		// 세션에서 회원정보 가져오기
		// 학생회원인지 확인하기
		// 학생회원인 경우 마이클래스 목록 dao에 요청
		// new,hot 클래스 정보 가져오기
		// mav.addObject("newClass",클래스정보);

		// My 클래스 정보 가져오기 (학생 로그인인 경우)
		if (request.getSession().getAttribute("mbType") != null) {

			String mbType = request.getSession().getAttribute("mbType").toString();
			System.out.println("mbType === "+ mbType);
			if(mbType=="1") {
				cList = bm.getClassList("my", request.getSession().getAttribute("mbId").toString());
				// 가져온 정보를 mav "myList" 에 넣기
				mav.addObject("myList", cList);			
			}
		}
		
		// new 클래스 정보 가져오기
		cList = bm.getClassList("new","");
		// 가져온 정보를 mav "nList" 에 넣기
		mav.addObject("nList", cList);
		
		mav.addObject("msg","메세지 담기");
		
		// index.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("common/index"); // .jsp
		
		return mav;
	}
}
