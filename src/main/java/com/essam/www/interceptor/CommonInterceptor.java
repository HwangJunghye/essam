package com.essam.www.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.essam.www.bean.MemberBean;

import lombok.extern.log4j.Log4j;

@Log4j
public class CommonInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// System.out.println("preHandler Call : " + request.getServletPath());
		log.info("preHandler Call : " + request.getServletPath());
		// 로그인 검증이나 권한 검증시 여기에 코드 추가

		// 로그인 정보 가져오기
		MemberBean mb = (MemberBean) request.getSession().getAttribute("loginData");
		if (mb == null) {
			// 로그아웃 상태인 경우
			log.info("user status : logout");
		} else {
			// 로그인 상태인 경우
			log.info("user status : login(" + mb.getMbId() + ")");
		}

		return true; // 다른 페이지로 이동할 경우 false 리턴
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("postHandle Call : " + request.getServletPath());
		super.postHandle(request, response, handler, modelAndView);
	}
}
