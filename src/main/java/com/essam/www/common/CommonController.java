package com.essam.www.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController {
	@Autowired
	private CommonMM comm;

	/**
	 * 메인 페이지 이동<br>
	 * "/"
	 * */
	@RequestMapping(value = "/")
	ModelAndView goIndex(HttpServletRequest request) {
		return comm.goIndex(request); // .jsp
	}

	/**
	 * 검색 페이지 이동<br>
	 * "/search?cate1No=카테고리1&cate2No=카테고리2"<br>
	 * "/search?keyword=키워드"
	 */
	@RequestMapping(value = "/search")
	public ModelAndView goSearch(Integer cate1No, Integer cate2No, String keyword) {
		return comm.goSearch(cate1No, cate2No, keyword); // .jsp
	}

	/**
	 * 검색결과 가져오기(ajax)<br> 
	 * "/getsearchlist" + param{pageNo,cate1No,cate2No,keyword}
	 */
	@RequestMapping(value = "/getsearchlist")
	@ResponseBody
	public Map<String, Object> getSearchList(Integer pageNo, Integer cate1No, Integer cate2No, String keyword) {
		return comm.getSearchList(pageNo, cate1No, cate2No, keyword);
	}
	
	
//	new/hot 클래스목록 가져오기	
//	클래스 소개 이동	
//	클래스정보가져오기	
	
	
	

}
