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
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.essam.www.bean.AdminBean;
import com.essam.www.bean.MemberBean;

@Controller
public class EtcController {
	@Autowired
	private EtcMM em;
	// (EM01)관리자 페이지 이동
	@RequestMapping(value = "/admin")
	ModelAndView goAdmin(HttpSession session) {
		ModelAndView mav = em.goAdmin(session);
		return mav;
	}
				
	// (EM02)통계 가져오기(ajax)	
	@RequestMapping(value = "/getstatistic")
	@ResponseBody Map<String,Object> getStatistic(AdminBean ab) {
		return em.getStatistic(ab);	
	}
}
