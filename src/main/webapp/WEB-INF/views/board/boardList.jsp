<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<%@ page import = "com.essam.www.constant.Constant" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<script>
//console.dir(${bList2 });
</script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp"%>
<section>
<div id="contents">
	<div id="aside">
		<div id="aside_area">
			<ul>
				<li><a href="${ctxPath}/class/boardlist?clsNo=${clsNo}&clsBrdType=1&pageNum=1">공지사항/자료실</a></li>
				<li><a href="${ctxPath}/class/boardlist?clsNo=${clsNo}&clsBrdType=2&pageNum=1">과제</a></li>
			</ul>
		</div>
	</div>
	<div id="contents_area">
		<h3>클래스명 : ${clsName}</h3>
		<table class="container">
		<tr><td></td>
			<td align="left">전체 : ${totalNum} 건</td>
			<td align="right">
				<c:if test="${sessionScope.loginData.mbType==2}">
					<button type="button" onclick="location.href='${ctxPath}/class/goboardwrite?clsNo=${clsNo}&clsBrdType=${clsBrdType}';">글쓰기</button>
				</c:if></td>
			<td></td></tr>
		</table>
		<div class="container">
		<table class='table table-hover' style='background-color: white;' width=760>
		<thead>
		<tr>
			<th>번호</th>
			<th>제 목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>첨부파일</th>
			<th>조회</th>
		</tr>
		</thead>
		<c:if test="${empty bList}">
			<thead>
			<tr class='prod'>
				<td colspan="6" align="center" height="200">등록된 게시물이 없습니다.</td>
			</tr>
			</thead>
		</c:if>
		<c:if test="${!empty bList}">
			<tbody class='tbl'>
				<c:forEach var="board" items="${bList}">
					<tr class='prod'>
						<td>${board.clsBrdNo}</td>
						<td><a href="${ctxPath}/class/boardread?clsBrdNo=${board.clsBrdNo}&pageNum=${param.pageNum}">${board.clsBrdTitle}</a></td>
						<td>${board.mbNickName}</td>
						<td>${board.clsBrdDate}</td>
						<td><c:if test="${board.clsBrdfileCnt > 0}"><img src="${ctxPath}/resources/images/icon_file.jfif" width=18></c:if></td>
						<td>${board.clsBrdView}</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>
		</table>
		</div>
		<!-- 페이징 -->
		${paging}

	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>