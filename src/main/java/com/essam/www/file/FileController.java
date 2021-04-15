package com.essam.www.file;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FileController {
	@Autowired
	private FileMM fm;
	
	/**
	 * 첨부파일 다운로드<br>
	 * "/download?fileNo=파일일련번호" 같은 방식으로 요청
	 * */
	@RequestMapping(value = "/download")
	void download(String fileNo, HttpServletRequest request, HttpServletResponse response) {
		fm.download(fileNo,request,response);
	}
	
//	동영상 가져오기	
//	파일 저장하기	
//	이미지 가져오기	
//	섬네일 가져오기	
}
