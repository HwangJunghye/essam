package com.essam.www.b;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.BoardBean;
import com.essam.www.bean.ClassBean;
import com.essam.www.bean.FileBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.TeacherBean;
import com.essam.www.constant.Constant;
import com.essam.www.eclass.Paging;
import com.essam.www.exception.CommonException;
import com.essam.www.file.FileMM;

@Service
public class BMM {
	@Autowired
	private IBDao bDao;
	@Autowired
	private FileMM fm;
	
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
		
		if(clsNo == null) {
			mav.setViewName("redirect:/");			
		} else {
			//클래스 정보 가져와 mav에 담기
			ClassBean cb = bDao.getClassInfo(clsNo);
			
			if(cb != null) {
				//클래스 수강신청인원 가져와 cb에 담기
				cb.setClsRegiCnt(bDao.getClassRegiCnt(clsNo));
			}
			mav.addObject("classInfo", cb);
			
			//강사 정보 가져와 mav에 담기 (MemberMM)
			//TeacherBean tb = bDao.getTeacherProfile(cb.getMbId());
			//mav.addObject("teacherProfile", tb);
			
			//커리큘럼 정보 가져와 mav에 담기
			
			mav.setViewName("class/classinfo_main");			
		}
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
		//pageNum이 null이면 1로 세팅
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
		//mav에 네비타이틀 추가
		mav.addObject("navtext", "마이 클래스 > "+ Constant.clsBrdName[clsBrdType]);
		//mav에 클래스명 추가
		mav.addObject("clsName", bDao.getClassName(clsNo));
		//view 페이지 설정
		mav.setViewName("board/boardList");	
		
		return mav;
	}

	private String getPaging(String clsNo, Integer clsBrdType, Integer pageNum) {
		//전체 글 갯수 가져오기
		int maxNum = bDao.getBoardCount(clsNo, clsBrdType);
		int listCount = 10; //페이지당 나타낼 글의 갯수
		int pageCount = 2;	//페이지그룹당 페이지 갯수
		
		//Paging 클래스 객체 생성해서 page makeHhml 리턴
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, clsNo, clsBrdType);
		return paging.makeHtmlPaging();
	}
	
	public ModelAndView goBoardWrite(String clsNo, Integer clsBrdType, String clsBrdNo) {
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
		//mav에 네비타이틀 추가
		mav.addObject("navtext", "마이 클래스 > "+ Constant.clsBrdName[clsBrdType]);
		//mav에 클래스명 추가
		mav.addObject("clsName", bDao.getClassName(clsNo));
		//view 페이지 설정
		mav.setViewName("board/boardWrite");
		return mav;
	}
	
	public ModelAndView boardWrite(BoardBean board, MultipartHttpServletRequest mReq, HttpServletRequest request, RedirectAttributes rattr) {
		mav = new ModelAndView();
		boolean result;
		String[] arrayFilename;
		String fileType;
		int fileTypeNo = 0;
		String clsBrdNo;
				
		//request에서 mbId 가져오기
		MemberBean loginData = (MemberBean)request.getSession().getAttribute("loginData");
		board.setMbId(loginData.getMbId());

		//게시판 글번호가 null이 아니면
		if(board.getClsBrdNo() != null) {
			//update 실행
			clsBrdNo = board.getClsBrdNo();
			result = bDao.boardUpdate(board);
		} else {
			//insert 실행 ==> clsBrdNo 반환
			clsBrdNo = bDao.boardInsert(board);
			result = (clsBrdNo != null) ? true : false;
		}
		
		//(새로운)첨부파일이 있다면 
		if(mReq.getFiles("files").get(0).getSize() != 0){
			//파일 가져오기
			List<MultipartFile> files = mReq.getFiles("files");
			System.out.println("파일 갯수 : "+ files.size());
			
			//여러개일 경우 한개씩 서버에 저장 for each
			for(MultipartFile mf : files) {
				System.out.println("file name= "+ mf.getOriginalFilename());
				//파일 확장자 추출
				arrayFilename = mf.getOriginalFilename().split(".");
				fileType = arrayFilename[arrayFilename.length-1];
				
				//1.이미지 2.동영상 3.기타
				if(fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("gif") || fileType.equalsIgnoreCase("png")) {
					fileTypeNo = 1;
				} else if(fileType.equalsIgnoreCase("mp4") || fileType.equalsIgnoreCase("avi")) {
					fileTypeNo = 2;
				} else 
					fileTypeNo = 3;
				
				//파일 저장 => fileNo 반환
				String fileNo = fm.saveFile(mReq, mf, fileTypeNo);
				
				if(fileNo != null) {
					if(bDao.brdFileInsert(clsBrdNo, fileNo))
						System.out.println("brd_file 테이블에 clsBrdNo, fileNo 저장 성공.");
				}			
			}
		}		
		
		if(result) 
			rattr.addFlashAttribute("fMsg","글이 등록되었습니다.");
		else
			rattr.addFlashAttribute("fMsg","글 등록에 실패하였습니다. \n문제가 지속된다면 관리자에 문의 바랍니다.");
		
		//mav에 클래스넘버 추가
		mav.addObject("clsNo", board.getClsNo());
		//mav에 게시판 타입 추가
		mav.addObject("clsBrdType", board.getClsBrdType());
		//mav에 네비타이틀 추가
		mav.addObject("navtext", "마이 클래스 > "+ Constant.clsBrdName[board.getClsBrdType()]);
		//mav에 클래스명 추가
		mav.addObject("clsName", bDao.getClassName(board.getClsNo()));
		//mav에 글번호 추가
		mav.addObject("clsBrdNo", clsBrdNo);
		//view 페이지 설정
		mav.setViewName("board/boardRead");
		return mav;
	}


	
}
