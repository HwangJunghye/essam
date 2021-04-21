package com.essam.www.b;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.MemberBean;

@Controller
public class BController {
	@Autowired
	private BMM bm;
	
	/**
	 * 클래스소개 이동
	 */
	@RequestMapping(value = "/classinfo")
	ModelAndView goClassInfo(String clsNo) {
		return bm.goClassInfo(clsNo);
	}
	/**
	 * 수강신청
	 */
	@RequestMapping(value = "/classjoin")
	ModelAndView classJoin(String clsNo, HttpSession session, RedirectAttributes rattr) {
		MemberBean loginData = (MemberBean)session.getAttribute("loginData");
		String mbId= loginData.getMbId();
		return bm.classJoin(clsNo, mbId, rattr);
	}
	/**
	 * 게시판 리스트
	 */
	@RequestMapping(value = "/class/boardlist")
	ModelAndView boardList(String clsNo, int clsBrdType) {
		return bm.goBoardList(clsNo, clsBrdType);		
	}
	/**
	 * 게시판 글쓰기/수정
	 */
	@RequestMapping(value = "/class/boardWrite")
	ModelAndView boardWrite(String clsNo, int clsBrdType, String clsBrdNo, HttpSession session, RedirectAttributes rattr) {
		MemberBean loginData = (MemberBean)session.getAttribute("loginData");
		String mbId= loginData.getMbId();
		return bm.goBoardWrite(clsNo, clsBrdType, clsBrdNo, mbId, rattr);		
	}
}
