package com.essam.www.member;

import java.util.List;

import com.essam.www.bean.ClassBean;
import com.essam.www.bean.MemberBean;

public interface IMemberDao {
	// member-mapper 사용
	boolean memberJoin(MemberBean mb);

	MemberBean getMemberInfo(String mbId);

	boolean checkEmail(String mbId);

	List<ClassBean> getMyClassList(String mbId);
	
}
