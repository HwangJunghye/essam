package com.essam.www.b;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.essam.www.bean.BoardBean;
import com.essam.www.bean.ClassBean;
import com.essam.www.bean.FileBean;

public interface IBDao {

	List<ClassBean> getClassListNew();
	
	List<ClassBean> getClassListHot(); 

	List<ClassBean> getClassListMy(String mbId);

	ClassBean getClassInfo(String clsNo);

	boolean classJoin(@Param("clsNo") String clsNo, @Param("mbId") String mbId);

	ArrayList<BoardBean> getBoardList(@Param("clsNo") String clsNo, @Param("clsBrdType") Integer clsBrdType, @Param("pageNum") Integer pageNum);

	int getBoardFileCnt(String clsBrdNo);

	BoardBean getBoardRead(String clsBrdNo);

	int getBoardCount(@Param("clsNo") String clsNo, @Param("clsBrdType") Integer clsBrdType);

	List<FileBean> getBoardFiles(String clsBrdNo);

	String getClassName(String clsNo);

	boolean boardUpdate(BoardBean board);

	boolean boardInsert(BoardBean board);

	boolean brdFileInsert(@Param("clsBrdNo") String clsBrdNo, @Param("fileNo") String fileNo);

	boolean deleteBrdFile(String fileNo);

	boolean deleteBrd(String clsBrdNo);

	boolean deleteReplyList(String clsBrdNo);

	int getBrdViewId(@Param("clsBrdNo") String clsBrdNo, @Param("mbId") String mbId);

	void addBrdView(@Param("clsBrdNo") String clsBrdNo, @Param("mbId") String mbId);

	boolean delBrdView(String clsBrdNo);

	int hasClassJoin(@Param("clsNo") String clsNo, @Param("mbId") String mbId);

	boolean addClsView(@Param("clsNo") String clsNo, @Param("sessionId") String sessionId, @Param("mbId") String mbId);

	int getClsViewCnt(@Param("clsNo") String clsNo, @Param("sessionId") String sessionId);

}
