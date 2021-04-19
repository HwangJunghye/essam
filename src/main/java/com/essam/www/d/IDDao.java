package com.essam.www.d;

import com.essam.www.bean.MemberBean;

public interface IDDao {
	public interface IMemberDao {
		// member-mapper 사용
		boolean memberJoin(MemberBean mb);

		MemberBean getMemberInfo(String mbId);

		boolean checkEmail(String mbId);
	}
}
