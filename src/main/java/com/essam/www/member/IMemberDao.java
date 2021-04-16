package com.essam.www.member;

import com.essam.www.bean.MemberBean;

public interface IMemberDao {
	// member-mapper 사용
	boolean memberJoin(MemberBean mb);
	
}
