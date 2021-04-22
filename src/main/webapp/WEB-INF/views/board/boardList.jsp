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
<h3>Board 1.<a href="${ctxPath}/class/boardlist?clsNo=${clsNo}&clsBrdType=1">공지사항</a> 2.<a href="${ctxPath}/class/boardlist?clsNo=${clsNo}&clsBrdType=2">과제</a></h3>
<span style="color:red">${msg}</span>

<c:if test="${sessionScope.loginData.mbType==2}">
	<button type="button" onclick="location.href='${ctxPath}/class/boardWrite?clsNo=${clsNo}&clsBrdType=${clsBrdType}';">글쓰기</button>
</c:if>

<table class="tbl" width=600>
<tr height=30>
	<th>번호</th>
	<th>제 목</th>
	<th>작성자</th>
	<th>작성일</th>
	<th>첨부파일</th>
	<th>조회</th>
</tr>
<c:if test="${empty bList}">
<tr>
	<td colspan="6">등록된 글이 없습니다.</td>
</tr>
</c:if>
<c:if test="${!empty bList}">
	<c:forEach var="board" items="${bList}">
		<tr height=24>
			<td align="center">${board.clsBrdNo}</td>
			<td align="center"><a href="${ctxPath}/class/boardWrite?clsNo=${clsNo}&clsBrdType=${clsBrdType}&clsBrdNo=${board.clsBrdNo}">${board.clsBrdTitle}</a></td>
			<td align="center">${board.mbNickName}</td>
			<td align="center">${board.clsBrdDate}</td>
			<td align="center"><c:if test="${board.clsBrdfileCnt > 0}"><img src="${ctxPath}/resources/images/icon_file.jfif"></c:if></td>
			<td align="center">${board.clsBrdView}</td>
		</tr>
	</c:forEach>
</c:if>
</table>
<!-- 페이징 -->
${paging}
</center>

<!-- <form action="testmap">
	컬럼명 : <input type="text" name="cName"><br>
	검색 : <input type="text" name="search"><br>
	<button>enter</button>
</form> -->

</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>