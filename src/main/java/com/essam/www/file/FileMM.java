package com.essam.www.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.essam.www.bean.FileBean;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Log4j
@Service
public class FileMM {
	@Autowired
	private IFileDao fDao;

	/**
	 * 첨부파일 다운로드<br>
	 * fileNo : 파일일련번호
	 */
	public void download(String fileNo, HttpServletRequest request, HttpServletResponse response) {
		// 파일정보 가져오기
		FileBean fData = fDao.getFileData(fileNo);
		if (fData == null) { // 파일정보가 존재하지 않는 경우
			return; // 종료
		}
		// 저장경로 가져오기
		String upPath = request.getSession().getServletContext().getRealPath("/") + "upload/";

		File file = new File(upPath + fData.getSysFileName());
		if (file.exists()) { // 파일이 존재할 경우
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

	/**
	 * 파일 저장하기 fileTypeNo :<br>
	 * 1.image<br>
	 * 2.video<br>
	 * 3.other<br>
	 * null:저장실패 fileNo:저장된 파일 번호
	 */
	public String saveFile(MultipartHttpServletRequest mReq, MultipartFile mFile, int fileTypeNo) {

		String upPath = mReq.getSession().getServletContext().getRealPath("/") + "upload/";
		String origFileName = mFile.getOriginalFilename();
		String sysFileName = System.currentTimeMillis() + origFileName;
		String contentType = mFile.getContentType();
		log.info("upPath : " + upPath);
		// 디렉토리가 없다면 생성
		File directory = new File(upPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		File file = new File(upPath + sysFileName);

		FileBean fileBean = new FileBean();
		fileBean.setOrigFileName(origFileName);
		fileBean.setSysFileName(sysFileName);
		fileBean.setContentType(contentType);
		fileBean.setFileTypeNo(fileTypeNo);

		try {
			if (fDao.saveFile(fileBean)) { // 파일을 DB에 저장했다면
				mFile.transferTo(file); // 파일을 저장
				return fileBean.getFileNo(); // 파일번호 리턴
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // 실패시 null
	}

	/**
	 * 동영상 가져오기<br>
	 * fileNo : 파일일련번호
	 */
	public void getVideo(String fileNo, HttpServletRequest request, HttpServletResponse response) {
		// 파일정보 가져오기
		FileBean fData = fDao.getFileData(fileNo);
		if (fData == null) { // 파일정보가 존재하지 않는 경우
			return; // 종료
		}

		// 비디오 파일인지 검증
		if (fData.getContentType().matches("video/*")) {
			return;
		}

		// 저장경로 가져오기
		String upPath = request.getSession().getServletContext().getRealPath("/") + "upload/";

		File file = new File(upPath + fData.getSysFileName());
		if (file.exists()) { // 파일이 존재할 경우
			try {
				InputStream is = new FileInputStream(file); // 파일 읽을 준비

				// 동영상 출력 설정
				response.setContentType(fData.getContentType());

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

	/**
	 * 이미지 가져오기<br>
	 * fileNo : 파일일련번호
	 */
	public void getImage(String fileNo, HttpServletRequest request, HttpServletResponse response) {
		// 파일정보 가져오기
		FileBean fData = fDao.getFileData(fileNo);
		if (fData == null) { // 파일정보가 존재하지 않는 경우
			return; // 종료
		}

		// 이미지 파일인지 검증
		if (fData.getContentType().matches("image/*")) {
			return; // 종료
		}

		// 저장경로 가져오기
		String upPath = request.getSession().getServletContext().getRealPath("/") + "upload/";

		File file = new File(upPath + fData.getSysFileName());
		if (file.exists()) { // 파일이 존재할 경우
			try {
				InputStream is = new FileInputStream(file); // 파일 읽을 준비

				// 이미지 출력 설정
				response.setContentType(fData.getContentType());

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

	/**
	 * 섬네일 가져오기<br>
	 * fileNo : 파일번호<br>
	 * width : 가로<br>
	 * height:세로
	 */
	void getThumbnail(String fileNo, Integer width, Integer height, HttpServletRequest request,
			HttpServletResponse response) {
		// 파일정보 가져오기
		FileBean fData = fDao.getFileData(fileNo);
		if (fData == null) { // 파일정보가 존재하지 않는 경우
			return; // 종료
		}

		// 이미지 파일인지 검증
		if (fData.getContentType().matches("image/*")) {
			return; // 종료
		}

		// 저장경로 가져오기
		String upPath = request.getSession().getServletContext().getRealPath("/") + "upload/";
		File file = new File(upPath + fData.getSysFileName());
		if (file.exists()) { // 파일이 존재할 경우
			try {
				InputStream is = new FileInputStream(file); // 파일 읽을 준비

				// 섬네일 출력 설정
				response.setContentType(fData.getContentType());

				OutputStream os = response.getOutputStream(); // 파일 출력 준비

				// 섬네일 변환 및 출력
				Thumbnailator.createThumbnail(is, os, width, height);

				// 스트림 닫기
				os.flush();
				os.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 파일 삭제하기<br>
	 * fileNo : 파일번호<br>
	 * return true - 삭제 성공<br>
	 * return false - 삭제 실패
	 */
	boolean deleteFile(String fileNo, HttpServletRequest request) {
		if (fileNo == null) { // 파일번호가 없다면 false
			return false;
		}

		FileBean fData = fDao.getFileData(fileNo);
		if (fData == null) { // 파일정보가 존재하지 않는 경우
			return false; // 종료
		}

		if (!fDao.deleteFile(fileNo)) {// DB의 파일정보 삭제
			// 실패시 false
			return false;
		}

		String upPath = request.getSession().getServletContext().getRealPath("/") + "upload/";
		File file = new File(upPath + fData.getSysFileName());
		if (file.exists()) { // 파일이 존재하는 경우
			return file.delete(); // 실제 파일 삭제
		}

		return false;
	}

}
