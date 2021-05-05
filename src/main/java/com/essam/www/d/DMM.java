package com.essam.www.d;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.MemberBean;
import com.essam.www.bean.ReplyBean;
import com.essam.www.file.FileMM;
import com.essam.www.member.IMemberDao;

@Service
public class DMM {
	@Autowired
	private IDDao DDao;
	@Autowired
	private FileMM fm;
	@Autowired
	private IMemberDao mDao;
	
}
