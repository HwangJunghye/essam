package com.essam.www.etc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.AdminBean;
import com.essam.www.bean.MemberBean;

@Service
public class EtcMM {
	@Autowired
	private IEtcDao eDao;

	// (EM01)관리자 페이지 이동	
	public ModelAndView goAdmin(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// 세션에서 mbType을 가져옴 --> 관리자 회원(mbType==3)인지 확인하기
		MemberBean loginData = (MemberBean)session.getAttribute("loginData");
		int mbType=loginData.getMbType();
		if(mbType==3){ //관리자라면
			mav.addObject("navtext", "관리자 모드> 통계관리");
			mav.setViewName("etc/admin"); // .jsp
		}else { //관리자가 아니라면 index.jsp로
			mav.setViewName("redirect:/"); // .jsp
		}
		return mav;
	}
	
	// (EM02)통계 가져오기(ajax)
	public List<Map<String, Object>> getStatistic(AdminBean ab) {
		List<Map<String, Object>> statistic = null;
		if(ab.getSearchTarget().equals("class")) { //검색대상이 클래스인 경우	
			statistic = eDao.getClassStatistic(ab);	
		}else if(ab.getSearchTarget().equals("teacher")){ //검색대상이 강사인 경우	
			statistic = eDao.getTeacherStatistic(ab);	
		}else if(ab.getSearchTarget().equals("student")){ //검색대상이 학생인 경우	
			statistic = eDao.getStudentStatistic(ab);	
		}			
		return statistic;
	}
} 
