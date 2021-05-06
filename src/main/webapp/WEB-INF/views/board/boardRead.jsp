<%@page import="com.essam.www.bean.BoardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<% //@Author 고연미 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e-쌤</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp"%>
<section>
<div id="contents">
	<div id="aside">
		<div id="aside_area">
			<%@ include file="../common/aside.jsp"%>
		</div>
	</div>
	<div id="contents_area">
	<!---------- 본문 시작 ---------->
 
		<table class="container">
		<tr>
			<td align="left" style="padding:20px 0;">
				<h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${clsName}</span></h6>
				<hr class="hr_${mbColor}"></td>
		</tr></table>

		<div class="container">
			<table width="86%" align="center" class="tbl_brd01">
			<tr style="background-color:#eeeeee;">
				<td colspan=3 align="left">[${boardData.clsBrdNo}] ${boardData.clsBrdTitle}</td>
			</tr>
			<tr>
				<td align="left">${boardData.mbNickName}</td>
				<td align="left">${boardData.clsBrdDate}</td>
				<td align="left">조회 <fmt:formatNumber value="${boardData.clsBrdView}" type="number"/></td>
			</tr>
			<tr>
				<td colspan=3 align="left" style="white-space:pre-wrap;word-break:break-all;">${boardData.clsBrdContent}</td>
			</tr>
			<tr>
				<td align="left" class="align-middle">첨부파일</td>
				<td colspan=2 align="left" class="align-middle">
					<c:set var="files" value="${boardData.filesInfo}" />
					<c:if test="${!empty files}">
						<ul style="list-style-type:circle;">
						<c:forEach var="file" items="${files}"><li>
							<c:if test="${file.fileTypeNo == 1}"><i class="far fa-file-image"></i></c:if>
							<c:if test="${file.fileTypeNo == 2}"><i class="far fa-file-video"></i></c:if>
							<c:if test="${file.fileTypeNo == 3}"><i class="far fa-file-alt"></i></c:if>
							<a href="${ctxPath}/download?fileNo=${file.fileNo}">${file.origFileName}</a></li>
						</c:forEach>
						</ul>
					</c:if></td>
			</tr>
			</table>
			<p>
			<table width="86%" align="center">
			<tr>
				<td align="left">${btnUpdate}</td>
				<td align="right"><button type="button" id="goList" onclick="location.href='${ctxPath}/class/boardlist?clsNo=${boardData.clsNo}&clsBrdType=${boardData.clsBrdType}&pageNum=${pageNum}';" class='btn_normal_t'>목록</button></td>
			</tr>
			</table></p>
			<%//댓글이 있는 게시판이면 댓글목록 표시
			BoardBean bb = (BoardBean)request.getAttribute("boardData");
			if(Constant.clsBrdHasReply[bb.getClsBrdType()]) {%>
			<%@ include file="../board/replyList.jsp" %>
			<%}	%> 
			
			<br><br><br>
		</div>
		
		<script>
		function cfmDelBrd() {
			let msg = confirm('게시글을 정말 삭제하시겠습니까?'); 
			if(msg) {
				// GET 방식
				// location.href="${ctxPath}/class/boarddelete?clsBrdNo=${boardData.clsBrdNo}&pageNum=${pageNum}";
				// POST 방식
				let delForm = $('<form>',{action:'${ctxPath}/class/boarddelete',method:'post'});
				$('<input>',{type:'text', name:'clsBrdNo', value:'${boardData.clsBrdNo}'}).appendTo(delForm);
				$('<input>',{type:'text', name:'pageNum', value:'${pageNum}'}).appendTo(delForm);
				delForm.appendTo('body').submit();
			}
		}
		</script>

	<!---------- 본문 끝 ---------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>