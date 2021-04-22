package com.essam.www.b;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.essam.www.bean.BoardBean;
import com.essam.www.bean.ClassBean;
import com.essam.www.bean.FileBean;

public interface IBDao {

	List<ClassBean> getClassListNew();

	List<ClassBean> getClassListMy(String mbId);

	ClassBean getClassInfo(String clsNo);

	int getClassRegiCnt(String clsNo);

	boolean classJoin(String clsNo, String mbId);

	ArrayList<BoardBean> getBoardList(@Param("clsNo") String clsNo, @Param("clsBrdType") Integer clsBrdType, @Param("pageNum") Integer pageNum);

	int getBoardFileCnt(String clsBrdNo);

	BoardBean getBoardRead(String clsBrdNo);

	int getBoardCount(@Param("clsNo") String clsNo, @Param("clsBrdType") Integer clsBrdType);

	List<FileBean> getBoardFiles(String clsBrdNo);

	String getClassName(String clsNo);

}
