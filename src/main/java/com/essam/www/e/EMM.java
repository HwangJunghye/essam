package com.essam.www.e;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.essam.www.bean.ClassBean;
import com.essam.www.constant.Constant;

@Service
public class EMM {
	@Autowired
	private IEDao eDao;

}
