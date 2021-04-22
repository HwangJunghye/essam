<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<script>
//console.dir(${bList2 });
</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<section>

<center>
<h3><c:if test="${empty boardData}">글쓰기</c:if><c:if test="${!empty boardData}">글수정</c:if></h3>
<table>
<tr>
	<th>제목</th>
	<td><input type="text" name="clsBrdTitle" id="clsBrdTitle" required/></td>
</tr>
<tr>
	<th>내용</th>
	<td><input type="textarea" name="" id="" rows="20" cols="60" required/></td>
</tr>
<tr>
	<th>첨부파일</th>
	<td><input type="file" name="files" id="files" multiple/></td>
</tr>
</table>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>