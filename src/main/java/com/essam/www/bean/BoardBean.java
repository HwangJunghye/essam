package com.essam.www.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.experimental.Accessors;

@Alias("boardbean")
@Data
@Accessors(chain = true)
public class BoardBean {
	private String clsBrdNo;		// 게시글번호
	private String clsNo;			// 클래스번호
	private int clsBrdType;			// 게시판타입(1.notice 2.report)
	private String mbId;			// 작성자아이디
	private String mbNickName;		// 작성자닉네임
	private String clsBrdTitle;		// 제목
	private String clsBrdContent;	// 본문
	private String clsBrdDate;		// 작성일
	private List<MultipartFile> files;	// 첨부파일 등록시
	private List<FileBean> filesInfo;	// 첨부파일 정보
	private int clsBrdView;			// 조회수 
	private int clsBrdfileCnt;		// 첨부파일 갯수
	
}
