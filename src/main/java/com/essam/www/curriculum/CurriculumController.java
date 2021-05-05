package com.essam.www.curriculum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CurriculumController {
	@Autowired
	private CurriculumMM crmm;

	// (CR01+CR02)클래스 커리큘럼 이동 + 커리큘럼 목록 가져오기
	// (CR03+CR04)커리큘럼 상세보기 이동 + 커리큘럼 상세가져오기
	// (CR05+CR06)동영상 페이지 이동 + 동영상 제목, 시작일, 종료일 가져오기	
	// (CR07)커리큘럼 등록 이동	
	// (CR08)커리큘럼 등록하기
	// (CR09)커리큘럼 수정 이동
	// (CR10)커리큘럼 수정하기
	// (CR11)커리큘럼 삭제하기
}
