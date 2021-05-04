package com.essam.www.d;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.MemberBean;
import com.essam.www.bean.ReplyBean;
import com.essam.www.file.FileMM;
import com.essam.www.member.IMemberDao;

@Service
public class DMM {
	@Autowired
	private IDDao DDao;
	@Autowired
	private FileMM fm;
	@Autowired
	private IMemberDao mDao;
	
	StringBuffer sb = new StringBuffer("");

	// 계정관리 이동하기+회원정보 가져오기
	public ModelAndView goMypage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
		MemberBean mb = DDao.getMemberInfo(loginData.getMbId());
		mb.setMbBirth(mb.getMbBirth().substring(0,mb.getMbBirth().length()-9));
		boolean[] cate1Chk = { false, false, false, false, false, false, false };
		boolean[] cate2Chk = { false, false, false, false, false, false, false, false, false };
		int[] cate1 = DDao.getinterCate(loginData.getMbId(), "INTER_CATE1");
		int[] cate2 = DDao.getinterCate(loginData.getMbId(), "INTER_CATE2");
		for(int c1 : cate1) {
			cate1Chk[c1]=true;
		}
		for(int c2 : cate2) {
			cate2Chk[c2]=true;
		}
		mb.setCate1No(cate1);
		mb.setCate2No(cate2);
		mav.setViewName("member/mypage");
		mav.addObject("myInfo", mb);
		mav.addObject("cate1Chk", cate1Chk);
		mav.addObject("cate2Chk", cate2Chk);
		mav.addObject("navtext", "마이페이지");

		return mav;
	}
	//	회원정보 수정 실행
	public ModelAndView memberUpdate(MemberBean mb, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		MemberBean loginData = (MemberBean) session.getAttribute("loginData");
		mb.setMbId(loginData.getMbId());
		DDao.memberUpdate(mb);

		// 관심카테고리1 저장
		if (mb.getCate1No() != null) {
			// 관심카테고리1 삭제
			DDao.deleteInterCate(mb.getMbId(), "INTER_CATE1");
			for (int cate1 : mb.getCate1No()) {
				mDao.putInterCate(cate1, "INTER_CATE1", mb.getMbId());
			}
		}
		// 관심카테고리2 저장
		if (mb.getCate2No() != null) {
			// 관심카테고리2 삭제
			DDao.deleteInterCate(mb.getMbId(), "INTER_CATE2");
			for (int cate2 : mb.getCate2No()) {
				mDao.putInterCate(cate2, "INTER_CATE2", mb.getMbId());
			}
		}
		mav.setViewName("redirect:/mypage");
		return mav;
	}
}
