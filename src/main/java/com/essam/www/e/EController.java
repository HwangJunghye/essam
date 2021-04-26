package com.essam.www.e;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EController {
	@Autowired
	private EMM em;

	/**
	 * 비밀번호 변경 실행(ajax)
	 * 
	 * */
	@PostMapping(value = "/changepassword")
	@ResponseBody Map<String,String> changePassword(HttpSession session, String mbPwd, String newMbPwd){
		return em.changePassword(session,mbPwd,newMbPwd);
	}
}
