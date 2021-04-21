package com.essam.www.b;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.BoardBean;
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

	public ModelAndView classJoin(String clsNo, String mbId, RedirectAttributes rattr) {
		mav = new ModelAndView();
		
		if(bDao.classJoin(clsNo, mbId)) 
			rattr.addFlashAttribute("fMsg","수강신청이 완료되었습니다.");
		else
			rattr.addFlashAttribute("fMsg","수강신청에 실패하였습니다. 다시 이용해주세요.");
		
		mav.setViewName("redirect:/myclass_s");
		return mav;
	}

	public ModelAndView goBoardList(int clsBrdType) {
		mav = new ModelAndView();
		//게시판 목록 가져오기
		ArrayList<BoardBean> bList = bDao.getBoardList(clsBrdType);	
		//각 게시글의 첨부파일 갯수 가져와 bean에 저장
		for(BoardBean board : bList) {
			board.setClsBrdfileCnt(bDao.getBoardFiles(board.getClsBrdNo()));
		}
		//mav에 게시판 목록 정보 저장
		mav.addObject("bList", bList);		
		//mav에 게시판 타입 추가
		mav.addObject("clsBrdType", clsBrdType);
		//view 페이지 설정
		mav.setViewName("board/boardList");
		return mav;
	}
	
}
