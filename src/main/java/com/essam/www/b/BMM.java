package com.essam.www.b;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import com.essam.www.bean.TeacherBean;
import com.essam.www.constant.Constant;
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

	public ModelAndView goBoardList(String clsNo, Integer clsBrdType, Integer pageNum, HttpServletRequest request) {
		mav = new ModelAndView();

		//pageNum이 null이면 1로 세팅
		pageNum = (pageNum==null)? 1 : pageNum;
		
		if(pageNum<=0) {
			throw new CommonException("잘못된 페이지번호 입니다.");
		}
		//전체 글 갯수 가져오기
		int totalNum = bDao.getBoardCount(clsNo, clsBrdType);
		//게시판 목록 가져오기
		ArrayList<BoardBean> bList = bDao.getBoardList(clsNo, clsBrdType, pageNum);	
		
		if(bList != null && bList.size() != 0) {
			//각 게시글의 첨부파일 갯수 가져와 bean에 저장
			for(BoardBean board : bList) {
				board.setClsBrdfileCnt(bDao.getBoardFileCnt(board.getClsBrdNo()));
			}
		}
		//mav에 게시판 목록 정보 저장
		mav.addObject("bList", bList);
		//mav에 페이징 정보 저장
		mav.addObject("paging", getPaging(clsNo, clsBrdType, pageNum, request));
		//mav에 클래스넘버 추가
		mav.addObject("clsNo", clsNo);
		//mav에 게시판 타입 추가
		mav.addObject("clsBrdType", clsBrdType);
		//mav에 네비타이틀 추가
		mav.addObject("navtext", "마이 클래스 > "+ Constant.clsBrdName[clsBrdType]);
		//mav에 클래스명 추가
		mav.addObject("clsName", bDao.getClassName(clsNo));
		//mav에 전체게시물수 추가
		mav.addObject("totalNum", totalNum);
		//view 페이지 설정
		mav.setViewName("board/boardList");			
		return mav;
	}

	private String getPaging(String clsNo, Integer clsBrdType, Integer pageNum, HttpServletRequest request) {
		//전체 글 갯수 가져오기
		int maxNum = bDao.getBoardCount(clsNo, clsBrdType);
		int listCount = 10; //페이지당 나타낼 글의 갯수
		int pageCount = 2;	//페이지그룹당 페이지 갯수
		
		//Paging 클래스 객체 생성해서 page makeHhml 리턴
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, clsNo, clsBrdType);
		return paging.makeHtmlPaging(request);
	}
	
	public ModelAndView goBoardWrite(String clsNo, Integer clsBrdType, String clsBrdNo, Integer pageNum) {
		mav = new ModelAndView();
		//pageNum이 null이면 1로 세팅
		pageNum = (pageNum==null)? 1 : pageNum;
		System.out.println("clsBrdNo ========> "+ clsBrdNo);
		//게시물 수정인 경우
		if(!StringUtils.isEmpty(clsBrdNo)) {
			System.out.println("게시물 수정 ----------");
			//게시물 정보 가져와 bean에 담기
			BoardBean board = bDao.getBoardRead(clsBrdNo);	
			//첨부파일 정보 가져와 bean에 저장
			if(board != null)
				board.setFilesInfo(bDao.getBoardFiles(clsBrdNo));
			
			mav.addObject("boardData", board);
		} else {
			System.out.println("새 게시물 등록 ----------");
			//새글 작성인 경우 글목록 클릭시 첫페이지로 이동
			pageNum = 1;
		}
		//mav에 클래스넘버 추가
		mav.addObject("clsNo", clsNo);
		//mav에 게시판 타입 추가
		mav.addObject("clsBrdType", clsBrdType);
		//mav에 네비타이틀 추가
		mav.addObject("navtext", "마이 클래스 > "+ Constant.clsBrdName[clsBrdType]);
		//mav에 클래스명 추가
		mav.addObject("clsName", bDao.getClassName(clsNo));
		//mav에 페이지넘버 추가
		mav.addObject("pageNum", pageNum);
		//view 페이지 설정
		mav.setViewName("board/boardWrite");
		return mav;
	}
	
	public ModelAndView boardWrite(BoardBean board, MultipartHttpServletRequest mReq, HttpServletRequest request, RedirectAttributes rattr) {
		mav = new ModelAndView();
		boolean result = false;		//게시판 저장 결과
		int fileTypeNo = 0;			//파일타입번호
		String clsBrdNo = null;		//글번호
		String extension = null;	//확장자
		int pageNum = Integer.parseInt(mReq.getParameter("pageNum"));
				
		//request에서 mbId 가져오기
		MemberBean loginData = (MemberBean)request.getSession().getAttribute("loginData");
		board.setMbId(loginData.getMbId());

		//게시판 글번호가 null이 아니면
		if(!StringUtils.isEmpty(board.getClsBrdNo())) {
			//update 실행
			System.out.println("-------- 게시물 update -------");
			clsBrdNo = board.getClsBrdNo();
			result = bDao.boardUpdate(board);
		} else {
			//insert 실행 ==> 새 clsBrdNo를 bean에 담아서 반환
			System.out.println("-------- 게시물 insert -------");
			//게시글 등록 성공이면
			if(bDao.boardInsert(board)) {
				clsBrdNo = board.getClsBrdNo();
				result = true;
			}
		}		
		//글저장에 성공하고, (새로운)첨부파일이 있다면 
		if(result && mReq.getFiles("files").get(0).getSize() != 0){
			//파일 가져오기
			List<MultipartFile> files = mReq.getFiles("files");
			System.out.println("파일 갯수 : "+ files.size());
			
			//여러개일 경우 한개씩 서버에 저장 for each
			for(MultipartFile mf : files) {
				System.out.println("file name= "+ mf.getOriginalFilename());
				//파일 확장자 추출
				extension = FilenameUtils.getExtension(mf.getOriginalFilename());
				System.out.println("파일 확장자 ==> "+ extension);
				
				//1.이미지 2.동영상 3.기타
				if(extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("png")) {
					fileTypeNo = 1;
				} else if(extension.equalsIgnoreCase("mp4") || extension.equalsIgnoreCase("avi")) {
					fileTypeNo = 2;
				} else 
					fileTypeNo = 3;
				
				//파일 저장 => fileNo 반환
				String fileNo = fm.saveFile(mReq, mf, fileTypeNo);
				
				if(fileNo != null) {
					// DB에 파일정보 저장
					if(bDao.brdFileInsert(clsBrdNo, fileNo))
						System.out.println("file 저장 후 brd_file 테이블에 데이터 저장 성공.");
				} else
					System.out.println("file 저장 실패");
			}
		}				
		if(result) {
			rattr.addFlashAttribute("fMsg","게시물 저장을 완료하였습니다.");
			//view 페이지 설정
			mav.setViewName("redirect:/class/boardread");
		} else {
			rattr.addFlashAttribute("fMsg","게시물 저장에 실패하였습니다. \n문제가 지속된다면 관리자에 문의 바랍니다.");
			//Referer : 이전 페이지에 대한 정보가 전부 들어있는 헤더
			String referer = request.getHeader("Referer");
			//view 페이지 설정
			mav.setViewName("redirect:"+ referer);
		}
		//등록한 게시물 정보 가져와 bean에 담기
//		BoardBean newBoard = bDao.getBoardRead(clsBrdNo);
//		//첨부파일 정보 가져와 bean에 저장
//		if(newBoard != null)
//			newBoard.setFilesInfo(bDao.getBoardFiles(clsBrdNo));
		
		//rattr에 게시물 정보 담기
//		rattr.addFlashAttribute("boardData", newBoard);	
		//rattr에 게시물번호 추가
		rattr.addFlashAttribute("clsBrdNo", clsBrdNo);	
		//rattr에 네비타이틀 추가
//		rattr.addFlashAttribute("navtext", "마이 클래스 > "+ Constant.clsBrdName[newBoard.getClsBrdType()]);
		//rattr에 클래스명 추가
//		rattr.addFlashAttribute("clsName", bDao.getClassName(newBoard.getClsNo()));
		//rattr에 페이지넘버 추가
		rattr.addFlashAttribute("pageNum", pageNum);
		return mav;
	}

	public ModelAndView boardRead(String clsBrdNo, Integer pageNum, HttpServletRequest request) {
		
		//RedirectAttributes.addFlashAttribute로 보낸 데이터 가져오기
		Map<String, ?> redirectMap = RequestContextUtils.getInputFlashMap(request);  
	    if( redirectMap != null ){
	    	clsBrdNo = (String)redirectMap.get("clsBrdNo");  // 오브젝트 타입이라 캐스팅해줌
	        pageNum = (Integer)redirectMap.get("pageNum");  
	    }
	    //글쓰기 후 boardRead로 이동된(redirect) 경우, 데이터를 addFlashAttribute로 가져오기때문에 브라우저 리로드시 NullPointException 발생
	    // (게시판 목록에서 직접 boardRead한 경우는 상관없음.)
		if(StringUtils.isEmpty(clsBrdNo)) {
			logger.info("게시물 리로드 오류. 메인으로 이동.");
			throw new CommonException("[오류] 페이지를 리로드할 수 없습니다.\n\n메인화면으로 이동합니다.");
		}
	
		mav = new ModelAndView();
		//게시물정보 가져와 bean에 담기
		BoardBean board = bDao.getBoardRead(clsBrdNo);	
		//가져온 게시물정보가 있으면
//		if(board != null) {
		if(!ObjectUtils.isEmpty(board)) {
			//첨부파일 정보 가져와 bean에 저장
			board.setFilesInfo(bDao.getBoardFiles(clsBrdNo));
		}
		//로그인한 세션정보가 있으면
		if(!ObjectUtils.isEmpty(request.getSession().getAttribute("loginData"))) {
			//로그인정보 가져와 bean에 담기
			MemberBean loginData = (MemberBean)request.getSession().getAttribute("loginData");
			//로그인정보의 mbId와 게시물정보의 mbId가 같으면
			if(loginData.getMbId().equals(board.getMbId())) {
				//mav에 수정/삭제 버튼 추가
				mav.addObject("btnUpdate", makeHtmlBtnUpdate(board, pageNum, request));
			}			
		}
		//mav에 게시판정보 담기
		mav.addObject("boardData", board);	
		//mav에 네비타이틀 추가
		mav.addObject("navtext", "마이 클래스 > "+ Constant.clsBrdName[board.getClsBrdType()]);
		//mav에 클래스명 추가
		mav.addObject("clsName", bDao.getClassName(board.getClsNo()));
		//mav에 페이지넘버 추가
		mav.addObject("pageNum", pageNum);
		//view 페이지 설정
		mav.setViewName("board/boardRead");
		return mav;
	}

	private String makeHtmlBtnUpdate(BoardBean board, Integer pageNum, HttpServletRequest request) {
		String ctxPath = request.getContextPath();
		StringBuilder sb = new StringBuilder();
		sb.append("<form action='"+ ctxPath +"/class/goboardwrite' method='post'>\n");
		sb.append("<input type='hidden' name='clsBrdNo' value='"+ board.getClsBrdNo() +"'>\n");
		sb.append("<input type='hidden' name='clsBrdType' value='"+ board.getClsBrdType() +"'>\n");
		sb.append("<input type='hidden' name='clsNo' value='"+ board.getClsNo() +"'>\n");
		sb.append("<input type='hidden' name='pageNum' value='"+ pageNum +"'>\n");
		sb.append("<button>수정</button> ");
		sb.append("<input type='button' value='삭제' onclick=\"location.href='"+ ctxPath +"/class/boarddelete?clsBrdNo="+ board.getClsBrdNo() +"&pageNum="+ pageNum +"';\">\n");
		sb.append("</form>");
		return sb.toString();
	}

	public ModelAndView boardDelete(String clsBrdNo, Integer pageNum, HttpServletRequest request) {
		mav = new ModelAndView();
		return mav;
	}


	
}
