package com.essam.www.c;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.CurriculumBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.TeacherBean;
import com.essam.www.exception.CommonException;
import com.essam.www.file.FileMM;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class CMM {
	@Autowired
	private ICDao mDao;

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
		
		if (mFile != null) { // 서버에 저장된 프로필이미지파일이 있으면
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

//클래스 커리큘럼 이동 + 커리큘럼 목록 가져오기
	public ModelAndView getClassCurriculumList(HttpSession session, String clsNo) {
		ModelAndView mav = new ModelAndView();
		List<CurriculumBean> curriInfo = null;
		
		// 세션에서 로그인 데이터를 MemberBean에 담기
		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
		// MemberBean으로 부터 mbType을 가져옴
		String mbType = loginData.getMbType() + "";
		// 가져온 정보를 mav에 넣기
		mav.addObject("mbType", mbType);
		
		curriInfo = mDao.getCurriculumList(clsNo);
		if (!ObjectUtils.isEmpty(curriInfo)) { // 커리큘럼 정보가 있다면
			// 가져온 정보를 mav에 넣기
			mav.addObject("curriInfo", curriInfo);
			// class_curriculum_read.jsp로 이동하기 위해 viewname 지정
			mav.setViewName("curriculum/curriculum_list"); // 커리큘럼보기 페이지로
			return mav;
		} else { // 등록된 커리큘럼 정보가 없다면
			String msg = "등록된 커리큘럼 정보가 없습니다.";
			mav.setViewName("curriculum/curriculum_list"); // 커리큘럼보기 페이지로
			mav.addObject("msg", msg);
			mav.addObject("clsNo", clsNo);
			return mav;
		}
		
	}

	//커리큘럼 상세정보 보기 이동 + 커리큘럼 상세정보 가져오기
	public ModelAndView getClassCurriculumRead(HttpSession session, String clsNo, String curNo) {
		ModelAndView mav = new ModelAndView();
		CurriculumBean curriInfo = null;
		
		// 세션에서 로그인 데이터를 MemberBean에 담기
		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
		// MemberBean으로 부터 mbType을 가져옴
		String mbType = loginData.getMbType() + "";
		// 가져온 정보를 mav에 넣기
		mav.addObject("mbType", mbType);
		
		curriInfo = mDao.getCurriculumRead(clsNo, curNo);
		if (curriInfo != null) { // 커리큘럼 상세정보가 있다면
			// 가져온 정보를 mav에 넣기
			mav.addObject("curriInfo", curriInfo);
			// curriculum_detail.jsp로 이동하기 위해 viewname 지정
			mav.setViewName("curriculum/curriculum_detail"); // 커리큘럼 상세정보 보기 페이지로
		} else { // 등록된 커리큘럼 상세정보가 없다면
			mav.setViewName("curriculum/curriculum_detail"); // 커리큘럼 상세정보 보기 페이지로
			mav.addObject("msg", "등록된 커리큘럼 상세정보가 없습니다");
		}
		return mav;
	}

	//커리큘럼 등록 이동
	public ModelAndView classCurriculumAdd(String clsNo) throws CommonException {
		ModelAndView mav = new ModelAndView();
		CurriculumBean curriInfo = null;
		curriInfo = mDao.getCurriculumAdd(clsNo);
		
		if (curriInfo != null) { // 커리큘럼 정보가 있다면(수정뷰)
			if(curriInfo.getCurTypeNo() == 1) { // 커리큘럼타입이 동영상이면, curTypeNo=1 : 동영상, curTypeNo=2 : 실시간
				// 가져온 정보를 mav에 넣기
				mav.addObject("curriInfo", curriInfo);
				// curriculum_write.jsp로 이동하기 위해 viewname 지정
				mav.setViewName("curriculum/curriculum_write"); // 커리큘럼 등록, 수정 페이지로
			}else if(curriInfo.getCurTypeNo() == 2) { //커리큘럼타입이 실시간이면
				// 가져온 정보를 mav에 넣기
				mav.addObject("curriInfo", curriInfo);
				// curriculum_write.jsp로 이동하기 위해 viewname 지정
				mav.setViewName("curriculum/curriculum_write"); // 커리큘럼 등록, 수정 페이지로
			}else { // 커리큐럼타입이 1 또는 2가 아닌 경우(정상적인 경우는 1 또는 2)
				throw new CommonException("커리큘럼타입 예외발생");	
			}
		}else { // 커리큘럼 정보가 없다면(등록뷰)
			mav.setViewName("curriculum/curriculum_write"); // 커리큘럼 등록, 수정 페이지로
			mav.addObject("msg", "등록된 커리큘럼 정보가 없습니다");
		}
		return mav;
	}



//동영상 페이지 이동
//동영상 제목,시작일,종료일 가져오기

//커리큘럼 등록
//커리큘럼 수정

}







