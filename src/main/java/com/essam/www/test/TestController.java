package com.essam.www.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.exception.TestException;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
@RequestMapping(value = "/test")
public class TestController {
	@Autowired
	ITestDao tDao;

	// 테스트 메인 페이지(../test/)
	@RequestMapping(value = "/")
	String testMain() {
		return "test/testmain";
	}

	@RequestMapping(value = "/1")
	public String Test1() {
		return "test/testdb";
	}

	@RequestMapping(value = "/1result")
	public ModelAndView Test1Result(TestBean numbers) {
		ModelAndView mav = new ModelAndView();
		// DB를 이용해 덧셈을 계산(테스트)
		mav.addObject("result", tDao.add(numbers));
		mav.setViewName("test/testdbresult");
		return mav;
	}

	@RequestMapping(value = "/2")
	public String Test2() {
		return "test/testimage";
	}

	@RequestMapping(value = "/2result")
	@ResponseBody
	public void Test2Result(MultipartHttpServletRequest multireq, HttpServletResponse resp) {
		// 업로드한 파일 가져오기
		MultipartFile image = multireq.getFile("image");
		// 파일 이름 가져오기
		String imageName = image.getOriginalFilename();
		// 파일의 ContentType 가져오기
		System.out.println(image.getContentType());
		// 파일의 출력타입 설정하기
		resp.setContentType(image.getContentType());
		try {
			// 파일 출력 준비
			OutputStream out = resp.getOutputStream();
			// 이미지 파일 읽을 준비
			InputStream imageIn = image.getInputStream();
			// 100*100 보다 작은 크기로 원본비율 유지 변환해 출력
			Thumbnailator.createThumbnail(imageIn, out, 100, 100);
			/*
			 * byte[] buffer = new byte[1024]; int length; // 파일을 1MB씩 읽어 출력 while ((length
			 * = in.read(buffer)) != -1) { out.write(buffer, 0, length); } out.close();
			 * in.close();
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/3")
	String Test3() {
		return "test/testsecure";
	}

	@RequestMapping(value = "/3result")
	ModelAndView Test3Result(String word) throws TestException {
		// 예외처리 테스트. 
		// 예외 발생시 @ControllerAdvice의 @ExceptionHandler 메소드로 이동.
		throw new TestException("TestException 예외 발생");
	}
}
