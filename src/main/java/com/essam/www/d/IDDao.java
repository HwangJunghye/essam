package com.essam.www.d;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.MemberBean;
import com.essam.www.bean.ReplyBean;

public interface IDDao {

	MemberBean getMemberInfo(String mbId);

	boolean memberUpdate(MemberBean mb);

	void putInterCate(@Param("cateno") int cateno, @Param("tablenm") String tablenm, @Param("mbid") String mbid);

	void deleteInterCate(@Param("mbId") String mbId, @Param ("tablenm")String tablenm);

	int[] getinterCate(@Param("mbId") String mbId, @Param("tablenm")String tablenm);

	List<ReplyBean> getReplyList(String clsBrdNo);

	void addReply(ReplyBean rb);

	ReplyBean getReply(String clsBrdRepNo);

	void updateReply(ReplyBean rb);

	void deleteReply(@Param("clsBrdRepNo") String clsBrdRepNo, @Param("mbId") String mbId);

	



}

	
