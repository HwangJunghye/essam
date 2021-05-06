package com.essam.www.etc;

import java.util.Map;

import com.essam.www.bean.AdminBean;

public interface IEtcDao {
	// etc-mapper.xml 사용
	Map<String, Object> getTotalStatistic(AdminBean ab);
	
	Map<String, Object> getNewStatistic(AdminBean ab);
}
