package com.essam.www.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.exception.CommonException;

import lombok.extern.log4j.Log4j;

@ControllerAdvice
@Log4j
public class BAdvice {
	@ExceptionHandler(CommonException.class)
	public String except(CommonException ex, RedirectAttributes attr) {
		attr.addFlashAttribute("msg", ex.getMessage());
		return "redirect: /class/boardlist";
	} 
}
	