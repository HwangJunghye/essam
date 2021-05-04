package com.essam.www.eclass;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.essam.www.bean.BoardBean;
import com.essam.www.bean.ClassBean;
import com.essam.www.bean.FileBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.bean.ReplyBean;
import com.essam.www.bean.StudentBean;

public interface IClassDao {
	// class-mapper.xml 사용
	//@Author 황정혜	
	ClassBean getMyClassList(String clsNo);
	
	List<StudentBean> getStudentList(String clsNo);

	StudentBean getStudentInfo(@Param("clsNo")String clsNo, @Param("mbId")String mbId);

	boolean classClassinfoUpdate(ClassBean cb);

	boolean classClassinfoInsert(ClassBean cb);

	String getFileNo(String clsNo);
	
	//@Author 고연미
	ClassBean getClassInfo(String clsNo);
	
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
		
	//@Author 임다영
	List<ReplyBean> getReplyList(String clsBrdNo);

	void addReply(ReplyBean rb);

	ReplyBean getReply(String clsBrdRepNo);

	void updateReply(ReplyBean rb);

	void deleteReply(@Param("clsBrdRepNo") String clsBrdRepNo, @Param("mbId") String mbId);
}
