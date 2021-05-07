package com.essam.www.eclass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

import com.essam.www.eclass.BListDecending;
import com.essam.www.bean.BoardBean;
import com.essam.www.bean.ClassBean;
import com.essam.www.bean.CurriculumBean;
import com.essam.www.bean.FileBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.ReplyBean;
import com.essam.www.bean.StudentBean;
import com.essam.www.bean.TeacherBean;
import com.essam.www.constant.Constant;
import com.essam.www.curriculum.ICurriculumDao;
import com.essam.www.exception.CommonException;
import com.essam.www.file.FileMM;
import com.essam.www.member.IMemberDao;

@Service
public class ClassMM {
	private static final Logger logger = LoggerFactory.getLogger(ClassMM.class);
	
	@Autowired
	private FileMM fm;
	@Autowired
	private IMemberDao mDao;	
	@Autowired
	private IClassDao cDao;	
	@Autowired
	private ICurriculumDao crDao;
 
		
	/**
	 * (CM01)클래스소개 이동 (관계자용)
	 * @Author 고연미 on 28/04/2021
	 */
	public ModelAndView goClassClassInfo(String clsNo, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		//세션에서 mbId 가져오기
		MemberBean loginData = (MemberBean)session.getAttribute("loginData");
		
		//클래스번호나 로그인데이터가 없으면 메인으로 이동
		if(StringUtils.isEmpty(clsNo) || ObjectUtils.isEmpty(loginData)) {
			mav.setViewName("redirect:/");			
		} else {
			//클래스정보 가져와 bean에 담기
			ClassBean cb = cDao.getClassInfo(clsNo);
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
	 * (CM02+CM03)게시판(공지사항/과제) 목록 페이지 이동 + 게시판 목록 가져오기	
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
	    
		ModelAndView mav = new ModelAndView();
		//pageNum이 null이면 1로 세팅
		pageNum = (pageNum==null)? 1 : pageNum;
		
		if(pageNum<=0) {
			throw new CommonException("잘못된 페이지번호 입니다.");
		}
		//전체 글 갯수 가져오기
		int totalNum = cDao.getBoardCount(clsNo, clsBrdType);
		
		//게시판 목록 가져오기
		ArrayList<BoardBean> bList = cDao.getBoardList(clsNo, clsBrdType, pageNum);	
		
		if(bList != null && bList.size() != 0) {
			//각 게시글의 첨부파일 갯수 가져와 bean에 저장
			for(BoardBean board : bList) {
				board.setClsBrdfileCnt(cDao.getBoardFileCnt(board.getClsBrdNo()));
			}
		}
		
		/* clsBrdNo 기준 내림차순 정렬하기*/
        BListDecending bListDecending = new BListDecending();
        Collections.sort(bList, bListDecending); 

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
		mav.addObject("clsName", cDao.getClassName(clsNo));
		//mav에 전체게시글수 추가
		mav.addObject("totalNum", totalNum);
		//view 페이지 설정
		mav.setViewName("board/boardList");			
		return mav;
	}
	/**
	 * (CM05)게시판 목록 페이징
	 * @Author 고연미 on 28/04/2021
	 */
	private String getPaging(String clsNo, Integer clsBrdType, Integer pageNum, HttpServletRequest request) {
		//전체 글 갯수 가져오기
		int maxNum = cDao.getBoardCount(clsNo, clsBrdType);
		int listCount = 10; //페이지당 나타낼 글의 갯수
		int pageCount = 2;	//페이지그룹당 페이지 갯수
		
		//Paging 클래스 객체 생성해서 page makeHhml 리턴
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, clsNo, clsBrdType);
		return paging.makeHtmlPaging(request);
	}
	/**
	 * (CM06+CM07)게시판(공지사항/과제) 상세 페이지 이동 + 과제 상세 가져오기
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
	
		ModelAndView mav = new ModelAndView();
		//게시글정보 가져와 bean에 담기
		BoardBean board = cDao.getBoardRead(clsBrdNo);	
		//가져온 게시글정보가 있으면
//		if(board != null) {
		if(!ObjectUtils.isEmpty(board)) {
			//첨부파일 정보 가져와 bean에 저장
			board.setFilesInfo(cDao.getBoardFiles(clsBrdNo));
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
		mav.addObject("clsName", cDao.getClassName(board.getClsNo()));
		//mav에 페이지넘버 추가
		mav.addObject("pageNum", pageNum);
		//view 페이지 설정
		mav.setViewName("board/boardRead");
		return mav;
	}
	/**
	 * (CM08)조회수 추가
	 * @Author 고연미 on 28/04/2021
	 */
	@Transactional
	private void addBrdView(String clsBrdNo, String mbId) {
		int isViewCnt = 0;
		//현 게시글에 해당 mbId 조회정보가 등록되어있는지 확인
		isViewCnt = cDao.getBrdViewId(clsBrdNo, mbId);
		if(isViewCnt < 1) {
			cDao.addBrdView(clsBrdNo, mbId);
		}		
	}
	/**
	 * (CM09)수정/삭제 버튼 생성
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
		sb.append("<input type='submit' class='btn_normal_t' value='수정'>&nbsp;&nbsp;&nbsp;");
		sb.append("<input type='button' class='btn_normal_t' value='삭제' onclick=\"cfmDelBrd()\">\n");
		sb.append("</form>");
		return sb.toString(); 
	}
	/**
	 * (CM10)게시글 등록/수정 이동
	 * @Author 고연미 on 28/04/2021
	 */
	public ModelAndView goBoardWrite(String clsNo, Integer clsBrdType, String clsBrdNo, Integer pageNum) {
		ModelAndView mav = new ModelAndView();
		//pageNum이 null이면 1로 세팅
		pageNum = (pageNum==null)? 1 : pageNum;
		
		//게시글 수정인 경우
		if(!StringUtils.isEmpty(clsBrdNo)) {
			System.out.println("게시글 수정 ----------");
			System.out.println("clsBrdNo ========> "+ clsBrdNo);
			//게시글 정보 가져와 bean에 담기
			BoardBean board = cDao.getBoardRead(clsBrdNo);	
			//첨부파일 정보 가져와 bean에 저장
			if(board != null)
				board.setFilesInfo(cDao.getBoardFiles(clsBrdNo));
			
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
		mav.addObject("clsName", cDao.getClassName(clsNo));
		//mav에 페이지넘버 추가
		mav.addObject("pageNum", pageNum);
		//view 페이지 설정
		mav.setViewName("board/boardWrite");
		return mav;
	}
	/**
	 * (CM11)게시글 등록/수정
	 * @Author 고연미 on 28/04/2021
	 */
	@Transactional
	public ModelAndView boardWrite(BoardBean board, MultipartHttpServletRequest mReq, HttpServletRequest request, RedirectAttributes rattr) {
		ModelAndView mav = new ModelAndView();
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
			result = cDao.boardUpdate(board);
		} else {
			//insert 실행 ==> 새 clsBrdNo를 bean에 담아서 반환
			System.out.println("-------- 게시글 insert -------");
			//게시글 등록 성공이면
			if(cDao.boardInsert(board)) {
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
					if(cDao.brdFileInsert(clsBrdNo, fileNo))
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
	 * (CM13)(게시글 수정시)Ajax 첨부파일 삭제 : 파일 삭제 후 파일목록 반환
	 * @Author 고연미 on 28/04/2021
	 */
	@Transactional
	public List<FileBean> delBrdFile(String fileNo, String clsBrdNo, HttpServletRequest request) {
		//DB > BRD_FILE 정보 삭제
		if(cDao.deleteBrdFile(fileNo)) {
			//DB > TB_FILE 정보 삭제, 파일 삭제
			if(fm.deleteFile(fileNo, request)) {
				logger.info(fileNo +"번 파일 : 삭제(DB/file) 완료.");
			} else
				logger.info(fileNo +"번 파일 : 삭제(DB or file) 실패! ");								
		} else {
			logger.info(fileNo +"번 파일 : DB(brd_file) 정보삭제 실패! ");
		}
		//삭제 여부와 상관없이 파일리스트 반환
		return cDao.getBoardFiles(clsBrdNo);
	}
	/**
	 * (CM14)게시글(공지사항/과제) 삭제
	 * @Author 고연미 on 28/04/2021
	 */
	@Transactional
	public ModelAndView boardDelete(String clsBrdNo, Integer pageNum, HttpServletRequest request, RedirectAttributes rattr) {
		ModelAndView mav = new ModelAndView();
		boolean result = false;		
		//게시글정보 가져와 bean에 담기
		BoardBean board = cDao.getBoardRead(clsBrdNo);	
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
				List<FileBean> fList = cDao.getBoardFiles(clsBrdNo);
				//가져온 파일정보가 있으면
				if(!ObjectUtils.isEmpty(fList)) {
					// 모두 삭제
					for(FileBean fb : fList) {
						//DB > BRD_FILE 정보 삭제
						if(cDao.deleteBrdFile(fb.getFileNo())) {
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
				if(cDao.delBrdView(clsBrdNo)) {
					System.out.println("조회수 정보 삭제 완료.");
					//게시글정보(CLS_BRD) 삭제
					if(cDao.deleteBrd(clsBrdNo)) {
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
	 * (CM15)게시글 댓글 목록 삭제
	 * @Author 고연미 on 28/04/2021
	 */
	@Transactional
	private boolean deleteReplyList(String clsBrdNo, HttpServletRequest request) {
		boolean result = true;
		//댓글리스트 가져오기
		List<ReplyBean> rList = cDao.getReplyList(clsBrdNo);		
		//댓글리스트 정보가 존재하면
		if(!ObjectUtils.isEmpty(rList)) {
			//댓글리스트 삭제
			if(cDao.deleteReplyList(clsBrdNo)) {
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
	
	// (CM16)댓글 목록 가져오기(ajax)	
	public List<ReplyBean> getReplyList(String clsBrdNo) {
		List<ReplyBean> rList = cDao.getReplyList(clsBrdNo);
		/* clsBrdRepNo 기준 내림차순 정렬하기*/
        RListDecending rListDecending = new RListDecending();
        Collections.sort(rList, rListDecending); 
		return rList;
	}
	// (CM17)댓글 등록(ajax)	
	public List<ReplyBean> addReply(ReplyBean rb, MultipartHttpServletRequest mReq) {
		MultipartFile file = mReq.getFile("file");
		MemberBean mb = (MemberBean)mReq.getSession().getAttribute("loginData");
		rb.setMbId(mb.getMbId());
		if(file != null) {
			rb.setFileNo(fm.saveFile(mReq, file, 3));
		}
		cDao.addReply(rb);
		return getReplyList(rb.getClsBrdNo());
	}
	// (CM18)댓글 수정(ajax)			
	public List<ReplyBean> updateReply(ReplyBean rb, MultipartHttpServletRequest mReq) {
		MemberBean mb = (MemberBean)mReq.getSession().getAttribute("loginData");
		rb.setMbId(mb.getMbId());
		MultipartFile file = mReq.getFile("file");
		if(file != null) {
			rb.setFileNo(fm.saveFile(mReq, file, 3));
			ReplyBean rBean = cDao.getReply(rb.getClsBrdRepNo());
			fm.deleteFile(rBean.getFileNo(), mReq);
		}
		cDao.updateReply(rb);
		return getReplyList(rb.getClsBrdNo());
	}
	// (CM19)댓글 삭제(ajax)
	public List<ReplyBean> deleteReply(String clsBrdRepNo, HttpServletRequest req, String clsBrdNo) {
		MemberBean mb = (MemberBean)req.getSession().getAttribute("loginData");
		System.out.println("clsBrdNo =====> "+ clsBrdNo);
		System.out.println("clsBrdRepNo =====> "+ clsBrdRepNo);
		System.out.println("mbId =====> "+ mb.getMbId());
		cDao.deleteReply(clsBrdRepNo, mb.getMbId());
		return getReplyList(clsBrdNo);
	}
		
	// (CM20+CM21)출석 현황 이동 + 출석현황 가져오기
	public ModelAndView goAttend(HttpServletRequest request, String clsNo) {
		ModelAndView mav = new ModelAndView();
		MemberBean loginData = (MemberBean)request.getSession().getAttribute("loginData");
		String mbId = loginData.getMbId();
		
		if(clsNo!=null) {
		StudentBean attendInfo = cDao.getStudentInfo(clsNo, mbId);
		
		//출석률 구하기 & 출석현황 메시지 가져오기.
		float attendDay = (float)attendInfo.getAttendDay();
		float totalDay = (float)attendInfo.getTotalDay();
		float attendPercent = (attendDay/totalDay)*100;
		
		if(attendPercent>=80) {
			mav.addObject("attendMsg","정말 최고예요! (>ㅁ<)b ");
		}else if(attendPercent>=60 && attendPercent<80) {
			mav.addObject("attendMsg","노력하고 있군요~ ('ㅁ') ");
		}else if(attendPercent<60){
			mav.addObject("attendMsg","조금 아쉽네요... (TㅅT) ");
		}
		
		// 가져온 정보를 mav에 넣기	
		mav.addObject("attendInfo",attendInfo);
		//총수업일수가 0일경우 NaN 발생-->수업일수에 따라 따로 넣기
			if(totalDay==0) {
				mav.addObject("attendPercent","0");
			}else {
				mav.addObject("attendPercent",attendPercent);
			}
		}
		
		mav.addObject("navtext", "마이 클래스> 출석현황");
		//class_attend.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("class/class_attend"); // .jsp
		return mav;
	}
		
	// (CM22+CM23)학생목록 이동 + 학생목록 가져오기
	public ModelAndView goStudentList(String clsNo) {
		ModelAndView mav = new ModelAndView();
		List<StudentBean> sList = null;
		ClassBean clsInfo = cDao.getMyClassList(clsNo);
		sList = cDao.getStudentList(clsNo); 
		// 가져온 정보를 mav에 넣기
		mav.addObject("clsInfo",clsInfo);
		mav.addObject("sList",sList);
		mav.addObject("navtext", "클래스 관리> 마이 클래스> 학생");
		// class_studentinfo.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("class/class_studentinfo"); // .jsp
		return mav;
	}
		
	// (CM24+CM25)학생정보보기 이동 + 학생정보 가져오기
	public ModelAndView goStudentInfo(String mbId, String clsNo) {
		ModelAndView mav = new ModelAndView();
		StudentBean sInfo = cDao.getStudentInfo(clsNo,mbId);
		MemberBean mInfo = mDao.getMemberInfo(mbId);
		
		//출석률 구하기
		float attendDay = (float)sInfo.getAttendDay();
		float totalDay = (float)sInfo.getTotalDay();
		float attendPercent = (attendDay/totalDay)*100;
				
		// 가져온 정보를 mav에 넣기
		mav.addObject("sInfo",sInfo);
		mav.addObject("mInfo",mInfo);		
		//총수업일수가 0일경우 NaN 발생-->수업일수에 따라 따로 넣기
		if(totalDay==0) {
			mav.addObject("attendPercent","0");
		}else {
			mav.addObject("attendPercent",attendPercent);
		}
		mav.addObject("navtext", "클래스 관리> 마이 클래스> 학생");
		// class_studentinfo_read.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("class/class_studentinfo_read"); // .jsp
		return mav;
	}
		
	// (CM26)클래스 등록, 수정하기 페이지 이동
	public ModelAndView goClassInfoWrite(String clsNo) {
		ModelAndView mav = new ModelAndView();
		ClassBean clsInfo = new ClassBean();
		if(clsNo!=null) {
			clsInfo = cDao.getMyClassList(clsNo);
			mav.addObject("clsInfo",clsInfo);
			mav.addObject("navtext", "클래스 관리> 마이 클래스> 클래스 수정");
			
		}else { 
		mav.addObject("navtext", "클래스 관리> 클래스 개설");
		}
		mav.setViewName("class/class_write"); // .jsp
		return mav;
	}
	
	// (CM27)클래스 등록, 수정하기
	@Transactional
	public ModelAndView classClassinfoUpdate(MultipartHttpServletRequest mReq, HttpServletRequest request, ClassBean cb) {
		ModelAndView mav = new ModelAndView();
		MemberBean loginData= (MemberBean)mReq.getSession().getAttribute("loginData");
		cb.setMbId(loginData.getMbId());
		boolean updatedOrNot = true;
		MultipartFile mFile = mReq.getFile("file");
		String fileNo=null;
		
		//새로 등록할 클래스 이미지의 fileNo 
		if(mFile!=null) {	
			//클래스 이미지를 가져왔다면 saveFile()에 MultipartFile 전달, fileNo 반환받음
			//가져온 fileNo를 cb에 저장.
			cb.setFileNo(fm.saveFile(mReq, mFile, 1));	
		}
		
		//기존에 올려져 있던 fileNo 가져오기 (기존파일 삭제를 위해서...)
		if(cb.getClsNo()!=null && cb.getFileNo()!=null) {//clsNo가 null이 아니라면 DB로 가서 기존 클래스 정보의 fileNo를 가져옴
			fileNo = cDao.getFileNo(cb.getClsNo()); 
		}
												
		if(cb.getClsNo()!=null){//clsNo가 있다면 --> 수정(UPDATE) SQL문 실행
			updatedOrNot = cDao.classClassinfoUpdate(cb);
		}else {//clsNo가 없다면 --> 삽입(INSERT) SQL문 실행
			updatedOrNot = cDao.classClassinfoInsert(cb);
		}
	
		if(fileNo!=null) {  //DB에서 fileNo를 가져왔다면 deleteFile()에 넘겨 이미지파일 삭제		
			fm.deleteFile(fileNo, request);
		}
		
		if(updatedOrNot) { //등록(수정) 성공시
			mav.setViewName("redirect:/class/classinfo?clsNo="+cb.getClsNo()); //.jsp
			System.out.println("클래스 수정/등록 성공");
		}else { //등록(수정) 실패시
			mav.setViewName("redirect:class/class_wirte"); //.jsp
			System.out.println("클래스 수정/등록 실패");
		}
		return mav;
	}

	// (CM28)클래스 삭제
	@Transactional
	public ModelAndView classDelete(String clsNo, HttpServletRequest request, RedirectAttributes rattr) {
		ModelAndView mav = new ModelAndView();
		// getClassInfo()에 clsNo 넘겨 클래스 정보 가져오기
		ClassBean  classInfo = cDao.getClassInfo(clsNo);
		String fileNo = classInfo.getFileNo();
		int clsRegiCnt =classInfo.getClsRegiCnt();
		
		if(clsRegiCnt>=1){//클래스 clsRegiCnt 있으면 클래스 삭제 불가 메시지 띄우기 ->클래스 소개 화면으로 이동
			rattr.addFlashAttribute("fMsg", "수강 중인 회원이 있어 클래스를 삭제할 수 없습니다.");
			mav.setViewName("redirect:/myclass_t");
		}else{//클래스 clsRegiCnt 없으면 클래스 삭제 진행
					
				//1. 게시판 글 및 댓글 삭제
					//1-1. 게시글 목록 가져오기
					List<BoardBean> boardList = cDao.getBListForDelete(clsNo);	
					for(BoardBean bb : boardList) {
						//1-2. 게시글 댓글 목록 가져오기
						List<ReplyBean> replyList = cDao.getReplyList(bb.getClsBrdNo());
						//1-3. 게시글 댓글 목록 삭제하기(CM15)
						if(cDao.deleteReplyList(bb.getClsBrdNo())) {
							//1-4.댓글 첨부파일 삭제
							for(ReplyBean rb : replyList) {
								//fileNo가 존재하는 경우 파일 삭제
								if(!StringUtils.isEmpty(rb.getFileNo())){
									fm.deleteFile(rb.getFileNo(), request);
										}
									} //1-4 for문 End
								} //1-3 if문 End
						//1-5. 게시글 조회수 정보 삭제
						cDao.delBrdView(bb.getClsBrdNo()); 
						//1-6. 게시글 첨부파일 번호 가져오기(CM14 415라인 참조)
						List<FileBean> fileList = cDao.getBoardFiles(bb.getClsBrdNo());
						//1-7. 게시글 첨부파일 삭제(FM06)
						for(FileBean fb : fileList) {
							//BRD_FILE 정보 삭제
							if(cDao.deleteBrdFile(fb.getFileNo())) {
								//DB > TB_FILE 정보 삭제, 파일 삭제
								fm.deleteFile(fb.getFileNo(), request);
							}
						} //1-7 for문 End
						//1-8. 게시글 DB삭제하기(CM14)
						cDao.deleteBrd(bb.getClsBrdNo());
					} //게시판 글 및 댓글 삭제 End
			
			
				 //2. 커리큘럼 삭제
					//2-1. 커리큘럼 목록 가져오기(CR02)
					//List<CurriculumBean> curriList = crDao.getCurriculumList(clsNo);
					//2-2. 커리큘럼 삭제하기(CR11)
					//for(CurriculumBean crb : curriList){
					// 	crDao.classCurriculumDelete(crb.getCurNo());		
					//	}
								
				 //3.  클래스 삭제
					//3-1. 클래스 조회수 정보 삭제
					cDao.classViewDelete(clsNo);
					//3-2. 클래스 DB 삭제
						if(cDao.classDelete(clsNo)){//삭제 성공시
							//3-3. 클래스 이미지 삭제(FM06)			
							fm.deleteFile(fileNo, request);
							rattr.addFlashAttribute("fMsg", "클래스 삭제 성공!");
							//클래스 삭제 완료시 클래스 관리 페이지로 이동
							mav.setViewName("redirect:/myclass_t");		
							}else{
							rattr.addFlashAttribute("fMsg", "클래스 삭제 실패");
							mav.setViewName("redirect:/class/classinfo");
							}
		}
		return mav;
	} //classDelete End
	public Map<String, String> getOnStudents(String clsNo, String mbId) {
		Map<String, String> sMap = new HashMap<>();
		List<StudentBean> sList = null;
		//해당 클래스의 담당 강사가 맞는지 체크
		if(cDao.isCharge(clsNo, mbId) == 1) {
			//맞으면 로그인한 학생리스트 가져오기
			sList = cDao.getOnStudents(clsNo);
			int n = 1;
			for(StudentBean sb : sList) {
				sMap.put(n+"", sb.getMbId());
				n++;
			}
		}
		return sMap;
	}
}//Class End

/**
 * (CM04)게시판 목록 내림차순 정렬
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
/**
 * (CM04)댓글 목록 내림차순 정렬
 * @Author 고연미 on 06/05/2021
 * Comparator : 새로운 정렬기준으로 객체를 정렬하는 인터페이스
 */
class RListDecending implements Comparator<ReplyBean> {
 
    @Override
    public int compare(ReplyBean a, ReplyBean b) {
        Integer temp1 = Integer.parseInt(a.getClsBrdRepNo());
        Integer temp2 = Integer.parseInt(b.getClsBrdRepNo());   
        //compareTo : 두개의 값을 비교하여 int로 반환(크다(1), 같다(0), 작다(-1))
        return temp2.compareTo(temp1);
    } 
}

