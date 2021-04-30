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
	
	ModelAndView mav;
	
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
	 * @param request 
	 * @Author 고연미 on 28/04/2021
	 */
	public ModelAndView goClassInfo(String clsNo, HttpServletRequest request) {
		mav = new ModelAndView();
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
	 * 클래스소개 이동 (관계자용)
	 * @Author 고연미 on 28/04/2021
	 */
	public ModelAndView goClassClassInfo(String clsNo, HttpSession session) {
		mav = new ModelAndView();
		//세션에서 mbId 가져오기
		MemberBean loginData = (MemberBean)session.getAttribute("loginData");
		
		//클래스번호나 로그인데이터가 없으면 메인으로 이동
		if(StringUtils.isEmpty(clsNo) || ObjectUtils.isEmpty(loginData)) {
			mav.setViewName("redirect:/");			
		} else {
			//클래스정보 가져와 bean에 담기
			ClassBean cb = bDao.getClassInfo(clsNo);
			mav.addObject("classInfo", cb);

			//mav에 네비타이틀 추가
			mav.addObject("navtext", "마이 클래스 > 클래스 소개");
			
			//강사 정보 가져와 mav에 담기 (MemberMM)
			TeacherBean tb = mDao.getTeacherProfile(cb.getMbId());
			mav.addObject("teacherInfo", tb);
			
			//커리큘럼 리스트 가져와 mav에 담기 (CurriculumMM)
			//List<CurriculumBean> crList = crDao.getCurriculumLIst(clsNo)
			//mav.addObject("curriList", crList);
			
			//회원타입 별 뷰페이지 분기
			if(loginData.getMbType() == 2)
				mav.setViewName("class/classinfo_t");	//강사
			else
				mav.setViewName("class/classinfo_s");	//학생
		}
		return mav;
	}
	/**
	 * 수강신청
	 * @Author 고연미 on 28/04/2021
	 */
	@Transactional
	public ModelAndView classJoin(String clsNo, HttpSession session, RedirectAttributes rattr) {
		mav = new ModelAndView();
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
	/**
	 * 게시판 목록 가져오기
	 * @Author 고연미 on 28/04/2021
	 */
	public ModelAndView goBoardList(String clsNo, Integer clsBrdType, Integer pageNum, HttpServletRequest request) {
		
		//게시글 삭제 후 목록으로 이동시 (redirect)
		//RedirectAttributes.addFlashAttribute로 보낸 데이터 가져오기
		Map<String, ?> redirectMap = RequestContextUtils.getInputFlashMap(request);  
		if( redirectMap != null ){
			clsNo = (String)redirectMap.get("clsNo");  // 오브젝트 타입이라 캐스팅해줌
			clsBrdType = (Integer)redirectMap.get("clsBrdType");
			pageNum = (Integer)redirectMap.get("pageNum");  
		}
	    
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
		
		/* clsBrdNo 기준 내림차순 정렬하기*/
		List<BoardBean> bListSort = new ArrayList<>();
        BListDecending bListDecending = new BListDecending();
        Collections.sort(bList, bListDecending);
 
        for (BoardBean nb : bList) {
            bListSort.add(nb);
        }

		//mav에 게시판 목록 정보 저장
		mav.addObject("bList", bListSort);
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
		//mav에 전체게시글수 추가
		mav.addObject("totalNum", totalNum);
		//view 페이지 설정
		mav.setViewName("board/boardList");			
		return mav;
	}
	/**
	 * Paging
	 * @Author 고연미 on 28/04/2021
	 */
	private String getPaging(String clsNo, Integer clsBrdType, Integer pageNum, HttpServletRequest request) {
		//전체 글 갯수 가져오기
		int maxNum = bDao.getBoardCount(clsNo, clsBrdType);
		int listCount = 10; //페이지당 나타낼 글의 갯수
		int pageCount = 2;	//페이지그룹당 페이지 갯수
		
		//Paging 클래스 객체 생성해서 page makeHhml 리턴
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, clsNo, clsBrdType);
		return paging.makeHtmlPaging(request);
	}
	/**
	 * 게시글 등록/수정 이동
	 * @Author 고연미 on 28/04/2021
	 */
	public ModelAndView goBoardWrite(String clsNo, Integer clsBrdType, String clsBrdNo, Integer pageNum) {
		mav = new ModelAndView();
		//pageNum이 null이면 1로 세팅
		pageNum = (pageNum==null)? 1 : pageNum;
		
		//게시글 수정인 경우
		if(!StringUtils.isEmpty(clsBrdNo)) {
			System.out.println("게시글 수정 ----------");
			System.out.println("clsBrdNo ========> "+ clsBrdNo);
			//게시글 정보 가져와 bean에 담기
			BoardBean board = bDao.getBoardRead(clsBrdNo);	
			//첨부파일 정보 가져와 bean에 저장
			if(board != null)
				board.setFilesInfo(bDao.getBoardFiles(clsBrdNo));
			
			mav.addObject("boardData", board);
		} else {
			System.out.println("새 게시글 등록 ----------");
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
	/**
	 * 게시글 등록/수정
	 * @Author 고연미 on 28/04/2021
	 */
	@Transactional
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
			System.out.println("-------- 게시글 update -------");
			clsBrdNo = board.getClsBrdNo();
			result = bDao.boardUpdate(board);
		} else {
			//insert 실행 ==> 새 clsBrdNo를 bean에 담아서 반환
			System.out.println("-------- 게시글 insert -------");
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
						System.out.println("file 저장 후 brd_file 테이블에 데이터 저장 완료.");
				} else
					System.out.println("file 저장 실패");
			}
		}				
		if(result) {
			rattr.addFlashAttribute("fMsg","게시글 저장을 완료하였습니다.");
			//view 페이지 설정
			mav.setViewName("redirect:/class/boardread");
		} else {
			rattr.addFlashAttribute("fMsg","게시글 저장에 실패하였습니다. \\n문제가 지속된다면 관리자에 문의 바랍니다.");
			//Referer : 이전 페이지에 대한 정보가 전부 들어있는 헤더
			String referer = request.getHeader("Referer");
			//view 페이지 설정
			mav.setViewName("redirect:"+ referer);
		}
		//rattr에 게시글번호 추가
		rattr.addFlashAttribute("clsBrdNo", clsBrdNo);	
		//rattr에 페이지넘버 추가
		rattr.addFlashAttribute("pageNum", pageNum);
		return mav;
	}
	/**
	 * 게시글 읽기
	 * @Author 고연미 on 28/04/2021
	 */
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
			logger.info("게시글 리로드 오류. 메인으로 이동.");
			throw new CommonException("[오류] 페이지를 리로드할 수 없습니다.\n\n메인화면으로 이동합니다.");
		}
	
		mav = new ModelAndView();
		//게시글정보 가져와 bean에 담기
		BoardBean board = bDao.getBoardRead(clsBrdNo);	
		//가져온 게시글정보가 있으면
