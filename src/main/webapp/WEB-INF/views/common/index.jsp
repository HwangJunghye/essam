<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e-쌤:::메인:::</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<div id="contents"><br><br>
	<ul>
	<!-- 수강중인 클래스가 있으면 -->
	<c:if test="${!empty myList}">
		<li>수강중인 클래스
		<table>
		<c:forEach var="eclass" items="${myList}">
			<tr>
				<td>번호:${eclass.clsNo}</td>
				<td>클래스명:<a href="${ctxPath}/class/curriculum?clsNo=${eclass.clsNo}">${eclass.clsName}</a></td>
				<td>${eclass.mbId}</td>
			</tr>
		</c:forEach>
		</table></li>
	</c:if>
	<br>
	<li>New 클래스 
	<table>
	<c:if test="${empty nList}">
		<tr><td>등록된 New클래스 정보가 없습니다.</td></tr>
	</c:if>
	<c:if test="${!empty nList}">
		<c:forEach var="eclass" items="${nList}">
			<tr>
				<td>번호:${eclass.clsNo}</td>
				<td>클래스명:<a href="${ctxPath}/classinfo?clsNo=${eclass.clsNo}">${eclass.clsName}</a></td>
				<td>${eclass.mbId}</td>
			</tr>
		</c:forEach>
	</c:if>
	</table></li>
	<br>
	<li>Hot 클래스 </li>
	</ul>
</div>
</section>

<%@ include file="../common/footer.jsp" %>
</body>
</html>