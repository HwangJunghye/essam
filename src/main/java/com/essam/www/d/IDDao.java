package com.essam.www.d;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.MemberBean;

public interface IDDao {

	MemberBean getMemberInfo(String mbId);

	boolean memberUpdate(MemberBean mb);

	void putInterCate(@Param("cateno") int cateno, @Param("tablenm") String tablenm, @Param("mbid") String mbid);

	void deleteInterCate(@Param("mbId") String mbId, @Param ("tablenm")String tablenm);

}
