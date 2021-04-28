<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<% //Author : 고연미 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e-쌤</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
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
			<td align="left" style="padding:20px 0;"><h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${clsName}</span></h6>
				<hr class="hr_${mbColor}"></td>
		</tr></table>

		<div class="container">

			<table width="86%" align="center" class="tbl_brd01">
			<tr>
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
				<td align="left">첨부파일</td>
				<td colspan=2 align="left">
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
				<td align="right"><button type="button" id="goList" onclick="location.href='${ctxPath}/class/boardlist?clsNo=${boardData.clsNo}&clsBrdType=${boardData.clsBrdType}&pageNum=${pageNum}';">목록</button></td>
			</tr>
			</table></p>
			<br><br><br><br>
		</div>

	<!---------- 본문 끝 ---------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>