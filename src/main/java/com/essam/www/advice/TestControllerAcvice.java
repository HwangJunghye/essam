package com.essam.www.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.exception.TestException;

@ControllerAdvice
public class TestControllerAcvice {
	@ExceptionHandler(TestException.class)
	ModelAndView Test(HttpServletRequest request) {
		// ExceptionHandler에서는 파라미터를 직접 받지 못함.
		// request 객체에서 직접 가져와야 함.
		String word = request.getParameter("word");
		ModelAndView mav = new ModelAndView();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encword = encoder.encode(word);
		// 원본 문장과 암호화 된 문장 비교(true)
		System.out.println(encoder.matches(word, encword));
		mav.addObject("encword", encword);
		mav.setViewName("test/testsecureresult");
		return mav;
	}
	
}
