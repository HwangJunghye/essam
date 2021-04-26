package com.essam.www.eclass;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.ClassBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.StudentBean;
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
	// (CM01)공지사항 쓰기, 수정페이지 이동	
	// (CM02)클래스 소개 이동(관계자용)	
	// (CM03+CM04)과제 목록 페이지 이동 + 과제 목록 가져오기		
	// (CM05+CM06)과제 상세 페이지 이동 + 과제 상세 가져오기
	// (CM07)댓글 목록 가져오기(ajax)	
	// (CM08)댓글 등록(ajax)	
	// (CM09)댓글 수정(ajax)	
	// (CM10)댓글 삭제(ajax)		
	// (CM11)과제 수정,등록	
	// (CM12)과제 삭제
	// (CM11)공지사항 수정, 등록	
	// (CM12)공지사항 삭제
	// (CM19+CM04)공지사항 목록 페이지 이동 + 공지사항 목록 가져오기	
	// (CM20+CM06)공지사항 상세 페이지 이동 + 공지사항 상세 가져오기	
	
	
	// (CM13+CM14)출석 현황 이동 + 출석현황 가져오기
	public ModelAndView goAttend(HttpServletRequest request, String clsNo) {
		ModelAndView mav = new ModelAndView();
		MemberBean loginData = (MemberBean)request.getSession().getAttribute("loginData");
		String mbId = loginData.getMbId();
		
		if(clsNo!=null) {
		StudentBean attendInfo = cDao.getStudentInfo(clsNo, mbId);
		
		//출석률 구하기 & 출석현황 메시지 가져오기.
		int attendDay = attendInfo.getAttendDay();
		int totalDay = attendInfo.getTotalDay();
		int attendPercent = (attendDay/totalDay)*100;
		mav.addObject("attendPercent",attendPercent);
		
		if(attendPercent<=80) {
			mav.addObject("attendMsg","정말 최고예요! (>ㅁ<)b ");
		}else if(attendPercent>=60 && attendPercent<80) {
			mav.addObject("attendMsg","노력하고 있군요~ ('ㅁ') ");
		}else if(attendPercent<60){
			mav.addObject("attendMsg","조금 아쉽네요... (TㅅT) ");
		}
		
		// 가져온 정보를 mav에 넣기
		mav.addObject("attendInfo",attendInfo);
		}	
		mav.addObject("navtext", "마이 클래스> 출석현황");
		//class_attend.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("class/class_attend"); // .jsp
		return mav;
	}
		
	// (CM15+CM16)학생목록 이동 + 학생목록 가져오기
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
		
	// (CM17+CM18)학생정보보기 이동 + 학생정보 가져오기
	public ModelAndView goStudentInfo(HttpServletRequest request, String clsNo) {
		ModelAndView mav = new ModelAndView();
		MemberBean loginData = (MemberBean)request.getSession().getAttribute("loginData");
		String mbId = loginData.getMbId(); 
		StudentBean sInfo = cDao.getStudentInfo(clsNo,mbId);
		MemberBean mInfo = mDao.getMemberInfo(mbId);
		
		// 가져온 정보를 mav에 넣기
		mav.addObject("sInfo",sInfo);
		mav.addObject("mInfo",mInfo);
		mav.addObject("navtext", "클래스 관리> 마이 클래스> 학생");
		// class_studentinfo_read.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("class/class_studentinfo_read"); // .jsp
		return mav;
	}
		
	// (CM21)클래스 등록, 수정하기 페이지 이동
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
	
	// (CM22)클래스 등록, 수정하기
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
		}else {//clsNo가 있다면 --> 삽입(INSERT) SQL문 실행
			updatedOrNot = cDao.classClassinfoInsert(cb);
		}
	
		if(fileNo!=null) {  //DB에서 fileNo를 가져왔다면 deleteFile()에 넘겨 이미지파일 삭제		
			fm.deleteFile(fileNo, request);
		}
		
		if(updatedOrNot) { //등록(수정) 성공시
			mav.setViewName("class/classinfo_t"); //.jsp
		}else { //등록(수정) 실패시
			mav.setViewName("class/class_wirte"); //.jsp
		}
		return mav;
	}
}
