package com.essam.www.b;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.ClassBean;
import com.essam.www.bean.TeacherBean;
import com.essam.www.file.FileMM;

@Service
public class BMM {
	@Autowired
	private IBDao bDao;
	@Autowired
	private FileMM fmm;
	
	ModelAndView mav;
	
	public List<ClassBean> getClassList(String str, String mbId) {
		List<ClassBean> cList = null;
		switch(str) {
		case "new":
			cList = bDao.getClassListNew();
			break;
		case "my":
			cList = bDao.getClassListMy(mbId);
			break;
		}
		return cList;
	}

	public ModelAndView goClassInfo(String clsNo) {
		mav = new ModelAndView();
		
		//클래스 정보 가져와 mav에 담기
		ClassBean cb = bDao.getClassInfo(clsNo);
		//클래스 수강신청인원 가져와 cb에 담기
		cb.setClsRegiCnt(bDao.getClassRegiCnt(clsNo));
		mav.addObject("classInfo", cb);
		
		//강사 정보 가져와 mav에 담기 (MemberMM)
		//TeacherBean tb = bDao.getTeacherProfile(cb.getMbId());
		//mav.addObject("teacherProfile", tb);
		
		//커리큘럼 정보 가져와 mav에 담기
		
		mav.setViewName("class/classinfo_main");
		return mav;
	}
	
}
