<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<script>
//console.dir(${bList2 });
</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp"%>
<section>

<center>
<h3>클래스 : ${clsName}</h3>

<table width=760>
<tr>
	<td colspan=3>[${boardData.clsBrdNo}] ${boardData.clsBrdTitle}</td>
</tr>
<tr>
	<td>${boardData.mbNickName}</td>
	<td>${boardData.clsBrdDate}</td>
	<td>조회 <fmt:formatNumber value="${boardData.clsBrdView}" type="number"/></td>
</tr>
<tr>
	<td colspan=3 style="white-space:pre-wrap;word-break:break-all;">${boardData.clsBrdContent}</td>
</tr>
<tr>
	<td>첨부파일</td>
	<td colspan=2>	
		<c:set var="files" value="${boardData.filesInfo}" />
		<c:if test="${!empty files}">
			<c:forEach var="file" items="${files}">
				<c:if test="${file.fileTypeNo == 1}"><i class="far fa-file-image"></i></c:if>
				<c:if test="${file.fileTypeNo == 2}"><i class="far fa-file-video"></i></c:if>
				<c:if test="${file.fileTypeNo == 3}"><i class="far fa-file-alt"></i></c:if>
				<a href="${ctxPath}/download?fileNo=${file.fileNo}">${file.origFileName}</a>&nbsp;&nbsp;
			</c:forEach>
		</c:if></td>
</tr>
</table>
<p>
<table width=760>
<tr>
	<td>${btnUpdate}</td>
	<td align="right"><button type="button" id="goList" onclick="location.href='${ctxPath}/class/boardlist?clsNo=${boardData.clsNo}&clsBrdType=${boardData.clsBrdType}&pageNum=${pageNum}';">목록</button></td>
</tr>
</table></p>
</section>

<script>

</script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>