package com.essam.www.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.essam.www.bean.MemberBean;

import lombok.extern.log4j.Log4j;

@Log4j
public class CommonInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private IInterceptorDao iDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String servletPath = request.getServletPath(); // 서블릿 주소
		log.info("preHandler Call : " + servletPath); // 현재 서블릿 주소 출력

		//int authFlag = this.getAuthFlag(servletPath, request);

		//if (authFlag != 1) { // 서비스 권한이 존재하지 않는 경우
			// 다른 페이지로 리다이렉트 및 메세지 설정
			//return this.sendAuthMessage(authFlag, request, response);
		//}
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// log.info("postHandle Call : " + request.getServletPath());
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * 권한에 대해서 작업할 내용을 판정
	 */
	private int getAuthFlag(String servletPath, HttpServletRequest request) {
		// 로그인 정보 가져오기
		MemberBean mb = (MemberBean) request.getSession().getAttribute("loginData");
		if (mb == null) { // 로그아웃 상태인 경우
			log.info("user status : logout");
			switch (servletPath) {
			// 회원타입 검증에 있는 URL도 추가할 것.
			// 로그인이 아니라면
			// 회원타입 검증이 필요한 모든 서비스도 사용 불가능하니까
			case "/mypage":
			case "/classjoin":
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
				return 0; // 로그인이 필요합니다
			}
		} else { // 로그인 상태인 경우
			log.info("user status : login(" + mb.getMbId() + ")");
			int mbType = mb.getMbType();
			// 회원타입에 따른 작업
			if (mbType == 1) {
				// 학생회원인 경우
				switch (servletPath) {
				case "/class/classinfo/update":
					// 주소 추가 필요
					return -1; // 권한이 존재하지 않습니다(학생회원)
				}
			} else if (mbType == 2) {
				// 교사회원인 경우
				switch (servletPath) {
				case "/classjoin":
					// 주소 추가 필요
					return -2; // 권한이 존재하지 않습니다(교사회원)
				}
			} else if (mbType == 3) {
				// 관리자인 경우
				// 권한이 없는 경우 return -3
			} else {
				// 존재할 수 없는 권한
				return -999;
			}

			// class 권한 확인
			if (servletPath.matches("/class/.*")) {
				String clsNo = request.getParameter("clsNo");
				// 클래스 등록 페이지가 아니면서 clsNo가 null이나 공백인 경우
				if (!servletPath.equals("/class/classinfo/write") && clsNo == null || clsNo.equals("")) { // clsNo가 없는 경우(잘못된 파라미터)
					return -4;
				}
				if (!iDao.hasClassAuth(mb.getMbId(), clsNo, mbType)) { // 클래스의 권한이 없는 경우
					return -5;
				}
			}
		}
		return 1; // 권한 사용가능
	}

	/**
	 * 권한에 따라서 메세지 출력 및 리다이렉트
	 */
	private boolean sendAuthMessage(int authFlag, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		FlashMap fMap = new FlashMap();
		switch (authFlag) {
		case 0:// 로그인이 필요한 경우
			fMap.put("modal", "modal open");
			fMap.put("fMsg", "로그인이 필요합니다");
			break;
		case -1:// 권한이 존재하지 않는 경우(rdFlag : 1(학생) or 2(강사) or 3(관리자)
			fMap.put("fMsg", "권한이 존재하지 않습니다(현재 권한 : 학생회원)");
			break;
		case -2:
			fMap.put("fMsg", "권한이 존재하지 않습니다(현재 권한 : 강사회원)");
			break;
		case -3:
			fMap.put("fMsg", "권한이 존재하지 않습니다(현재 권한 : 관리자)");
			break;
		case -4:
			fMap.put("fMsg", "클래스 번호가 입력되지 않았습니다");
			break;
		case -5:
			fMap.put("fMsg", "해당 클래스의 권한이 존재하지 않습니다");
			break;
		default:
			fMap.put("fMsg", "올바르지 않은 권한입니다");
			break;
		}
		// fMsg 넣기
		FlashMapManager fMapManager = RequestContextUtils.getFlashMapManager(request);
		fMapManager.saveOutputFlashMap(fMap, request, response);

		// 이전 페이지로 리다이렉트
		String referer = request.getHeader("Referer");
		response.sendRedirect(referer == null ? request.getContextPath() + "/" : referer);
		return false;
	}
}
