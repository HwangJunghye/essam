package com.essam.www.etc;
 
import java.util.List;
import java.util.Map;

import com.essam.www.bean.AdminBean;

public interface IEtcDao {
	// etc-mapper.xml 사용
	List<Map<String, Object>> getTotalStatistic(AdminBean ab);
	
	List<Map<String, Object>> getNewStatistic(AdminBean ab);
}
