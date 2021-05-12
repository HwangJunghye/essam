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

		// 권한 검증
		int authFlag = this.getAuthFlag(servletPath, request);

		if (authFlag != 1) { // 서비스 권한이 존재하지 않는 경우
			// 다른 페이지로 리다이렉트 및 메세지 설정
			return this.sendAuthMessage(authFlag, request, response);
		}
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
			case "/memberupdate":
			case "/teacher_profile": // 교사 프로필 관련
			case "/teacher_profile/write":
			case "/teacher_profile/update":
			case "/teacher_profile/delete":
			case "/myclass_t": // 교사용 마이클래스
			case "/class/classinfo/write": // 클래스 정보 작성 이동(교사용)
			case "/class/classinfo/update": // 클래스 정보 작성(교사용)
			case "/class/deleteclass": // 클래스 삭제(교사용)
			case "/class/goboardwrite": // 게시글 쓰기 관련(교사용)
			case "/class/boardwrite":
			case "/class/studentlist": // 학생 목록보기(교사용)
			case "/class/studentinfo": // 학생 정보보기(교사용)
			case "/class/curriculum/write": // 커리큘럼 작성 관련
			case "/class/curriculum/add":
			case "/class/curriculum/update":
			case "/class/curriculum/update_server":
			case "/class/curriculum/delete":
			case "/classjoin": // 수강신청
			case "/myclass_s": // 마이클래스(학생)
			case "/class/attend": // 출석 보기(학생)
			case "/admin":
				return 0; // 로그인이 필요합니다
			}
		} else { // 로그인 상태인 경우
			log.info("user status : login(" + mb.getMbId() + ")");
			int mbType = mb.getMbType();
			// 회원타입에 따른 작업
			if (mbType == 1) {
				// 학생회원인 경우
				switch (servletPath) {
				case "/teacher_profile": // 교사 프로필 관련
				case "/teacher_profile/write":
				case "/teacher_profile/update":
				case "/teacher_profile/delete":
				case "/myclass_t": // 교사용 마이클래스
				case "/class/classinfo/write": // 클래스 정보 작성 이동(교사용)
				case "/class/classinfo/update": // 클래스 정보 작성(교사용)
				case "/class/deleteclass": // 클래스 삭제(교사용)
				case "/class/goboardwrite": // 게시글 쓰기 관련(교사용)
				case "/class/boardwrite":
				case "/class/studentlist": // 학생 목록보기(교사용)
				case "/class/studentinfo": // 학생 정보보기(교사용)
				case "/class/curriculum/write": // 커리큘럼 작성 관련
				case "/class/curriculum/add":
				case "/class/curriculum/update":
				case "/class/curriculum/update_server":
				case "/class/curriculum/delete":
				case "/admin": // 관리자용 페이지
					// 주소 추가 필요
					return -1; // 권한이 존재하지 않습니다(학생회원)
				}
			} else if (mbType == 2) {
				// 교사회원인 경우
				switch (servletPath) {
				case "/classjoin": // 수강신청
				case "/myclass_s": // 마이클래스(학생)
				case "/class/attend": // 출석 보기(학생)
				case "/admin": // 관리자용 페이지
					// 주소 추가 필요
					return -2; // 권한이 존재하지 않습니다(교사회원)
				}
			} else if (mbType == 3) {
				switch (servletPath) {
				case "/teacher_profile": // 교사 프로필 관련
				case "/teacher_profile/write":
				case "/teacher_profile/update":
				case "/teacher_profile/delete":
				case "/myclass_t": // 교사용 마이클래스
				case "/class/classinfo/write": // 클래스 정보 작성 이동(교사용)
				case "/class/classinfo/update": // 클래스 정보 작성(교사용)
				case "/class/deleteclass": // 클래스 삭제(교사용)
				case "/class/goboardwrite": // 게시글 쓰기 관련(교사용)
				case "/class/boardwrite":
				case "/class/studentlist": // 학생 목록보기(교사용)
				case "/class/studentinfo": // 학생 정보보기(교사용)
				case "/class/curriculum/write": // 커리큘럼 작성 관련
				case "/class/curriculum/add":
				case "/class/curriculum/update":
				case "/class/curriculum/update_server":
				case "/class/curriculum/delete":
				case "/classjoin": // 수강신청
				case "/myclass_s": // 마이클래스(학생)
				case "/class/attend": // 출석 보기(학생)
					return -3;
				}
				// 관리자인 경우
				// 권한이 없는 경우 return -3
			} else {
				// 존재할 수 없는 권한
				log.error("mbType is " + mbType + ". Impossible type");
				return -999;
			}

			// class 권한 확인(주소의 /class가 들어가는 경우)
			if (servletPath.matches("/class/.*")) {
				String clsNo = request.getParameter("clsNo");
				// 클래스 등록 페이지가 아니면서 clsNo가 null이나 공백인 경우
				if (clsNo == null || clsNo.equals("")) { // clsNo가 없는 경우
					if (!servletPath.equals("/class/classinfo/write") || !servletPath.equals("/class/classinfo/update")) { // 클래스 등록이 아닌 경우
						return -4;
					}
				} else { // 클래스 번호가 있는 경우
					if (!iDao.hasClassAuth(mb.getMbId(), clsNo, mbType)) { // 클래스의 권한이 없는 경우
						return -5;
					}
				}
			} // "/class" 권한 확인 End
		}
		return 1; // 권한 사용가능
	}

	/**
	 * 권한에 따라서 메세지 출력 및 리다이렉트
	 */
	private boolean sendAuthMessage(int authFlag, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		FlashMap fMap = new FlashMap(); // flashMap 생성(flashAttribute 사용을 위해)
		switch (authFlag) {
		case 0:// 로그인이 필요한 경우
			fMap.put("modal", "modal open"); // 로그인 모달창 열기
			fMap.put("fMsg", "로그인이 필요합니다");
			break;
		case -1:// 권한이 존재하지 않는 경우(authFlag : -1(학생) or -2(강사) or -3(관리자)
			fMap.put("fMsg", "권한이 존재하지 않습니다(현재 권한 : 학생회원)");
			break;
		case -2:
			fMap.put("fMsg", "권한이 존재하지 않습니다(현재 권한 : 강사회원)");
			break;
		case -3:
			fMap.put("fMsg", "권한이 존재하지 않습니다(현재 권한 : 관리자)");
			break;
		case -4:
			fMap.put("fMsg", "클래스 번호가 잘못되었습니다"); // clsNo 파라미터가 없는 경우
			break;
		case -5:
			fMap.put("fMsg", "해당 클래스의 권한이 존재하지 않습니다"); // 클래스의 멤버가 아닌경우
			break;
		default:
			fMap.put("fMsg", "올바르지 않은 권한입니다"); // 예상치 못한 에러
			break;
		}
		// FlashAttribute에 fMap 넣기
		FlashMapManager fMapManager = RequestContextUtils.getFlashMapManager(request);
		fMapManager.saveOutputFlashMap(fMap, request, response);

		// 이전 페이지로 리다이렉트
		String referer = request.getHeader("referer");
		response.sendRedirect(referer == null ? request.getContextPath() + "/" : referer);
		return false;
	}
}
