package com.essam.www.eclass;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.b.IBDao;
import com.essam.www.bean.ClassBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.StudentBean;
import com.essam.www.bean.TeacherBean;
import com.essam.www.exception.CommonException;
import com.essam.www.file.FileMM;
import com.essam.www.member.IMemberDao;

@Service
public class ClassMM {
	@Autowired
	private FileMM fm;
	@Autowired
	private IMemberDao mDao;	
	@Autowired
	private IClassDao cDao;	
	
	
	@Autowired
	private IBDao bDao; //통합 후 삭제해야 할것.
	
	//**********고연미**********//
	// (CM01)클래스 소개 이동(관계자용)
	// (CM02+CM03)게시판(공지사항/과제) 목록 페이지 이동 + 게시판 목록 가져오기	
	// (CM04)게시판 목록 내림차순 정렬
	// (CM05)게시판 목록 페이징
	// (CM06+CM07)게시판(공지사항/과제) 상세 페이지 이동 + 과제 상세 가져오기
	// (CM08)조회수 추가
	// (CM09)수정/삭제 버튼 생성
	// (CM10)게시판(공지사항/과제) 등록/수정 페이지 이동
	// (CM11)게시판(공지사항/과제) 등록/수정
	// (CM12)(수정시)Ajax 첨부파일 리스트 가져오기
	// (CM13)(수정시)Ajax 첨부파일 삭제
	// (CM14)게시글(공지사항/과제) 삭제
	// (CM15)게시글 댓글 목록 삭제
	
	//**********임다영**********//
	// (CM16)댓글 목록 가져오기(ajax)	
	// (CM17)댓글 등록(ajax)	
	// (CM18)댓글 수정(ajax)	
	// (CM19)댓글 삭제(ajax)	
		
	
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
		String fileNo =null;
		
		//새로 등록할 클래스 이미지의 fileNo 
		if(mFile!=null) {	
			//클래스 이미지를 가져왔다면 saveFile()에 MultipartFile 전달, fileNo 반환받음
			//가져온 fileNo를 cb에 저장.
			cb.setFileNo(fm.saveFile(mReq, mFile, 1));	
		}
		
		//기존에 올려져 있던 fileNo 가져오기 (기존파일 삭제를 위해서...)
		if(cb.getClsNo()!=null && mFile!=null) {//clsNo가 null이 아니라면 DB로 가서 기존 클래스 정보의 fileNo를 가져옴
			fileNo = cDao.getFileNo(cb.getFileNo()); 	
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
	public ModelAndView classDelete(String clsNo, RedirectAttributes rattr) {
		ModelAndView mav = new ModelAndView();
		// getClassInfo()에 clsNo 넘겨 클래스 정보 가져오기
		ClassBean  classInfo = bDao.getClassInfo(clsNo); //파일 통합후 bDao-->cDao 변경요망
		String fileNo = classInfo.getFileNo();
		int clsRegiCnt =classInfo.getClsRegiCnt();
		
		if(clsRegiCnt>=1){//클래스 clsRegiCnt 있으면 클래스 삭제 불가 메시지 띄우기 ->클래스 소개 화면으로 이동
			rattr.addFlashAttribute("fMsg", "수강 중인 회원이 있어 클래스를 삭제할 수 없습니다.");
			mav.setViewName("redirect:/myclass_t");
		}else{//클래스 clsRegiCnt 없으면 클래스 삭제 진행
			//
			rattr.addFlashAttribute("fMsg", "클래스 삭제 성공!");
			//클래스 삭제 완료시 클래스 관리 페이지로 이동
			mav.setViewName("redirect:/myclass_t");
		}
		return mav;
	}
	
	
}
