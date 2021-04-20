package com.essam.www.etc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.MemberBean;

@Controller
public class EtcController {
	@Autowired
	private EtcMM em;
		// 관리자 페이지 이동
		@RequestMapping(value = "/admin")
		ModelAndView goAdmin(HttpSession session) {
			ModelAndView mav = em.goAdmin(session);
			return mav;
		}
				
		// 통계 가져오기(ajax)	
		//@RequestMapping(value = "/getstatistic")
		//String getStatistic() {
		// return null;	
		//}

}
