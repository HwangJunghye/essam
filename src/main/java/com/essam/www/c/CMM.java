package com.essam.www.c;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.MemberBean;
import com.essam.www.bean.TeacherBean;

@Service
public class CMM {
	@Autowired
	private ICDao mDao;

	/**
	 * 교사프로필 가져오기 getTeacherProfile()
	 * @param session
	 * @return ModelAndView
	 */
	public ModelAndView getTeacherProfile(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<TeacherBean> teacherInfo = null;
		MemberBean loginData = (MemberBean)session.getAttribute("loginData");
		//세션에서 mbId, mbType을 가져옴
		String mbId = loginData.getMbId();
		System.out.println("mbId");
		String mbType = loginData.getMbType()+"";
		//강사회원(mbType==2)인지 확인하기
		if(mbType.equals("2")) { //회원타입이 강사라면
			//getTeacherProfile()에 mbId 넘겨 강사프로필데이터 가져오기 dao에 요청
			teacherInfo = mDao.getTeacherProfile(mbId);
		}
		//가져온 정보를 mav에 넣기
		mav.addObject("teacherInfo", teacherInfo);
		//teacher_profile.jsp로 이동하기 위해 viewname 지정
		mav.setViewName("member/teacher_profile"); // .jsp
		return mav;
	}
	
	
	//교사프로필 등록, 수정 이동하기
	//교사프로필 등록, 수정
	//교사프로필 삭제하기
	
}
