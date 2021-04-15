package com.essam.www.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essam.www.bean.FileBean;

@Service
public class FileMM {
	@Autowired
	private IFileDao fDao;


	/**
	 * 첨부파일 다운로드<br>
	 * fileNo : 파일일련번호
	 * */
	public void download(String fileNo, HttpServletRequest request, HttpServletResponse response) {
		// 파일정보 가져오기
		FileBean fData = fDao.getFileData(fileNo);
		String upPath = request.getSession().getServletContext().getRealPath("/") + "upload/";

		File file = new File(upPath + fData.getSysFileName());
		if (file.exists()) {
			try {
				InputStream is = new FileInputStream(file); // 파일 읽을 준비

				// 파일 다운 설정
				response.setContentType("application/octet-stream");
				response.setHeader("content-Disposition", "attachment; filename=\"" + fData.getOrigFileName() + "\"");

				OutputStream os = response.getOutputStream(); // 파일 출력 준비

				// 파일쓰기
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) != -1) {
					os.write(buffer, 0, length);
				}
				// 스트림 닫기
				os.flush();
				os.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//	동영상 가져오기	
//	파일 저장하기	
//	이미지 가져오기	
//	섬네일 가져오기	

}
