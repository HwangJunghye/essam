package com.essam.www.b;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.essam.www.bean.BoardBean;
import com.essam.www.bean.ClassBean;
import com.essam.www.bean.FileBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.ReplyBean;
import com.essam.www.bean.TeacherBean;
import com.essam.www.c.ICDao;
import com.essam.www.constant.Constant;
import com.essam.www.d.IDDao;
import com.essam.www.eclass.Paging;
import com.essam.www.exception.CommonException;
import com.essam.www.file.FileMM;

@Service
public class BMM {
	private static final Logger logger = LoggerFactory.getLogger(BMM.class);
	
	@Autowired
	private IBDao bDao;
	@Autowired
	private FileMM fm;
	@Autowired
	private IDDao DDao;
	@Autowired
	private ICDao mDao;
		
	/**
	 * (메인) 클래스 리스트 가져오기
	 * @Author 고연미 on 28/04/2021
	 */
	public List<ClassBean> getClassList(String str, String mbId) {
		List<ClassBean> cList = null;
		switch(str) {
		case "new":
			cList = bDao.getClassListNew();
			break;
		case "hot":
			cList = bDao.getClassListHot();
			break;
		case "my":
			cList = bDao.getClassListMy(mbId);
			break;
		}
		return cList;
	}	
	/**
	 * 클래스소개 이동
	 * @Author 고연미 on 28/04/2021
	 */
	public ModelAndView goClassInfo(String clsNo, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String sessionId, mbId = null;
		
		//클래스번호가 없으면 메인으로 이동
		if(StringUtils.isEmpty(clsNo)) {
			mav.setViewName("redirect:/");			
		} else {
			//클래스정보 가져와 bean에 담기
			ClassBean cb = bDao.getClassInfo(clsNo);
			
			//가져온 클래스정보가 있으면 조회수 추가
			if(!ObjectUtils.isEmpty(cb)) {
				//loginData가 있으면
				if(!ObjectUtils.isEmpty(request.getSession().getAttribute("loginData"))) {
					//mbId 가져오기
					MemberBean loginData = (MemberBean)request.getSession().getAttribute("loginData");
					mbId = loginData.getMbId();
				} 
				sessionId = request.getSession().getId();
				//같은 sessionId와 clsNo가 등록된 데이터가 없으면
				if(bDao.getClsViewCnt(clsNo, sessionId) < 1) {
					//조회수 추가
					if(!bDao.addClsView(clsNo, sessionId, mbId))
						logger.info("클래스 조회수 추가 실패.");					
				}
			}
			mav.addObject("classInfo", cb);
			//mav에 네비타이틀 추가
			mav.addObject("navtext", "클래스 > "+ cb.getClsName());
			
			//강사 정보 가져와 mav에 담기 (MemberMM)
			TeacherBean tb = mDao.getTeacherProfile(cb.getMbId());
			mav.addObject("teacherInfo", tb);
			
			//커리큘럼 리스트 가져와 mav에 담기 (CurriculumMM)
			//List<CurriculumBean> crList = crDao.getCurriculumLIst(clsNo)
			//mav.addObject("curriList", crList);
			
			mav.setViewName("class/classinfo_main");			
		}
		return mav;
	}
	/**
	 * 수강신청
	 * @Author 고연미 on 28/04/2021
	 */
	@Transactional
	public ModelAndView classJoin(String clsNo, HttpSession session, RedirectAttributes rattr) {
		ModelAndView mav = new ModelAndView();
		//세션에서 mbId 가져오기
		MemberBean loginData = (MemberBean)session.getAttribute("loginData");
		String mbId= loginData.getMbId();
		
		if(loginData.getMbType() == 1) {
			//수강신청 내역이 있는지 검사
			if(bDao.hasClassJoin(clsNo, mbId) == 1)
				rattr.addFlashAttribute("fMsg","이미 수강 중인 클래스입니다.");
			else {			
				if(bDao.classJoin(clsNo, mbId)) 
					rattr.addFlashAttribute("fMsg","수강신청이 완료되었습니다.");
				else
					rattr.addFlashAttribute("fMsg","수강신청에 실패하였습니다. 다시 이용해주세요.");			
			}
		} else 
			rattr.addFlashAttribute("fMsg","학생 계정으로 로그인 후 이용해주세요!");
		
		mav.setViewName("redirect:/myclass_s");
		return mav;
	}
}