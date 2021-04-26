package com.essam.www.e;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.ClassBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.constant.Constant;
import com.essam.www.member.IMemberDao;

@Service
public class EMM {
	@Autowired
	private IEDao eDao;
	@Autowired
	private IMemberDao mDao;

	/**
	 * 비밀번호 변경 실행(ajax)
	 */
	public Map<String, String> changePassword(HttpSession session, String mbPwd, String newMbPwd) {
		Map<String, String> map = new HashMap<>();
		boolean flag = false; // 비밀번호 변경 성공여부 저장
		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
		if(loginData==null) {
			map.put("msg", "로그인 상태가 아닙니다");
			return map;
		}
		
		MemberBean mb = mDao.getMemberInfo(loginData.getMbId());
		if(mb==null) {
			map.put("msg", "회원정보가 존재하지 않습니다.");
			return map;
		}

		// 비밀번호 인코더 불러오기
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if (encoder.matches(mbPwd, mb.getMbPwd())) { // 비밀번호가 일치하는 경우
			// 비밀번호 변경하기
			String encPw = encoder.encode(newMbPwd); // 새로운 비밀번호 암호화
			if (eDao.changePassword(loginData.getMbId(), encPw)) { // 변경 실행
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

}
