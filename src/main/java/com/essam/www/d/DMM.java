package com.essam.www.d;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.MemberBean;



@Service
public class DMM {
	@Autowired
	private IDDao DDao;
	
	//계정관리 이동하기+회원정보 가져오기
	public ModelAndView goMypage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		MemberBean loginData = (MemberBean)session.getAttribute("loginData");
		MemberBean mb = DDao.getMemberInfo(loginData.getMbId());
		
		int[] cate1 = DDao.getinterCate(loginData.getMbId(), "INTER_CATE1");
		int[] cate2 = DDao.getinterCate(loginData.getMbId(), "INTER_CATE2");
		mb.setCate1No(cate1);
		mb.setCate2No(cate2);
		mav.setViewName("member/mypage");
		mav.addObject("myInfo",mb);
		
		return mav;
	}	
//	회원정보 수정 실행

	public ModelAndView memberUpdate(MemberBean mb,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		MemberBean loginData = (MemberBean)session.getAttribute("loginData");
		mb.setMbId(loginData.getMbId());
		DDao.memberUpdate(mb);
		
		// 관심카테고리1 저장
		if(mb.getCate1No() != null) { 
			//관심카테고리1 삭제
			DDao.deleteInterCate(mb.getMbId(), "INTER_CATE1");
			for(int cate1 : mb.getCate1No()) {
				DDao.putInterCate(cate1, "INTER_CATE1", mb.getMbId());
			}
		}
		// 관심카테고리2 저장
		if(mb.getCate2No() != null) {
			//관심카테고리2 삭제
			DDao.deleteInterCate(mb.getMbId(), "INTER_CATE2");
			for(int cate2 : mb.getCate2No()) {
				DDao.putInterCate(cate2, "INTER_CATE2", mb.getMbId());
			}
		}
		mav.setViewName("redirect:/mypage");
		return mav;
	}

}
