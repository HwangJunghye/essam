package com.essam.www.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.ClassBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.TeacherBean;
import com.essam.www.exception.CommonException;
import com.essam.www.file.FileMM;

@Service
public class MemberMM {
	@Autowired
	private IMemberDao mDao;
	
	@Autowired
	private FileMM fm;
	
	// (MM01)로그인 이동-해당없음
	// (MM02)회원가입 이동-해당없음
	// (MM04)로그아웃-해당없음

	// (MM07)수강신청 실행

	// (MM09)계정관리 이동
	// (MM10)회원정보 수정 실행
	// (MM11)회원정보 가져오기
	
	
	

	// (MM03)이메일 중복체크(ajax)
	public Map<String, String> checkEmail(String mbId) {
		Map<String, String> hMap = new HashMap<>();
		boolean isExist = mDao.checkEmail(mbId);
		if (isExist) {
			hMap.put("msg", "존재하는 이메일입니다.");
		} else {
			hMap.put("msg", "사용가능한 이메일입니다.");
		}
		return hMap;
	}

	// (MM05)로그인 실행
	public ModelAndView access(MemberBean mb, HttpServletRequest request, RedirectAttributes rattr) {
		ModelAndView mav = new ModelAndView();
		MemberBean mbInfo = mDao.getMemberInfo(mb.getMbId());
		if (mbInfo != null) { // 아이디 정보가 존재할 경우
			// 암호화 라이브러리 가져오기
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (encoder.matches(mb.getMbPwd(), mbInfo.getMbPwd())) {
				// 비밀번호가 일치 한다면
				MemberBean loginData = new MemberBean();
				loginData.setMbId(mbInfo.getMbId());
				loginData.setMbType(mbInfo.getMbType());
				loginData.setMbName(mbInfo.getMbName());
				loginData.setMbNickName(mbInfo.getMbNickName());
				request.getSession().setAttribute("loginData", loginData);
				mav.setViewName("redirect:/"); // 메인으로
				rattr.addFlashAttribute("fMsg", "로그인 성공");

//				Referer : 이전 페이지에 대한 정보가 전부 들어있는 헤더
//				String referer = request.getHeader("Referer");
//				mav.setViewName("redirect:"+ referer);
			} else { // 비밀번호 불일치시
				mav.setViewName("redirect:/"); // 로그인 페이지로
				rattr.addFlashAttribute("fMsg", "로그인 실패");
				rattr.addFlashAttribute("modal", "모달창띄우기");
			}
		} else { // 아이디가 없는 경우
			mav.setViewName("redirect:/"); // 로그인 페이지로
			rattr.addFlashAttribute("fMsg", "로그인 실패");
			rattr.addFlashAttribute("modal", "모달창띄우기");
		}
		return mav;
	}

	// (MM06)회원가입 실행
	public ModelAndView memberJoin(MemberBean mb, RedirectAttributes rattr) {
		ModelAndView mav = new ModelAndView();
		// 비밀번호 암호화 라이브러리 불러오기
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// 비밀번호 암호화
		String encPwd = encoder.encode(mb.getMbPwd());
		mb.setMbPwd(encPwd);
		if (mDao.memberJoin(mb)) { // 회원등록 성공시
			// 관심카테고리1 저장
			if (mb.getCate1No() != null) {
				for (int cate1 : mb.getCate1No()) {
					mDao.putInterCate(cate1, "INTER_CATE1", mb.getMbId());
				}
			}
			// 관심카테고리2 저장
			if (mb.getCate2No() != null) {
				for (int cate2 : mb.getCate2No()) {
					mDao.putInterCate(cate2, "INTER_CATE2", mb.getMbId());
				}
			}
			// 강사회원인 경우 TEACHER 테이블에 정보 저장
			if (mb.getMbType() == 2) {
				mDao.putTeacher(mb.getMbId());
			}
			rattr.addFlashAttribute("fMsg", "회원가입 성공");
			mav.setViewName("redirect:/");
		} else {
			rattr.addFlashAttribute("fMsg", "회원가입 실패");
			mav.setViewName("redirect:/join");
		}
		return mav;
	}

	// (MM08)비밀번호 변경 실행(ajax)
	@Transactional
	public Map<String, String> changePassword(HttpSession session, String mbPwd, String newMbPwd) {
		Map<String, String> map = new HashMap<>();
		boolean flag = false; // 비밀번호 변경 성공여부 저장
		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
		if (loginData == null) {
			map.put("msg", "로그인 상태가 아닙니다");
			return map;
		}

		MemberBean mb = mDao.getMemberInfo(loginData.getMbId());
		if (mb == null) {
			map.put("msg", "회원정보가 존재하지 않습니다.");
			return map;
		}

		// 비밀번호 인코더 불러오기
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		if (encoder.matches(mbPwd, mb.getMbPwd())) { // 비밀번호가 일치하는 경우
			// 비밀번호 변경하기
			String encPw = encoder.encode(newMbPwd); // 새로운 비밀번호 암호화
			if (mDao.changePassword(loginData.getMbId(), encPw)) { // 변경 실행
				flag = true;
			}
		}

		if (flag) { // 변경 성공시
			map.put("msg", "비밀번호 변경 성공");
		} else { // 변경 실패시
			map.put("msg", "비밀번호 변경 실패");
		}
		return map;
	}

	// (MM12+MM13)교사프로필 이동 + 교사프로필 가져오기
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
	
	// (MM14)교사프로필 동록, 수정 이동
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
	
	// (MM15)교사프로필 등록,수정
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

		//log.info(tb.toString());
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
	
	// (MM16)교사프로필 삭제하기
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
	
	// (MM17+MM19)클래스관리이동 + 내 클래스 목록 가져오기
	public ModelAndView goMyclass_t(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<ClassBean> clsInfo = null;
		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
		// 세션에서 mbId,mbType을 가져옴
		String mbId = loginData.getMbId();
		int mbType = loginData.getMbType();
		// 강사회원(mbType==2)인지 확인하기
		if (mbType == 2) { // 회원타입이 강사라면
			// getMyClassList()에 mbId, mbType을 넘겨 클래스목록 가져오기 dao에 요청
			clsInfo = mDao.getMyClassList(mbId, mbType);
		}
		// 가져온 정보를 mav에 넣기
		mav.addObject("clsInfo", clsInfo);
		mav.addObject("navtext", "클래스 관리> 마이 클래스");
		// myclass_t.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("member/myclass_t"); // .jsp
		return mav;
	}

	// (MM18+MM19)마이클래스이동 + 내 클래스 목록 가져오기
	public ModelAndView goMyclass_s(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<ClassBean> clsInfo = null;
		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
		// 세션에서 mbId,mbType을 가져옴
		String mbId = loginData.getMbId();
		int mbType = loginData.getMbType();
		// 학생회원(mbType==1)인지 확인하기
		if (mbType == 1) { // 회원타입이 학생이라면
			// getMyClassList()에 mbId, mbType을 넘겨 클래스목록 가져오기 dao에 요청
			clsInfo = mDao.getMyClassList(mbId, mbType);
		}
		// 가져온 정보를 mav에 넣기
		mav.addObject("clsInfo", clsInfo);
		mav.addObject("navtext", "마이 클래스");
		// myclass_s.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("member/myclass_s"); // .jsp
		return mav;
	}

}
