package com.essam.www.bean;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.experimental.Accessors;

@Alias("filebean")
@Data
@Accessors(chain = true)
public class FileBean {
	private String fileNo; // 파일일련번호
	private int fileTypeNo; // 파일타입일련번호
	private String origFileName; // 원본파일명
	private String sysFileName; // 저장파일명
	private String contentType; // 컨텐츠타입
}
