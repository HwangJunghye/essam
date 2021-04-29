package com.essam.www.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.essam.www.bean.MemberBean;

import lombok.extern.log4j.Log4j;

@Log4j
public class CommonInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String servletPath = request.getServletPath(); // 서블릿 주소
		String indexPath = request.getContextPath() + "/"; // 인덱스 주소
		int rdFlag = -1; // 인덱스로 이동해야 하는 지 여부

		// System.out.println("preHandler Call : " + request.getServletPath());
		log.info("preHandler Call : " + servletPath);
		// 로그인 검증이나 권한 검증시 여기에 코드 추가

		// 로그인 정보 가져오기
		MemberBean mb = (MemberBean) request.getSession().getAttribute("loginData");
		if (mb == null) {
			// 로그아웃 상태인 경우
			log.info("user status : logout");
			switch (servletPath) {
			// 회원타입 검증에 있는 URL도 추가할 것.
			// 로그인이 아니라면
			// 회원타입 검증이 필요한 모든 서비스도 사용 불가능하니까
			case "/classjoin":
			case "/mypage":
			case "/memberupdate":
			case "/teacher_profile":
			case "/teacher_profile/write":
			case "/teacher_profile/update":
			case "/teacher_profile/delete":
			case "/myclass_t":
			case "/myclass_s":
			case "/logout":
				// case "/class/classinfo" :
				// case "/class/goboardwrite" :
				// case "/class/boardwrite" :
				// 어떤 url 추가해야 할지 정리 필요.
				// 개발 단계이기 때문에 로그인이 반드시 필요한 항목 외에는 주석처리함
				// 이 위에 case문 추가
				rdFlag = 0; // 로그인이 필요합니다
				break;
			}
		} else {
			// 로그인 상태인 경우
			log.info("user status : login(" + mb.getMbId() + ")");

			// 회원타입에 따른 작업
			if (mb.getMbType() == 1) {
				// 학생회원인 경우
				switch (servletPath) {
				case "/class/classinfo/update":
					// 주소 추가 필요
					rdFlag = 1; // 권한이 존재하지 않습니다(학생회원)
					break;
				}
			} else if (mb.getMbType() == 2) {
				// 교사회원인 경우
				switch (servletPath) {
				case "/classjoin":
					// 주소 추가 필요
					rdFlag = 2; // 권한이 존재하지 않습니다(교사회원)
				}
			} else {
				// 관리자인 경우
			}
		}

		if (rdFlag != -1) { // 리다이렉트가 필요한 경우
			FlashMap fMap = new FlashMap();
			switch (rdFlag) {
			case 0:// 로그인이 필요한 경우
				fMap.put("fMsg", "로그인이 필요합니다");
				break;
			case 1:// 권한이 존재하지 않는 경우(rdFlag : 1(학생) or 2(강사) or 3(관리자)
			case 2:
			case 3:
				fMap.put("fMsg", "권한이 존재하지 않습니다");
				break;
			default:
				fMap.put("fMsg", "올바르지 않은 값");
				break;
			}
			FlashMapManager fMapManager = RequestContextUtils.getFlashMapManager(request);
			fMapManager.saveOutputFlashMap(fMap, request, response);
			response.sendRedirect(indexPath);
			return false;
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
