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
	 * [CO01] 메인페이지 이동
	 * @Author 고연미 on 28/04/2021
	 */
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
	public Map<String, Object> getSearchList(Integer pageSize, Integer pageNo, Integer cate1No, Integer cate2No,
			String keyword) {
		return comm.getSearchList(pageSize, pageNo, cate1No, cate2No, keyword);
	}
	/**
	 * [CO05] 클래스소개 이동
	 * @Author 고연미 on 28/04/2021
	 */
	@RequestMapping(value = "/classinfo")
	ModelAndView goClassInfo(String clsNo, HttpServletRequest request) {
		return comm.goClassInfo(clsNo, request);
	}

}
