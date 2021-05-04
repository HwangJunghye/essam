package com.essam.www.b;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.essam.www.bean.BoardBean;
import com.essam.www.bean.FileBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.exception.CommonException;
import com.essam.www.file.FileMM;

@Controller
public class BController {
	
	@Autowired
	private BMM bm;
	@Autowired
	private FileMM fm;
	@Autowired
	private IBDao bDao;
	
	/**
	 * [CO05] 클래스소개 이동
	 * @Author 고연미 on 28/04/2021
	 */
	@RequestMapping(value = "/classinfo")
	ModelAndView goClassInfo(String clsNo, HttpServletRequest request) {
		return bm.goClassInfo(clsNo, request);
	}
	/**
	 * [CM01] 클래스소개 이동 (관계자용)
	 * @Author 고연미 on 28/04/2021
	 */
	/**
	 * [MM07] 수강신청
	 * @Author 고연미 on 28/04/2021
	 */
	@RequestMapping(value = "/classjoin")
	ModelAndView classJoin(String clsNo, HttpSession session, RedirectAttributes rattr) {
		return bm.classJoin(clsNo, session, rattr);
	}
	/**
	 * [CM02] 게시판 리스트 이동
	 * @Author 고연미 on 28/04/2021
	 */
	/**
	 * [CM10] 게시판 글쓰기/수정 이동
	 * @Author 고연미 on 28/04/2021
	 */
	/**
	 * [CM11] 게시판 글쓰기/수정
	 * @Author 고연미 on 28/04/2021
	 */
	/**
	 * [CM06] 게시판 글읽기
	 * @Author 고연미 on 28/04/2021
	 */
	/**
	 * [CM14] 게시판 글 삭제
	 * @Author 고연미 on 28/04/2021
	 */
	/**
	 * [CM12] (ajax) 첨부파일 리스트 가져오기
	 * @Author 고연미 on 28/04/2021
	 */
	/**
	 * [CM13] (ajax) 첨부파일 삭제
	 * @Author 고연미 on 28/04/2021
	 */
}
