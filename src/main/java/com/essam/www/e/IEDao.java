package com.essam.www.e;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.essam.www.bean.ClassBean;

public interface IEDao {

	boolean changePassword(@Param("mbId") String mbId, @Param("mbPwd") String mbPwd);
}
