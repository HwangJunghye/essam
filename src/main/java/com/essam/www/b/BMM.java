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
import com.essam.www.eclass.Paging;
import com.essam.www.exception.CommonException;
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

	public ModelAndView goBoardList(String clsNo, Integer clsBrdType, Integer pageNum) throws CommonException {
		mav = new ModelAndView();
		pageNum = (pageNum==null)? 1 : pageNum;
		
		if(pageNum<=0) {
			throw new CommonException("잘못된 페이지번호 입니다.");
		}
		//게시판 목록 가져오기
		ArrayList<BoardBean> bList = bDao.getBoardList(clsNo, clsBrdType, pageNum);	
		System.out.println("bList.size = "+ bList.size());
		
		if(bList != null && bList.size() != 0) {
			//각 게시글의 첨부파일 갯수 가져와 bean에 저장
			for(BoardBean board : bList) {
				board.setClsBrdfileCnt(bDao.getBoardFileCnt(board.getClsBrdNo()));
			}
		}
		//mav에 게시판 목록 정보 저장
		mav.addObject("bList", bList);
		//mav에 페이징 정보 저장
		mav.addObject("paging", getPaging(clsNo, clsBrdType, pageNum));
		//mav에 클래스넘버 추가
		mav.addObject("clsNo", clsNo);
		//mav에 게시판 타입 추가
		mav.addObject("clsBrdType", clsBrdType);
		//view 페이지 설정
		mav.setViewName("board/boardList");	
		
		return mav;
	}

	private String getPaging(String clsNo, Integer clsBrdType, Integer pageNum) {
		//전체 글 갯수 가져오기
		int maxNum = bDao.getBoardCount(clsNo, clsBrdType);
		int listCount = 10; //페이지당 나타낼 글의 갯수
		int pageCount = 2;	//페이지그룹당 페이지 갯수
		
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, clsNo, clsBrdType);
		return paging.makeHtmlPaging();
	}
	
	public ModelAndView goBoardWrite(String clsNo, Integer clsBrdType, String clsBrdNo, String mbId, RedirectAttributes rattr) {
		mav = new ModelAndView();
		//게시물 수정인 경우
		if(clsBrdNo != null) {
			//게시물 정보 가져와 bean에 담기
			BoardBean board = bDao.getBoardRead(clsBrdNo);	
			//첨부파일 정보 가져와 bean에 저장
			if(board != null)
				board.setFiles(bDao.getBoardFiles(clsBrdNo));
			
			mav.addObject("boardData", board);
		}
		//mav에 클래스넘버 추가
		mav.addObject("clsNo", clsNo);
		//mav에 게시판 타입 추가
		mav.addObject("clsBrdType", clsBrdType);
		//view 페이지 설정
		mav.setViewName("board/boardWrite");
		return mav;
	}
	
}
