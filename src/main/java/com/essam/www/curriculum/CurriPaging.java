package com.essam.www.curriculum;

import javax.servlet.http.HttpServletRequest;

public class CurriPaging {
	private int totalNum; 			// 전체 글의 개수
	private int pageNum; 			// 현재 페이지 번호
	private int listCount; //10		// 페이지당 나타낼 글의 갯수
	private int pageCount; //2		// 페이지그룹당 페이지 갯수
	private String clsNo;

	public CurriPaging(int totalNum, int pageNum, int listCount, int pageCount, String clsNo) {
		this.totalNum = totalNum;
		this.pageNum = pageNum;
		this.listCount = listCount;
		this.pageCount = pageCount;
		this.clsNo = clsNo;
	}

	@SuppressWarnings("unused")	//unused 경고 무시하겠는 설정
	public String makeHtmlPaging(HttpServletRequest request) {
		// 전체 페이지 갯수
		int totalPage = (totalNum % listCount > 0)
				? totalNum/listCount+1 : totalNum/listCount;
		// 전체 페이지 그룹 갯수
		int totalGroup = (totalPage % pageCount > 0)
				? totalPage/pageCount+1 : totalPage/pageCount;
		// 현재 페이지가 속해 있는 그룹 번호
		int currentGroup = (pageNum % pageCount > 0)
				? pageNum/pageCount+1 : pageNum/pageCount;
		return makeHtml(currentGroup, totalPage, clsNo, request);
	}

	private String makeHtml(int currentGroup, int totalPage, String clsNo, HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		String ctxPath = request.getContextPath();
		
		//현재그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) 
				    - (pageCount - 1);
		//현재그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage)
				? totalPage
				: currentGroup * pageCount;
		
		sb.append("<div class=\"container\">");
		sb.append("<ul class=\"pagination justify-content-center\">");
		if (start != 1) {
			sb.append("<li class=\"page-item\">");
			sb.append("<a class=\"page-link\" href='"+ ctxPath +"/class/curriculum?clsNo="+ clsNo + "&pageNum=" + (start -1) + "'>");
			sb.append("이전");
			sb.append("</a></li>");
		}

		for (int i = start; i <= end; i++) {
			if (pageNum != i) { //현재 페이지가 아닌 경우 링크처리
				sb.append("<li class=\"page-item\">");
				sb.append("<a class=\"page-link\" href='"+ ctxPath +"/class/curriculum?clsNo="+ clsNo +"&pageNum=" + i + "'>");
				sb.append(i);
				sb.append("</a></li>");
			} else { //현재 페이지인 경우 링크 해제
				sb.append("<li class=\"page-item active\">");
				sb.append("<a class=\"page-link\" href=\"#\">");
				sb.append(i);
				sb.append("</a></li>");
			}
		}
		if (end != totalPage) {
			sb.append("<li class=\"page-item\">");
			sb.append("<a class=\"page-link\" href='"+ ctxPath +"/class/curriculum?clsNo="+ clsNo +"&pageNum=" + (end + 1) + "'>");
			sb.append("다음");
			sb.append("</a></li>");
		}
		sb.append("</ul>");
		sb.append("</div>");
		return sb.toString();
	}
}
