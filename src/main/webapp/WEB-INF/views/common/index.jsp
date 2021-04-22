<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<body>
<%@ include file="../common/header.jsp" %>
<section>
	인덱스 페이지(/)<br/>
	${msg}<br>
	${fMsg}
	
	<!-- 수강중인 클래스가 있으면 -->
	<c:if test="${!empty myList}">
		<li>수강중인 클래스
		<table>
		<c:forEach var="eclass" items="${myList}">
			<tr>
				<td>번호:${eclass.clsNo}</td>
				<td>클래스명:<a href="">${eclass.clsName}</a></td>
				<td>${eclass.mbId}</td>
			</tr>
		</c:forEach>
		</table>
	</c:if>
	
	<li>New 클래스 
	<table>
	<c:if test="${empty nList}">
		<tr><td>등록된 New클래스 정보가 없습니다.</td></tr>
	</c:if>
	<c:if test="${!empty nList}">
		<c:forEach var="eclass" items="${nList}">
			<tr>
				<td>번호:${eclass.clsNo}</td>
				<td>클래스명:<a href="">${eclass.clsName}</a></td>
				<td>${eclass.mbId}</td>
			</tr>
		</c:forEach>
	</c:if>
	</table>
</section>

<%@ include file="../common/footer.jsp" %>

</body>
</html>