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
	 */
	@RequestMapping(value = "/download")
	void download(String fileNo, HttpServletRequest request, HttpServletResponse response) {
		if (fileNo != null) { // 파일번호가 파라미터로 넘어왔을 경우
			fm.download(fileNo, request, response);
		}
	}

	/**
	 * 동영상 가져오기<br>
	 * "/getvideo?fileNo=파일일련번호"
	 */
	@RequestMapping(value = "/getvideo")
	void getVideo(String fileNo, HttpServletRequest request, HttpServletResponse response) {
		if (fileNo != null) { // 파일번호가 파라미터로 넘어왔을 경우
			fm.getVideo(fileNo, request, response);
		}
	}

	/**
	 * 이미지 가져오기<br>
	 * "/getimage?fileNo=파일일련번호"
	 */
	@RequestMapping(value = "/getimage")
	void getImage(String fileNo, HttpServletRequest request, HttpServletResponse response) {
		if (fileNo != null) {
			fm.getImage(fileNo, request, response);
		}
	}

	/**
	 * 섬네일 가져오기<br>
	 * "/getthumbnail?fileNo=파일번호&width=가로&height=세로
	 */
	@RequestMapping(value = "/getthumbnail")
	void getThumbnail(String fileNo, Integer width, Integer height, HttpServletRequest request,
			HttpServletResponse response) {
		if (fileNo != null) {
			if (width == null || height == null) { // 가로길이나 세로길이가 입력되지 않았다면
				// 그냥 이미지 출력
				fm.getImage(fileNo, request, response);
			} else { // 섬네일 출력
				fm.getThumbnail(fileNo, width, height, request, response);
			}
		}
	}
}