//		if(board != null) {
		if(!ObjectUtils.isEmpty(board)) {
			//첨부파일 정보 가져와 bean에 저장
			board.setFilesInfo(bDao.getBoardFiles(clsBrdNo));
		}
		//로그인한 세션정보가 있으면
		if(!ObjectUtils.isEmpty(request.getSession().getAttribute("loginData"))) {
			//로그인정보 가져와 bean에 담기
			MemberBean loginData = (MemberBean)request.getSession().getAttribute("loginData");
			//조회수 추가(아이디 당 1번만 추가)
			addBrdView(clsBrdNo, loginData.getMbId());
			//로그인정보의 mbId와 게시글정보의 mbId가 같으면
			if(loginData.getMbId().equals(board.getMbId())) {
				//mav에 수정/삭제 버튼 추가
				mav.addObject("btnUpdate", makeHtmlBtnUpdate(board, pageNum, request));
			}			
		}
		//mav에 게시판정보 담기
		mav.addObject("boardData", board);	
		System.out.println("getFilesInfo 파일갯수 =======> "+ board.getFilesInfo().size());
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
	/**
	 * 조회수 추가
	 * @Author 고연미 on 28/04/2021
	 */
	@Transactional
	private void addBrdView(String clsBrdNo, String mbId) {
		int isViewCnt = 0;
		isViewCnt = bDao.getBrdViewId(clsBrdNo, mbId);
		if(isViewCnt < 1) {
			bDao.addBrdView(clsBrdNo, mbId);
		}		
	}
	/**
	 * 수정/삭제 버튼 생성
	 * @Author 고연미 on 28/04/2021
	 */
	private String makeHtmlBtnUpdate(BoardBean board, Integer pageNum, HttpServletRequest request) {
		String ctxPath = request.getContextPath();
		StringBuilder sb = new StringBuilder();
		sb.append("<form action='"+ ctxPath +"/class/goboardwrite' method='post'>\n");
		sb.append("<input type='hidden' name='clsBrdNo' value='"+ board.getClsBrdNo() +"'>\n");
		sb.append("<input type='hidden' name='clsBrdType' value='"+ board.getClsBrdType() +"'>\n");
		sb.append("<input type='hidden' name='clsNo' value='"+ board.getClsNo() +"'>\n");
		sb.append("<input type='hidden' name='pageNum' value='"+ pageNum +"'>\n");
		sb.append("<input type='submit' value='수정'> ");
		sb.append("<input type='button' value='삭제' onclick=\"location.href='"+ ctxPath +"/class/boarddelete?clsBrdNo="+ board.getClsBrdNo() +"&pageNum="+ pageNum +"';\">\n");
		sb.append("</form>");
		return sb.toString();
	}
	/**
	 * 게시글 삭제
	 * @Author 고연미 on 28/04/2021
	 */
	@Transactional
	public ModelAndView boardDelete(String clsBrdNo, Integer pageNum, HttpServletRequest request, RedirectAttributes rattr) {
		mav = new ModelAndView();
		boolean result = false;		
		//게시글정보 가져와 bean에 담기
		BoardBean board = bDao.getBoardRead(clsBrdNo);	
		//가져온 게시글정보가 있으면
		if(!ObjectUtils.isEmpty(board)) {
			//댓글 사용하는 게시판이면
			if(Constant.clsBrdHasReply[board.getClsBrdType()]) {
				//댓글정보 삭제
				if(result = deleteReplyList(clsBrdNo, request))	//삭제 성공시 true 리턴
					logger.info(clsBrdNo +"번 게시글 댓글리스트 정보삭제 완료. ");	
				else
					logger.info(clsBrdNo +"번 게시글 댓글리스트 정보삭제 실패! ");	
			} else
				result = true;	//댓글 사용하지않는 게시판이면 true
			
			//댓글 삭제 성공이거나 댓글 사용하지않는 게시판이면 첨부파일 삭제루틴 실행
			if(result) {				
				//첨부파일 정보 가져오기
				List<FileBean> fList = bDao.getBoardFiles(clsBrdNo);
				//가져온 파일정보가 있으면
				if(!ObjectUtils.isEmpty(fList)) {
					// 모두 삭제
					for(FileBean fb : fList) {
						//DB > BRD_FILE 정보 삭제
						if(bDao.deleteBrdFile(fb.getFileNo())) {
							//DB > TB_FILE 정보 삭제, 파일 삭제
							if(fm.deleteFile(fb.getFileNo(), request)) {
								logger.info(fb.getFileNo() +"번 파일 : 삭제(DB/file) 완료.");
							} else
								logger.info(fb.getFileNo() +"번 파일 : 삭제(DB or file) 실패! ");								
						} else {
							logger.info(fb.getFileNo() +"번 파일 : DB(brd_file) 정보삭제 실패! ");
							result = false;
						}
					}
				}				
			}
			//댓글과 파일 삭제가 성공하면
			if(result) {
				//조회수 정보 삭제
				if(bDao.delBrdView(clsBrdNo)) {
					System.out.println("조회수 정보 삭제 완료.");
					//게시글정보(CLS_BRD) 삭제
					if(bDao.deleteBrd(clsBrdNo)) {
						rattr.addFlashAttribute("fMsg","게시글 삭제가 완료되었습니다.");
						logger.info(clsBrdNo +"번 게시글 : 삭제(DB) 완료. ");
					} else
						result = false;
				} else
					result = false;		
			}
			//실패시
			if(!result) {
				rattr.addFlashAttribute("fMsg","게시글 삭제에 실패하였습니다. \n문제가 지속된다면 관리자에 문의 바랍니다.");
				logger.info(clsBrdNo +"번 게시글 : 삭제 실패! ");
			}
		}
		//rattr에 클래스넘버 추가
		rattr.addFlashAttribute("clsNo", board.getClsNo());
		//rattr에 게시판 타입 추가
		rattr.addFlashAttribute("clsBrdType", board.getClsBrdType());
		//rattr에 페이지넘버 추가
		rattr.addFlashAttribute("pageNum", pageNum);
		//view 페이지 설정
		mav.setViewName("redirect:/class/boardlist");
		return mav;
	}
	/**
	 * 댓글 목록 삭제
	 * @Author 고연미 on 28/04/2021
	 */
	@Transactional
	private boolean deleteReplyList(String clsBrdNo, HttpServletRequest request) {
		boolean result = true;
		//댓글리스트 가져오기
		List<ReplyBean> rList = DDao.getReplyList(clsBrdNo);		
		//댓글리스트 정보가 존재하면
		if(!ObjectUtils.isEmpty(rList)) {
			//댓글리스트 삭제
			if(bDao.deleteReplyList(clsBrdNo)) {
				//첨부파일 삭제루틴 실행
				for(ReplyBean rb : rList) {
					//fileNo가 존재하는 경우(첨부파일이 있는 경우)
					if(!StringUtils.isEmpty(rb.getFileNo())) {
						//파일 삭제
						if(fm.deleteFile(rb.getFileNo(), request)) {
							logger.info(rb.getFileNo() +"번 파일 : 삭제(DB/file) 완료.");
						} else {
							logger.info(rb.getFileNo() +"번 파일 : 삭제(DB or file) 실패! ");	
						}
					}			
				}
			} else 	result = false;			
		}		
		return result;
	}
	/**
	 * (게시글 수정시) 파일 삭제 : 파일 삭제 후 파일목록 반환
	 * @Author 고연미 on 28/04/2021
	 */
	@Transactional
	public List<FileBean> delBrdFile(String fileNo, String clsBrdNo, HttpServletRequest request) {
		//DB > BRD_FILE 정보 삭제
		if(bDao.deleteBrdFile(fileNo)) {
			//DB > TB_FILE 정보 삭제, 파일 삭제
			if(fm.deleteFile(fileNo, request)) {
				logger.info(fileNo +"번 파일 : 삭제(DB/file) 완료.");
			} else
				logger.info(fileNo +"번 파일 : 삭제(DB or file) 실패! ");								
		} else {
			logger.info(fileNo +"번 파일 : DB(brd_file) 정보삭제 실패! ");
		}
		//삭제 여부와 상관없이 파일리스트 반환
		return bDao.getBoardFiles(clsBrdNo);
	}
}
/**
 * 게시판 목록 내림차순 정렬
 * @Author 고연미 on 28/04/2021
 * Comparator : 새로운 정렬기준으로 객체를 정렬하는 인터페이스
 */
class BListDecending implements Comparator<BoardBean> {
 
    @Override
    public int compare(BoardBean a, BoardBean b) {
        Integer temp1 = Integer.parseInt(a.getClsBrdNo());
        Integer temp2 = Integer.parseInt(b.getClsBrdNo());   
        //compareTo : 두개의 값을 비교하여 int로 반환(크다(1), 같다(0), 작다(-1))
        return temp2.compareTo(temp1);
    }
}
