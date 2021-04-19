package com.essam.www.e;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.essam.www.bean.ClassBean;

@Service
public class EMM {
	@Autowired
	private IEDao eDao;

	// 클래스 검색 결과 가져오기
	public Map<String, Object> getSearchList(Integer pageNo, Integer cate1No, Integer cate2No, String keyword) {
		// null값 변경
		if (cate1No == null) {
			cate1No = 0;
		}
		if (cate2No == null) {
			cate2No = 0;
		}
		if (keyword == null) {
			keyword = "";
		}
		List<ClassBean> cList = eDao.getSearchList(pageNo, cate1No, cate2No, keyword);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageNo", pageNo);
		result.put("pageSize", (cList == null ? 0 : cList.size()));
		result.put("cList", cList);
		// return ResponseEntity.ok(cList);
		return result;
	}

}
