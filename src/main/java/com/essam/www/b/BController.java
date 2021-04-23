package com.essam.www.b;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.BoardBean;
import com.essam.www.bean.MemberBean;
import com.essam.www.exception.CommonException;
import com.essam.www.file.FileMM;

@Controller
public class BController {
	@Autowired
	private BMM bm;
	@Autowired
	private FileMM fm;
	
	/**
	 * 클래스소개 이동
	 */
	@RequestMapping(value = "/classinfo")
	ModelAndView goClassInfo(String clsNo) {
		return bm.goClassInfo(clsNo);
	}
	/**
	 * 수강신청
	 */
	@RequestMapping(value = "/classjoin")
	ModelAndView classJoin(String clsNo, HttpSession session, RedirectAttributes rattr) {
		MemberBean loginData = (MemberBean)session.getAttribute("loginData");
		String mbId= loginData.getMbId();
		return bm.classJoin(clsNo, mbId, rattr);
	}
	/**
	 * 게시판 리스트
	 */
	@RequestMapping(value = "/class/boardlist")
	ModelAndView boardList(String clsNo, Integer clsBrdType, Integer pageNum, HttpServletRequest request) throws CommonException{
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
	 */
	@RequestMapping(value = "/class/goboardwrite")
	ModelAndView goBoardWrite(String clsNo, Integer clsBrdType, String clsBrdNo, Integer pageNum) {
		return bm.goBoardWrite(clsNo, clsBrdType, clsBrdNo, pageNum);		
	}
	/**
	 * 게시판 글쓰기/수정
	 */
	@PostMapping(value = "/class/boardwrite")
	ModelAndView boardWrite(BoardBean board, MultipartHttpServletRequest mReq, HttpServletRequest request, RedirectAttributes rattr) {
		return bm.boardWrite(board, mReq, request, rattr);		
	}
	/**
	 * 게시판 글읽기
	 */
	@RequestMapping(value = "/class/boardread")
	ModelAndView boardRead(String clsBrdNo, Integer pageNum, HttpServletRequest request) throws CommonException{
		return bm.boardRead(clsBrdNo, pageNum, request);
	}
	/**
	 * 게시판 글 삭제
	 */
	@RequestMapping(value = "/class/boarddelete")
	ModelAndView boardDelete(String clsBrdNo, Integer pageNum, HttpServletRequest request) throws CommonException{
		return bm.boardDelete(clsBrdNo, pageNum, request);
	}
	/**
	 * 게시판 파일 삭제
	 */
	@PostMapping(value = "/class/delbrdfile")
	public @ResponseBody boolean delBrdFile(@RequestBody String fileNo, HttpServletRequest request, RedirectAttributes rattr) {
		return fm.deleteFile(fileNo, request);	
	}
	
}
