package com.essam.www.d;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.MemberBean;
import com.essam.www.bean.ReplyBean;

@Controller
public class DController {
	@Autowired
	private DMM dm;
	
	//계정관리 이동하기+회원정보 가져오기
	@RequestMapping(value = "/mypage")
	ModelAndView goMypage(HttpSession session) {
		ModelAndView mav = dm.goMypage(session);
		return mav;
	}	
	
//	회원정보 수정 실행
	
	@RequestMapping(value = "/memberupdate")
	ModelAndView memberUpdate(MemberBean mb, HttpSession session) {
		ModelAndView mav = dm.memberUpdate(mb, session);
		
		return mav;
	}
	//댓글 목록 가져오기(ajax)
	@RequestMapping(value="/class/getreplylist")
	@ResponseBody List<ReplyBean> getReplyList(String clsBrdNo){
		System.out.println("clsBrdNo =====> "+ clsBrdNo);
		List<ReplyBean> rList = dm.getReplyList(clsBrdNo);
		System.out.println("rList ====> "+ rList.size());
		return rList;
	}
	//댓글 등록(ajax)	
	@RequestMapping(value = "/class/addreply")
	@ResponseBody List<ReplyBean> addReply(ReplyBean rb, MultipartHttpServletRequest mReq){
		System.out.println("여기 오나 ==============>"+ rb.getClsBrdNo());
		List<ReplyBean> rList = dm.addReply(rb,mReq);
		return rList;
	}
	//댓글 수정(ajax)
	@RequestMapping(value = "/class/updatereply")
	@ResponseBody List<ReplyBean> updateReply(ReplyBean rb, MultipartHttpServletRequest mReq){
		List<ReplyBean> rList = dm.updateReply(rb, mReq);
		return rList;
	}
	
	//댓글 삭제(ajax)	
	@RequestMapping(value = "/deletereply")
	@ResponseBody List<ReplyBean> deleteReply(String clsBrdRepNo, HttpServletRequest req, String clsBrdNo){
		List<ReplyBean> rList = dm.deleteReply(clsBrdRepNo, req, clsBrdNo);
		
		return rList;
	}
}		








