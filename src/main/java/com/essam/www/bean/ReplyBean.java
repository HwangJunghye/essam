package com.essam.www.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.experimental.Accessors;

@Alias("replybean")
@Data
@Accessors(chain = true)
public class ReplyBean {
	 private String clsBrdRepNo;
	 private String clsBrdNo;
	 private String mbId;
	 private String fileNo;
	 private String clsBrdRepDate;
	 private String clsBrdRepContent;
	 private String mbNickName;
	 private String origFileName;
	
}
