package com.essam.www.e;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EController {
	@Autowired
	private EMM em;

	/**
	 * 검색 페이지 이동<br>
	 * "/search?cate1No=카테고리1&cate2No=카테고리2"<br>
	 * "/search?keyword=키워드"
	 */
	@RequestMapping(value = "/search")
	public ModelAndView goSearch(Integer cate1No, Integer cate2No, String keyword) {
		return em.goSearch(cate1No, cate2No, keyword); // .jsp
	}

	/**
	 * 검색결과 가져오기(ajax) "/getsearchlist" + param{pageNo,cate1No,cate2No,keyword}
	 */
	@RequestMapping(value = "/getsearchlist")
	@ResponseBody
	public Map<String, Object> getSearchList(Integer pageNo, Integer cate1No, Integer cate2No, String keyword) {
		return em.getSearchList(pageNo, cate1No, cate2No, keyword);
	}
}
