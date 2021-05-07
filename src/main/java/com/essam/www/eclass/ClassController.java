package com.essam.www.eclass;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.essam.www.bean.BoardBean;
import com.essam.www.bean.ClassBean;
import com.essam.www.bean.FileBean;
import com.essam.www.bean.ReplyBean;

@Controller
public class ClassController {
	@Autowired
	private ClassMM cm;
	@Autowired
	private IClassDao cDao;	
	
	// (CM01)클래스 소개 이동(관계자용)
	@RequestMapping(value = "/class/classinfo")
	ModelAndView goClassClassInfo(String clsNo, HttpSession session) {
		return cm.goClassClassInfo(clsNo, session);
	}
	// (CM02+CM03)게시판(공지사항/과제) 목록 페이지 이동 + 게시판 목록 가져오기	
	@RequestMapping(value = "/class/boardlist")
	ModelAndView boardList(String clsNo, Integer clsBrdType, Integer pageNum, HttpServletRequest request) {
		return cm.goBoardList(clsNo, clsBrdType, pageNum, request);		
		// ControllerAdvide 안쓰고 예외처리
		// try {
		// 	 cm.goBoardList(clsNo, clsBrdType, pageNum);
		// } catch(CommonException err){
		// 		예외가 발생한 경우
		// }
	}
	// (CM06+CM07)게시판(공지사항/과제) 상세 페이지 이동 + 게시글 상세 가져오기
	@RequestMapping(value = "/class/boardread")
	ModelAndView boardRead(String clsBrdNo, Integer pageNum, HttpServletRequest request) {
		return cm.boardRead(clsBrdNo, pageNum, request);
	}
	// (CM10)게시판(공지사항/과제) 등록/수정 페이지 이동
	@RequestMapping(value = "/class/goboardwrite")
	ModelAndView goBoardWrite(String clsNo, Integer clsBrdType, String clsBrdNo, Integer pageNum) {
		return cm.goBoardWrite(clsNo, clsBrdType, clsBrdNo, pageNum);		
	}
	// (CM11)게시판(공지사항/과제) 등록/수정
	@PostMapping(value = "/class/boardwrite")
	ModelAndView boardWrite(BoardBean board, MultipartHttpServletRequest mReq, HttpServletRequest request, RedirectAttributes rattr) {
		return cm.boardWrite(board, mReq, request, rattr);		
	}
	// (CM12)(수정시)Ajax 첨부파일 리스트 가져오기
	@RequestMapping(value = "/class/getfilelist")
	@ResponseBody List<FileBean> getFileList(String clsBrdNo) {
		return cDao.getBoardFiles(clsBrdNo);
	}
	// (CM13)(수정시)Ajax 첨부파일 삭제
	@PostMapping(value = "/class/delbrdfile")
	@ResponseBody List<FileBean> delBrdFile(String fileNo, String clsBrdNo, HttpServletRequest request) {
		return cm.delBrdFile(fileNo, clsBrdNo, request);
	}
	// (CM14)게시글(공지사항/과제) 삭제
	@RequestMapping(value = "/class/boarddelete")
	ModelAndView boardDelete(String clsBrdNo, Integer pageNum, HttpServletRequest request, RedirectAttributes rattr) {
		return cm.boardDelete(clsBrdNo, pageNum, request, rattr);
	}
	// (CM16)댓글 목록 가져오기(ajax)
	@RequestMapping(value="/class/getreplylist")
	@ResponseBody List<ReplyBean> getReplyList(String clsBrdNo){
		System.out.println("clsBrdNo =====> "+ clsBrdNo);
		List<ReplyBean> rList = cm.getReplyList(clsBrdNo);
		System.out.println("rList ====> "+ rList.size());
		return rList;
	}
	// (CM17)댓글 등록(ajax)
	@RequestMapping(value = "/class/addreply")
	@ResponseBody List<ReplyBean> addReply(ReplyBean rb, MultipartHttpServletRequest mReq){
		System.out.println("여기 오나 ==============>"+ rb.getClsBrdNo());
		List<ReplyBean> rList = cm.addReply(rb,mReq);
		return rList;
	}
	// (CM18)댓글 수정(ajax)	
	@RequestMapping(value = "/class/updatereply")
	@ResponseBody List<ReplyBean> updateReply(ReplyBean rb, MultipartHttpServletRequest mReq){
		List<ReplyBean> rList = cm.updateReply(rb, mReq);
		return rList;
	}
	// (CM19)댓글 삭제(ajax)
	@RequestMapping(value = "/deletereply")
	@ResponseBody List<ReplyBean> deleteReply(String clsBrdRepNo, HttpServletRequest req, String clsBrdNo){
		List<ReplyBean> rList = cm.deleteReply(clsBrdRepNo, req, clsBrdNo);
		return rList;
	}
	
	// (CM20+CM21)출석 현황 이동 + 출석현황 가져오기
	@RequestMapping(value = "/class/attend")
	ModelAndView goAttend(HttpServletRequest request, String clsNo){
		ModelAndView mav = cm.goAttend(request, clsNo);
		return mav;
		}
	
	
	// (CM22+CM23)학생목록 이동 + 학생목록 가져오기	
	@RequestMapping(value = "/class/studentlist")
	ModelAndView goStudentList(String clsNo) {
		ModelAndView mav = cm.goStudentList(clsNo);
		return mav;
		}
		
	// (CM24+CM25)학생정보보기 이동 + 학생정보 가져오기	
	@RequestMapping(value = "/class/studentinfo")
	ModelAndView goStudentInfo(String mbId, String clsNo){
		ModelAndView mav = cm.goStudentInfo(mbId, clsNo); 
		return mav;
		}	
	
	// (CM26)클래스 등록, 수정 페이지 이동
	@RequestMapping(value = "/class/classinfo/write")
	ModelAndView goClassInfoWrite(String clsNo) {
		ModelAndView mav = cm.goClassInfoWrite(clsNo);
		return mav;
		}
	// (CM27)클래스 등록, 수정하기	
	@PostMapping(value = "/class/classinfo/update")
	ModelAndView classClassinfoUpdate(MultipartHttpServletRequest mReq, HttpServletRequest request, ClassBean cb) {
		ModelAndView mav = cm.classClassinfoUpdate(mReq, request, cb);
		return mav;
		}
	// (CM28)클래스 삭제
	@RequestMapping(value = "/class/deleteclass")
	ModelAndView classDelete(String clsNo, HttpServletRequest request, RedirectAttributes rattr) {
		ModelAndView mav = cm.classDelete(clsNo, request, rattr);
		return mav;
		}
	
	// @author 고연미 on 2021.05.07
	// Ajax 해당클래스 로그인한 학생목록(mbId) 가져오기(담당 강사가 맞는지 체크 후)
	@RequestMapping(value = "/class/getonstudents")
	@ResponseBody Map<String, String> getOnStudents(String clsNo, String mbId){
		Map<String,String> sMap = cm.getOnStudents(clsNo,mbId);
		return sMap;
	}
	
}
