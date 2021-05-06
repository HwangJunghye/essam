package com.essam.www.etc;

import com.essam.www.bean.AdminBean;

public interface IEtcDao {
	// etc-mapper.xml 사용
	String getStatistic(AdminBean ab);
}
