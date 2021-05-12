package com.essam.www.c;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.CurriculumBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.TeacherBean;
import com.essam.www.curriculum.CurriPaging;
import com.essam.www.eclass.IClassDao;
import com.essam.www.exception.CommonException;
import com.essam.www.file.FileMM;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class CMM {
	@Autowired
	private ICDao mDao;
	
	@Autowired
	private IClassDao cDao;

	@Autowired
	private FileMM fm;
	

	/**
	 * 교사프로필 가져오기 getTeacherProfile()
	 * @param HttpSession
	 * @param RedirectAttriutes
	 * @return ModelAndView
	 * @throws CommonException
	 */
	public ModelAndView getTeacherProfile(HttpSession session) throws CommonException {
		ModelAndView mav = new ModelAndView();
		TeacherBean teacherInfo = null;
		// 세션에서 로그인 데이터를 MemberBean에 담기
		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
		// MemberBean으로 부터 mbId, mbType을 가져옴
		String mbId = loginData.getMbId();
		// System.out.println("mbId");
		String mbType = loginData.getMbType() + "";
		// 강사회원(mbType==2)인지 확인하기
		if (mbType.equals("2")) { // 회원타입이 강사라면
			// getTeacherProfile()에 mbId 넘겨 강사프로필데이터 가져오기 dao에 요청
			teacherInfo = mDao.getTeacherProfile(mbId);
			if (teacherInfo != null) { // 강사프로필 정보가 있다면
				// 가져온 정보를 mav에 넣기
				mav.addObject("teacherInfo", teacherInfo);
				// teacher_profile.jsp로 이동하기 위해 viewname 지정
				mav.setViewName("member/teacher_profile"); // 강사프로필 페이지로
				return mav;
			} else { // 강사프로필 정보가 없다면(정상적인 경우 강사프로필 정보가 없는 경우는 없음)
				// mav.setViewName("member/teacher_profile"); //강사프로필 페이지로
				// mav.addObject("msg", "등록된 강사프로필 정보가 없습니다");
				// rattr.addFlashAttribute("fMsg", "등록된 강사프로필 정보가 없습니다");
				throw new CommonException("teacher 테이블 teacherGrade 예외발생");
			}
		}
		return mav;
	}
	
	/**
	 * 교사프로필 등록, 수정 이동하기 getTeacherProfileWrite()
	 * @param session
	 * @return ModelAndView
	 */
	public ModelAndView getTeacherProfileWrite(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		TeacherBean teacherInfo = null;
		// 세션에서 로그인 데이터를 MemberBean에 담기
		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
		// MemberBean으로 부터 mbId, mbType을 가져옴
		String mbId = loginData.getMbId();
		// System.out.println("mbId");
		teacherInfo = mDao.getTeacherProfile(mbId);
		if (teacherInfo != null) { // 강사프로필 정보가 있다면
			// 가져온 정보를 mav에 넣기
			mav.addObject("teacherInfo", teacherInfo);
			// teacher_profile.jsp로 이동하기 위해 viewname 지정
			mav.setViewName("member/teacher_profile_write"); // 강사프로필 페이지로
		}
		return mav;
	}

	/**
	 *  교사프로필 등록, 수정 teacherProfileUpdate()
	 * @param MultipartHttpServletRequest
	 * @param TeacherBeanb
	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@Transactional
	public ModelAndView teacherProfileUpdate(MultipartHttpServletRequest mReq, TeacherBean tb, HttpServletRequest request) {
		// <input type="file" name="속성"> --> mReq.getFile("속성")
		// 파라미터 가져오기 : mReq.getParameter(name)
		MultipartFile mFile = mReq.getFile("file");
		// image : 1
		// MemberBean loginData = (MemberBean)session.getAttribute("loginData");
		// session 객체로 부터 mbId 가져옴
		String mbId = ((MemberBean) mReq.getSession().getAttribute("loginData")).getMbId();
		System.out.println(mbId);
		// teacherBean에 mbid 저장
		tb.setMbId(mbId);
		// MemberDao에 mbId 가져가서 강사정보 요청
		TeacherBean teacherInfo = mDao.getTeacherProfile(mbId);
		
		if (mFile != null) { // 입력된 프로필이미지파일이 있으면
			// FileMM.saveFile()에 파라미터(mReq,mFile,1) 넘겨 fileNo 가져옴
			String fileNo = fm.saveFile(mReq, mFile, 1);
			// teacherBean에 가져온 fileNo 저장
			tb.setFileNo(fileNo);
		}

		log.info(tb.toString());
		// mDao.teacherProfileUpdate()에 TeacherBean 넘겨 수정요청
		boolean result = mDao.teacherProfileUpdate(tb);
		// 강사정보에 fileNo가 있고 서버에 저장된 프로필이미지파일이 있으면
		if (tb.getFileNo() != null && mFile != null) {
			// 서버에 저장된 프로필이미지파일 삭제요청
			fm.deleteFile(teacherInfo.getFileNo(), request);
		}
		// mav에 viewName을 /teacher_profile로 지정
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/teacher_profile");
		// mav 반환
		return mav;
	}

	/**
	 *  교사프로필 삭제하기 teacherProfileDelete()
	 *  @param HttpSession
	 *  @param HttpServletRequest
	 *  @throws CommonException
	 */
	@Transactional
	public ModelAndView teacherProfileDelete(HttpSession session, HttpServletRequest request) throws CommonException {
		ModelAndView mav = new ModelAndView();
		TeacherBean teacherInfo = null;
		// 세션에서 로그인 데이터를 MemberBean에 담기
		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
		// MemberBean으로 부터 mbId 가져옴
		String mbId = loginData.getMbId();
		// getTeacherProfile()에 mbId 넘겨 fileNo 가져오기
		teacherInfo = mDao.getTeacherProfile(mbId);
		String fileNo = teacherInfo.getFileNo();
		// DB에 mbId 가지고 가서 fileNo, teacherIntro, teacherDetail에 NULL값 UPDATE
		boolean isDeleteTeacherInfo = mDao.teacherInfoDelete(mbId);
		if (isDeleteTeacherInfo) { // DB에 UPDATE 성공하면
			// UPDATE 한 강사프로필 데이터 가져오기
			teacherInfo = mDao.getTeacherProfile(mbId);
			if (fileNo != null) { // 강사프로필에 fileNo가 있었다면
				// 서버에 저장된 이미지파일 삭제
				boolean isDeleteFile = fm.deleteFile(fileNo, request);
				if(isDeleteFile) { // 서버에 저장된 이미지파일 삭제 성공하면
					// 삭제한(정확히는 update한) 강사프로필 정보를 mav에 담기
					mav.addObject("teacherInfo", teacherInfo);
					// teacher_profile.jsp로 이동하기 위해 viewname 지정
					mav.setViewName("member/teacher_profile"); // 강사프로필 페이지로
				}else {
					System.out.println("서버에 저장된 이미지파일 삭제 실패");
				}
			}else { // 강사프로필에 fileNo가 없다면
				// 삭제한(정확히는 update한) 정보를 mav에 담기
				mav.addObject("teacherInfo", teacherInfo);
				// teacher_profile.jsp로 이동하기 위해 viewname 지정
				mav.setViewName("member/teacher_profile"); // 강사프로필 페이지로
			}
			return mav;
		}else { // DB에 UPDATE 실패하면
			throw new CommonException("DB에 UPDATE 실패 예외발생");
		}	
	}



//커리큘럼------------------------------------------------------------------
	
	
	
	private String getPaging(String clsNo, Integer pageNum, int totalNum, HttpServletRequest request) {
		//전체 글 갯수 가져오기
		int listCount = 10; //페이지당 나타낼 글의 갯수
		int pageCount = 10;	//페이지그룹당 페이지 갯수
		
		//Paging 클래스 객체 생성해서 page makeHhml 리턴
		CurriPaging paging = new CurriPaging(totalNum, pageNum, listCount, pageCount, clsNo);
		return paging.makeHtmlPaging(request);
	}

//클래스 커리큘럼 이동 + 커리큘럼 목록 가져오기
	public ModelAndView getClassCurriculumList(String clsNo, Integer pageNum, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		List<CurriculumBean> curriInfo = null;

		//pageNum이 null이면 1로 세팅
		pageNum = (pageNum==null)? 1 : pageNum;
		
		if(pageNum<=0) {
			throw new CommonException("잘못된 페이지번호 입니다.");
		}
		//전체 글 갯수 가져오기
		int totalNum = mDao.getCurriculumCount(clsNo);
		
//		// 세션에서 로그인 데이터를 MemberBean에 담기
//		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
//		// MemberBean으로 부터 mbType을 가져옴
//		String mbType = loginData.getMbType() + "";
//		// 가져온 정보를 mav에 넣기
//		mav.addObject("mbType", mbType);
		
		curriInfo = mDao.getCurriculumList(clsNo, pageNum);
		
		/* 내림차순 정렬하기*/
        CurListAscending curListAscending = new CurListAscending();
        Collections.sort(curriInfo, curListAscending); 
		
		if (!ObjectUtils.isEmpty(curriInfo)) { // 커리큘럼 정보가 있다면
			// 가져온 정보를 mav에 넣기
			mav.addObject("curriInfo", curriInfo);
			mav.addObject("clsNo", clsNo);
			mav.addObject("pageNum", pageNum);
			// class_curriculum_read.jsp로 이동하기 위해 viewname 지정
			mav.setViewName("curriculum/curriculum_list"); // 커리큘럼보기 페이지로
			mav.addObject("navtext", "마이클래스> 커리큘럼");
			mav.addObject("clsName", cDao.getClassName(clsNo));
			//mav에 페이징 정보 저장
		    mav.addObject("paging", getPaging(clsNo, pageNum, totalNum, request));
			return mav;
		} else { // 등록된 커리큘럼 정보가 없다면
			String msg = "등록된 커리큘럼 정보가 없습니다.";
			mav.setViewName("curriculum/curriculum_list"); // 커리큘럼보기 페이지로
			//mav에 클래스명 추가
			mav.addObject("clsName", cDao.getClassName(clsNo));
			mav.addObject("clsNo", clsNo);
			mav.addObject("msg", msg);
			mav.addObject("navtext", "마이클래스> 커리큘럼");			
			return mav;
		}
		
	}

	//커리큘럼 상세정보 보기 이동 + 커리큘럼 상세정보 가져오기
	public ModelAndView getClassCurriculumRead(String clsNo, String curNo, Integer pageNum) {
		ModelAndView mav = new ModelAndView();
		CurriculumBean curriInfo = null;
		
//		// 세션에서 로그인 데이터를 MemberBean에 담기
//		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
//		// MemberBean으로 부터 mbType을 가져옴
//		String mbType = loginData.getMbType() + "";
//		// 가져온 정보를 mav에 넣기
//		mav.addObject("mbType", mbType);
		
		curriInfo = mDao.getCurriculumRead(clsNo, curNo);
		if (curriInfo != null) { // 커리큘럼 상세정보가 있다면
			// 가져온 정보를 mav에 넣기
			mav.addObject("curriInfo", curriInfo);
			mav.addObject("pageNum", pageNum);
			// curriculum_detail.jsp로 이동하기 위해 viewname 지정
			mav.setViewName("curriculum/curriculum_detail"); // 커리큘럼 상세정보 보기 페이지로
			mav.addObject("clsName", cDao.getClassName(clsNo));
			mav.addObject("navtext", "마이클래스> 커리큘럼> 상세정보");	
		} else { // 등록된 커리큘럼 상세정보가 없다면
			mav.addObject("pageNum", pageNum);
			mav.setViewName("curriculum/curriculum_detail"); // 커리큘럼 상세정보 보기 페이지로(curriculum_list.jsp에서 상세정보 required라 없을 수 없음)
			mav.addObject("msg", "등록된 커리큘럼 상세정보가 없습니다");
			mav.addObject("clsName", cDao.getClassName(clsNo));
			mav.addObject("navtext", "마이클래스> 커리큘럼> 상세정보");	
		}
		return mav;
	}

	//커리큘럼 등록 이동
	public ModelAndView goClassCurriculumWrite(String clsNo, Integer pageNum) {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("clsNo", clsNo);
		mav.addObject("pageNum", pageNum);
		//mav에 클래스명 추가
		mav.addObject("clsName", cDao.getClassName(clsNo));
		mav.addObject("navtext", "마이클래스> 커리큘럼> 등록");
		mav.setViewName("curriculum/curriculum_write");
		return mav;
	}
	
	//커리큘럼 등록
	public ModelAndView ClassCurriculumAdd(MultipartHttpServletRequest mReq, CurriculumBean cb, HttpServletRequest request, RedirectAttributes rattr, Integer pageNum) throws CommonException {
		ModelAndView mav = new ModelAndView();
		boolean result = false;  //게시판 저장 결과
		int fileTypeNo = 2;      //파일타입번호 2(동영상)
		String curNo = null;	 //커리큘럼번호
		//int pageNum = Integer.parseInt(mReq.getParameter("pageNum"));
		
		if(cb.getCurTypeNo() == 1) { //커리큘럼타입이 동영상 이면
			//커리큘럼 등록 성공하면
			if(mDao.classCurriculumAdd(cb)) {
				result = true;
				curNo = cb.getCurNo();
			}
			log.info("커리큘럼등록 성공여부 : " + result);	
			
			//커리큘럼 등록 성공하고, 첨부한 동영상파일이 있다면
			if(result && mReq.getFiles("file").get(0).getSize() != 0) {
				//파일 가져오기
				MultipartFile file = mReq.getFile("file");
				
				//여러개일 경우 한개씩 서버에 저장 for each
				
				log.info("file name= "+ file.getOriginalFilename());
				//파일 저장 => fileNo 반환
				String fileNo = fm.saveFile(mReq, file, fileTypeNo);
				
				if(fileNo != null) {
					//DB에 파일정보 저장
					if(mDao.curFileInsert(curNo, fileNo)) {
						log.info("file 저장 후 curri 테이블에 fileNo 저장 완료.");
					}
				}else {
					log.info("file 저장 후 curri 테이블에 fileNo 저장 실패.");
				}
				
			}
			if(result) {
				rattr.addFlashAttribute("fMsg", "커리큘럼을 등록하였습니다.");
				mav.setViewName("redirect:/class/curriculum?pageNum=1&clsNo=" + cb.getClsNo());
			}else {
				rattr.addFlashAttribute("fMsg", "커리큘럼 등록을 실패하였습니다. \\n문제가 지속된다면 관리자에게 문의 바랍니다.");
				//Referer : 이전 페이지에 대한 정보가 전부 들어있는 헤더
				String referer = request.getHeader("Referer");
				//view 페이지 ㅣ설정
				mav.setViewName("redirect:" + referer);
			}
			//rattr에 커리큘럼번호 추가
			//rattr.addFlashAttribute("curNo", curNo);
			//rattr에 페이지 넘버 추가
			//rattr.addFlashAttribute("pageNum", pageNum);
		}else if(cb.getCurTypeNo() == 2) { //커리큘럼타입이 실시간 이면
			String sDate = cb.getCurStartDate().replace('T', ' ');
			cb.setCurStartDate(sDate);
			String eDate = cb.getCurEndDate().replace('T', ' ');
			cb.setCurEndDate(eDate);
			
			if(mDao.classCurriculumAdd(cb)) {
				result = true;
				curNo = cb.getCurNo();
			}
			log.info("커리큘럼등록 성공여부 : " + result);	
			
			if(result) { //커리큘럼 등록 성공하면
				rattr.addFlashAttribute("fMsg", "커리큘럼을 등록하였습니다.");
				mav.setViewName("redirect:/class/curriculum?pageNum=1&clsNo=" + cb.getClsNo());
			}else { //커리큘럼 등록 실패하면
				rattr.addFlashAttribute("fMsg", "커리큘럼 등록을 실패하였습니다. \\n문제가 지속된다면 관리자에게 문의 바랍니다.");
				//Referer : 이전 페이지에 대한 정보가 전부 들어있는 헤더
				String referer = request.getHeader("Referer");
				//view 페이지 ㅣ설정
				mav.setViewName("redirect:" + referer);
			}
		}else { //커리큘럼타입 예외처리
			throw new CommonException("커리큘럼타입 예외발생");
		}
		return mav;
	}
	
	//커리큘럼 수정 이동
	public ModelAndView goClassCurriculumUpdate(String clsNo, String curNo, Integer pageNum) {
		ModelAndView mav = new ModelAndView();
		CurriculumBean curriInfo = null;
		mav.addObject("pageNum", pageNum);
		
		curriInfo = mDao.getCurriculumRead(clsNo, curNo);
		
		
		if(curriInfo != null && curriInfo.getCurTypeNo()==1) { //등록된 커리큘럼이 있고 동영상수업 이라면
			String curStartDate = curriInfo.getCurStartDate().substring(0,10);
			String curEndDate = curriInfo.getCurEndDate().substring(0,10);
			
			mav.addObject("curStartDate", curStartDate);
			mav.addObject("curEndDate", curEndDate);
			mav.addObject("curriInfo", curriInfo);
			mav.setViewName("curriculum/curriculum_update");
			mav.addObject("clsNo", clsNo);
			//mav에 클래스명 추가
			mav.addObject("clsName", cDao.getClassName(clsNo));
			mav.addObject("navtext", "마이클래스> 커리큘럼> 수정");
		}else if(curriInfo != null && curriInfo.getCurTypeNo()==2) { //등록된 커리큘럼이 있고 실시간수업 이라면
			String curStartDate = curriInfo.getCurStartDate().replace(" ", "T");
			String curEndDate = curriInfo.getCurEndDate().replace(" ", "T");
			
			mav.addObject("curStartDate", curStartDate);
			mav.addObject("curEndDate", curEndDate);
			mav.addObject("curriInfo", curriInfo);
			mav.setViewName("curriculum/curriculum_update");
			mav.addObject("clsNo", clsNo);
			//mav에 클래스명 추가
			mav.addObject("clsName", cDao.getClassName(clsNo));
			mav.addObject("navtext", "마이클래스> 커리큘럼> 수정");
		}else { //등록된 커리큘럼이 없으면
			mav.setViewName("curriculum/curriculum_write");
			mav.addObject("clsNo", clsNo);
			//mav에 클래스명 추가
			mav.addObject("clsName", cDao.getClassName(clsNo));
			mav.addObject("navtext", "마이클래스> 커리큘럼> 등록");
		}
		return mav;
	}
		
	//커리큘럼 수정
	public ModelAndView ClassCurriculumUpdateServer(MultipartHttpServletRequest mReq, CurriculumBean cb, HttpServletRequest request, RedirectAttributes rattr) throws CommonException {
		ModelAndView mav = new ModelAndView();
		boolean result = false;  //게시판 수정 결과
		boolean isDeleteVideo = false; //이전 동영상 삭제 결과
		String clsNo = cb.getClsNo();
		String curNo = mReq.getParameter("curNo");
		//int pageNum = Integer.parseInt(mReq.getParameter("pageNum"));
		//mav.addObject("pageNum", pageNum);
		
		MultipartFile mFile = mReq.getFile("file");
		CurriculumBean curriInfo = mDao.getCurriculumRead(clsNo, curNo);
		
		if(cb.getCurTypeNo() == 1) { //커리큘럼타입이 동영상 이면
			if(mReq.getFile("file").getSize() != 0) { //입력값에 동영상 파일이 있으면
				String fileNo = fm.saveFile(mReq, mFile, 2); //2: 동영상
				// curriculumBean cb에 가져온 fileNo 저장
				cb.setFileNo(fileNo);
				
				if(mDao.classCurriculumUpdateServer(cb)) { //커리큘럼 수정 성공하면
					result = true;
					// 서버에 저장된 이전 동영상파일 삭제요청
					isDeleteVideo = fm.deleteFile(curriInfo.getFileNo(), request);
					log.info("이전 동영상 삭제여부 : " + isDeleteVideo);
				}else { //커리큘럼 수정 실패하면
					rattr.addFlashAttribute("fMsg", "커리큘럼 수정이 실패하였습니다. \\n문제가 지속된다면 관리자에게 문의 바랍니다.");
					//Referer : 이전 페이지에 대한 정보가 전부 들어있는 헤더
					String referer = request.getHeader("Referer");
					//view 페이지 ㅣ설정
					mav.setViewName("redirect:" + referer);
				}
			}else if(mReq.getFile("file").getSize() == 0) { //입력값에 동영상 파일이 없으면
				result = mDao.classCurriculumUpdateServer(cb);
			}else {
				throw new CommonException("커리큘럼 수정 동영상파일 예외발생");
			}
			log.info("커리큘럼수정 성공여부 : " + result);				
			
			if(result) {
				rattr.addFlashAttribute("fMsg", "커리큘럼을 수정하였습니다.");
				mav.setViewName("redirect:/class/curriculum?pageNum=1&clsNo=" + cb.getClsNo());
				//mav에 클래스명 추가
				mav.addObject("clsName", cDao.getClassName(clsNo));
				mav.addObject("navtext", "마이클래스> 커리큘럼");
			}else {
				rattr.addFlashAttribute("fMsg", "커리큘럼 수정이 실패하였습니다. \\n문제가 지속된다면 관리자에게 문의 바랍니다.");
				//Referer : 이전 페이지에 대한 정보가 전부 들어있는 헤더
				String referer = request.getHeader("Referer");
				//view 페이지 ㅣ설정
				mav.setViewName("redirect:" + referer);
			}
			//rattr에 커리큘럼번호 추가
			//rattr.addFlashAttribute("curNo", curNo);
			//rattr에 페이지 넘버 추가
			//rattr.addFlashAttribute("pageNum", pageNum);
			
		}else if(cb.getCurTypeNo() == 2) { //커리큘럼타입이 실시간 이면
			String sDate = cb.getCurStartDate().replace('T', ' ');
			cb.setCurStartDate(sDate);
			String eDate = cb.getCurEndDate().replace('T', ' ');
			cb.setCurEndDate(eDate);
			
			if(mDao.classCurriculumUpdateServerSil(cb)) {
				result = true;
			}
			log.info("커리큘럼 수정 성공여부 : " + result);	
			
			if(result) { //커리큘럼 수정 성공하면
				rattr.addFlashAttribute("fMsg", "커리큘럼을 수정하였습니다.");
				mav.setViewName("redirect:/class/curriculum?pageNum=1&clsNo=" + cb.getClsNo());
				//mav에 클래스명 추가
				mav.addObject("clsName", cDao.getClassName(clsNo));
				mav.addObject("navtext", "마이클래스> 커리큘럼");
			}else { //커리큘럼 수정 실패하면
				rattr.addFlashAttribute("fMsg", "커리큘럼 수정이 실패하였습니다. \\n문제가 지속된다면 관리자에게 문의 바랍니다.");
				//Referer : 이전 페이지에 대한 정보가 전부 들어있는 헤더
				String referer = request.getHeader("Referer");
				//view 페이지 ㅣ설정
				mav.setViewName("redirect:" + referer);
			}
		}else { //커리큘럼타입 예외처리
			throw new CommonException("커리큘럼타입 예외발생");
		}
		return mav;
	}
	
		

	//동영상 페이지 이동 + 동영상 제목,시작일,종료일 가져오기
	public ModelAndView goClassVideoPlay(String clsNo, String curNo, HttpSession session, Integer pageNum) {
		ModelAndView mav = new ModelAndView();
		CurriculumBean curriInfo = null;
		mav.addObject("pageNum", pageNum);
		
		if(mDao.isCurriTime(curNo)) { //동영상 시청기간 이라면
			curriInfo = mDao.getCurriculumRead(clsNo, curNo);
			String curTitle = curriInfo.getCurTitle();
			
			mav.addObject("clsNo", clsNo);
			mav.addObject("curriInfo", curriInfo);
			mav.addObject("msg", curTitle);
		}else { //동영상 시청기간이 아니라면
			mav.addObject("msg", "동영상 시청 기간이 아닙니다.");
		}
		
		// 세션에서 로그인 데이터를 MemberBean에 담기
		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
		// MemberBean으로 부터 mbType을 가져옴
		int mbType = loginData.getMbType();
		String mbId = loginData.getMbId();
		String regiNo = mDao.getRegiNo(clsNo, mbId);
		//출석테이블에 출석기록하기
		if(mbType==1) { //학생이면
			//수업시간인지 확인(수업시간에만 허용)
			if(mDao.isCurriTime(curNo)) { //true면 수업시간, false면 수업시간 아님
				//이미 출석여부 확인
				if(!mDao.isAttendAlready(curNo, regiNo)) { //출석한적 없다면
					//attend 테이블에 curNo, regiNo 보내어 DB 저장(이미 저장되어 있으면 패스)
					mDao.addAttend(curNo, regiNo);  //attend 테이블에 insert(출석등록)
				}
			}	
		}
		//mav에 클래스명 추가
		mav.addObject("clsName", cDao.getClassName(clsNo));
		mav.addObject("navtext", "마이클래스> 커리큘럼> 동영상");	
		mav.setViewName("curriculum/curriculum_videoplay");
		return mav;
	}

	//줌링크 이동
	public ModelAndView goClassZoomLink(String clsNo, String curNo, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		//mbType 확인(학생만 허용)
		//CurriculumBean curriInfo = null;
		
		// 세션에서 로그인 데이터를 MemberBean에 담기
		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
		// MemberBean으로 부터 mbType을 가져옴
		int mbType = loginData.getMbType();
		String mbId = loginData.getMbId();
		String regiNo = mDao.getRegiNo(clsNo, mbId);
		//출석테이블에 출석기록하기
		if(mbType==1) { //학생이면
			//수업시간인지 확인(수업시간에만 허용)
			if(mDao.isCurriTime(curNo)) { //true면 수업시간, false면 수업시간 아님
				//이미 출석여부 확인
				if(!mDao.isAttendAlready(curNo, regiNo)) { //출석한적 없다면
					//attend 테이블에 curNo, regiNo 보내어 DB 저장(이미 저장되어 있으면 패스)
					mDao.addAttend(curNo, regiNo);  //attend 테이블에 insert(출석등록)
				}
			}	
		}
		//줌링크 가져오기
		String zoomLink = mDao.getZoomLink(clsNo);
		//줌링크로 redirect
		mav.setViewName("redirect:"+ zoomLink);
		return mav;
	}

	//커리큘럼 삭제
	public ModelAndView classCurriculumDelete(String clsNo, String curNo, HttpServletRequest request, RedirectAttributes rattr) throws CommonException {
		ModelAndView mav = new ModelAndView();
		boolean result = false;  //커리큘럼 삭제 결과
		boolean isDeleteVideo = false; //동영상 삭제 결과
		CurriculumBean curriInfo = null;
		// getCurriculumRead()에 clsNo, curNo 넘겨 fileNo 가져오기
		curriInfo = mDao.getCurriculumRead(clsNo, curNo);
		String fileNo = curriInfo.getFileNo();
		// DB에 clsNo,curNo 가지고 가서 커리큘럼 삭제
		boolean isDeletecurriculumInfo = mDao.curriculumDelete(curNo);
		if (isDeletecurriculumInfo) { // DB 삭제 성공하면
			if (fileNo != null) { // 커리큘럼에 fileNo가 있다면
				// 서버에 저장된 동영상파일 삭제
				isDeleteVideo = fm.deleteFile(fileNo, request);
				if(isDeleteVideo) { // 서버에 저장된 동영상파일 삭제 성공하면
					result = true;
				}else {
					throw new CommonException("커리큘럼 동영상파일 삭제 예외발생");
				}
			}else { // 커리큘럼에 fileNo가 없다면
				result = true;
			}
		}else { // DB에 삭제 실패하면
			throw new CommonException("커리큘럼정보 DB 삭제 예외발생");
		}
		
		if(result) { //커리큘럼 삭세 성공하면
			rattr.addFlashAttribute("fMsg", "커리큘럼을 삭제하였습니다.");
			mav.setViewName("redirect:/class/curriculum?pageNum=1&clsNo=" + clsNo);
			//mav에 클래스명 추가
			mav.addObject("clsName", cDao.getClassName(clsNo));
			mav.addObject("navtext", "마이클래스> 커리큘럼");	
		}else { //커리큘럼 삭제 실패하면
			rattr.addFlashAttribute("fMsg", "커리큘럼 삭제를 실패하였습니다. \\n문제가 지속된다면 관리자에게 문의 바랍니다.");
			//Referer : 이전 페이지에 대한 정보가 전부 들어있는 헤더
			String referer = request.getHeader("Referer");
			//view 페이지 ㅣ설정
			mav.setViewName("redirect:" + referer);
		}
		return mav;
	}
	

}


//sort(내림차순 정렬 클래스)
class CurListAscending implements Comparator<CurriculumBean> {
	 
    @Override
    public int compare(CurriculumBean a, CurriculumBean b) {
        String temp1 = a.getCurStartDate();
        String temp2 = b.getCurStartDate();   
        //compareTo : 두개의 값을 비교하여 int로 반환(크다(1), 같다(0), 작다(-1))
        return temp1.compareTo(temp2);
    } 
}




