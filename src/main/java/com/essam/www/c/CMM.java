package com.essam.www.c;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public ModelAndView teacherProfileUpdate(MultipartHttpServletRequest mReq, TeacherBean tb, HttpServletRequest request) {
		// <input type="file" name="속성"> --> mReq.getFile("속성")
		// 파라미터 가져오기 : mReq.getParameter(name)
		MultipartFile mFile = mReq.getFile("file");
		// image : 1
		// MemberBean loginData = (MemberBean)session.getAttribute("loginData");
		String mbId = ((MemberBean) mReq.getSession().getAttribute("loginData")).getMbId();
		System.out.println(mbId);
		tb.setMbId(mbId);

		TeacherBean teacherInfo = mDao.getTeacherProfile(mbId);

		if (mFile != null) {
			String fileNo = fm.saveFile(mReq, mFile, 1);
			tb.setFileNo(fileNo);
		}

		log.info(tb.toString());
		boolean result = mDao.teacherProfileUpdate(tb);
		
		if (teacherInfo.getFileNo() != null && mFile != null) {
			fm.deleteFile(teacherInfo.getFileNo(), request);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/teacher_profile");
		return mav;
	}

	/**
	 *  교사프로필 삭제하기 teacherProfileDelete()
	 *  @param HttpSession
	 *  @param HttpServletRequest
	 *  @throws CommonException
	 */
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
			}else {
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
}













