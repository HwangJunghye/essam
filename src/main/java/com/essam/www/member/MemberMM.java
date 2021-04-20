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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.ClassBean;
import com.essam.www.bean.MemberBean;

@Service
public class MemberMM {
	@Autowired
	private IMemberDao mDao;
//	로그인	
//	회원가입	
//	이메일 중복체크(ajax)	
//	로그아웃	
//	로그인 실행	
//	수강신청 실행	
//	비밀번호 변경 실행(ajax)	
//	계정관리 이동하기	
//	회원정보 수정 실행	
//	회원정보 가져오기        	
//	교사프로필 이동	
//	교사프로필 가져오기	
//	교사프로필 동록, 수정 이동하기	
//	교사프로필 등록,수정	
//	교사프로필 삭제하기	

	
	// 회원가입 실행
	public ModelAndView memberJoin(MemberBean mb, RedirectAttributes rattr) {
		ModelAndView mav = new ModelAndView();
		// 비밀번호 암호화 라이브러리 불러오기
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// 비밀번호 암호화
		String encPwd = encoder.encode(mb.getMbPwd());
		mb.setMbPwd(encPwd);

		if (mDao.memberJoin(mb)) { // 회원등록 성공시
			// 관심카테고리1 저장
			if(mb.getCate1No().length >= 1) {
				for(int cate1 : mb.getCate1No()) {
					mDao.putInterCate(cate1, "INTER_CATE1", mb.getMbId());
				}
			}
			// 관심카테고리2 저장
			if(mb.getCate2No().length >= 1) {
				for(int cate2 : mb.getCate2No()) {
					mDao.putInterCate(cate2, "INTER_CATE2", mb.getMbId());
				}
			}
			rattr.addFlashAttribute("fMsg", "회원가입 성공");
			mav.setViewName("redirect:/");
		} else {
			rattr.addFlashAttribute("fMsg", "회원가입 실패");
			mav.setViewName("redirect:/join");
		}
		return mav;
	}

	// 로그인 실행
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
				rattr.addFlashAttribute("fMsg","로그인 성공");
			}else { // 비밀번호 불일치시
				mav.setViewName("redirect:/login"); // 로그인 페이지로
				rattr.addFlashAttribute("fMsg","로그인 실패");
			}
		}else { // 아이디가 없는 경우
			mav.setViewName("redirect:/login"); // 로그인 페이지로
			rattr.addFlashAttribute("fMsg","로그인 실패");
		}
		return mav;
	}

	// 이메일 중복체크
	public Map<String, String> checkEmail(String mbId) {
		Map<String,String> hMap = new HashMap<>();
		boolean isExist = mDao.checkEmail(mbId);
		if(isExist) {
			hMap.put("msg", "존재하는 이메일입니다.");
		}else {
			hMap.put("msg", "사용가능한 이메일입니다.");
		}
		return hMap;
	}
	
	/**
	 * 클래스관리 페이지로 이동<br>
	 * 클래스목록 가져오기 getMyClassList()
	 */
	public ModelAndView goMyclass_t(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<ClassBean> clsInfo = null;
		String mbId= session.getAttribute("mbId").toString();
		// 세션에서 mbId,mbType을 가져옴
		// 강사회원(mbType==2)인지 확인하기
		if(session.getAttribute("mbType").toString().equals("2")){ //회원타입이 강사라면
			//getMyClassList()에 mbId, mbType을 넘겨 클래스목록 가져오기 dao에 요청
			clsInfo = mDao.getMyClassList(mbId);
		}
		// 가져온 정보를 mav에 넣기
		mav.addObject("clsInfo",clsInfo);	
		// myclass_t.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("member/myclass_t"); // .jsp
		return mav;
	}

	/**
	 * 마이클래스 페이지로 이동<br>
	 * 클래스목록 가져오기 getMyClassList()
	 */
	public ModelAndView goMyclass_s(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<ClassBean> clsInfo = null;
		String mbId= session.getAttribute("mbId").toString();
		// 세션에서 mbId,mbType을 가져옴
		// 학생회원(mbType==1)인지 확인하기
		if(session.getAttribute("mbType").toString().equals("1")){ //회원타입이 학생이라면
			//getMyClassList()에 mbId, mbType을 넘겨 클래스목록 가져오기 dao에 요청
			clsInfo = mDao.getMyClassList(mbId);
		}
		// 가져온 정보를 mav에 넣기
		mav.addObject("clsInfo",clsInfo);
		// myclass_s.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("member/myclass_s"); // .jsp
		return mav;
	}

	
	
}
