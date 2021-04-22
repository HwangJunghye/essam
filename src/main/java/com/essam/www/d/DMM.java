package com.essam.www.d;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.MemberBean;

@Service
public class DMM {
	@Autowired
	private IDDao DDao;
	
	//계정관리 이동하기+회원정보 가져오기
	public ModelAndView goMypage(String mbId) {
		ModelAndView mav = new ModelAndView();
		MemberBean mb = DDao.getMemberInfo(mbId);
		
		return mav;
	}	
//	회원정보 수정 실행

	public ModelAndView memberUpdate() {
		MemberBean mb = new MemberBean();
		ModelAndView mav = new ModelAndView();
		mav.addObject(mav);
		mav  = DDao.memberUpdate(mb);
		// 관심카테고리1 저장
		if(mb.getCate1No() != null) {
			for(int cate1 : mb.getCate1No()) {
				DDao.putInterCate(cate1, "INTER_CATE1", mb.getMbId());
			}
		}
		// 관심카테고리2 저장
		if(mb.getCate2No() != null) {
			for(int cate2 : mb.getCate2No()) {
				DDao.putInterCate(cate2, "INTER_CATE2", mb.getMbId());
			}
		}
		return mav;
	}

}
