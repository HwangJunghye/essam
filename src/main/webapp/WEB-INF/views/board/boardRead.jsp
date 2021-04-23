<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<script>
//console.dir(${bList2 });
</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp"%>
<section>

<center>
<h3>클래스명 : ${clsName}</h3>

<table>
<tr>
	<td colspan=3>${boardData.clsBrdTitle}</td>
</tr>
<tr>
	<td>${boardData.mbNickName}</td>
	<td>${boardData.clsBrdDate}</td>
	<td>조회 <fmt:formatNumber value="${boardData.clsBrdView}" type="number"/></td>
</tr>
<tr>
	<td colspan=3>${boardData.clsBrdContent}</td>
</tr>
</table>
<p>${btnUpdate} <button type="button" id="goList" onclick="location.href='${ctxPath}/class/boardlist?clsNo=${boardData.clsNo}&clsBrdType=${boardData.clsBrdType}&pageNum=${pageNum}';">목록</button></p>
</section>

<script>

</script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>