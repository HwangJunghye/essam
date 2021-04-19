package com.essam.www.d;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.MemberBean;
import com.essam.www.member.IMemberDao;

@Service
public class DMM {
	@Autowired
	private IMemberDao mDao;
	
	// 회원가입 실행
		public ModelAndView memberJoin(MemberBean mb, RedirectAttributes rattr) {
			ModelAndView mav = new ModelAndView();
			// 비밀번호 암호화 라이브러리 불러오기
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			// 비밀번호 암호화
			String encPwd = encoder.encode(mb.getMbPwd());
			mb.setMbPwd(encPwd);

			if (mDao.memberJoin(mb)) { // 회원등록 성공시
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
	
	
	
	
}
