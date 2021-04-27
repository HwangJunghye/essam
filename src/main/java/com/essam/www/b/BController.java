package com.essam.www.b;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.essam.www.bean.BoardBean;
import com.essam.www.bean.FileBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.exception.CommonException;
import com.essam.www.file.FileMM;

@Controller
public class BController {
	
	@Autowired
	private BMM bm;
	@Autowired
	private FileMM fm;
	@Autowired
	private IBDao bDao;
	
	/**
	 * 클래스소개 이동
	 * Author : 고연미
	 */
	@RequestMapping(value = "/classinfo")
	ModelAndView goClassInfo(String clsNo) {
		return bm.goClassInfo(clsNo);
	}
	/**
	 * 수강신청
	 * Author : 고연미
	 */
	@RequestMapping(value = "/classjoin")
	ModelAndView classJoin(String clsNo, HttpSession session, RedirectAttributes rattr) {
		MemberBean loginData = (MemberBean)session.getAttribute("loginData");
		String mbId= loginData.getMbId();
		return bm.classJoin(clsNo, mbId, rattr);
	}
	/**
	 * 게시판 리스트
	 * Author : 고연미
	 */
	@RequestMapping(value = "/class/boardlist")
	ModelAndView boardList(String clsNo, Integer clsBrdType, Integer pageNum, HttpServletRequest request) {
		return bm.goBoardList(clsNo, clsBrdType, pageNum, request);		
		// ControllerAdvide 안쓰고 예외처리
		// try {
		// 	 bm.goBoardList(clsNo, clsBrdType, pageNum);
		// } catch(CommonException err){
		// 		예외가 발생한 경우
		// }
	}
	/**
	 * 게시판 글쓰기/수정 이동
	 * Author : 고연미
	 */
	@RequestMapping(value = "/class/goboardwrite")
	ModelAndView goBoardWrite(String clsNo, Integer clsBrdType, String clsBrdNo, Integer pageNum) {
		return bm.goBoardWrite(clsNo, clsBrdType, clsBrdNo, pageNum);		
	}
	/**
	 * 게시판 글쓰기/수정
	 * Author : 고연미
	 */
	@PostMapping(value = "/class/boardwrite")
	ModelAndView boardWrite(BoardBean board, MultipartHttpServletRequest mReq, HttpServletRequest request, RedirectAttributes rattr) {
		return bm.boardWrite(board, mReq, request, rattr);		
	}
	/**
	 * 게시판 글읽기
	 * Author : 고연미
	 */
	@RequestMapping(value = "/class/boardread")
	ModelAndView boardRead(String clsBrdNo, Integer pageNum, HttpServletRequest request) {
		return bm.boardRead(clsBrdNo, pageNum, request);
	}
	/**
	 * 게시판 글 삭제
	 * Author : 고연미
	 */
	@RequestMapping(value = "/class/boarddelete")
	ModelAndView boardDelete(String clsBrdNo, Integer pageNum, HttpServletRequest request, RedirectAttributes rattr) {
		return bm.boardDelete(clsBrdNo, pageNum, request, rattr);
	}
	/**
	 * (ajax) 첨부파일 리스트 가져오기
	 * Author : 고연미
	 */
	@RequestMapping(value = "/class/getfilelist")
	@ResponseBody List<FileBean> getFileList(String clsBrdNo) {
		return bDao.getBoardFiles(clsBrdNo);
	}
	/**
	 * (ajax) 첨부파일 삭제
	 * Author : 고연미
	 */
	@PostMapping(value = "/class/delbrdfile")
	@ResponseBody List<FileBean> delBrdFile(String fileNo, String clsBrdNo, HttpServletRequest request) {
		return bm.delBrdFile(fileNo, clsBrdNo, request);
	}
}
