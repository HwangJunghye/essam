<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="../common/header.jsp" %>
	<section>
	인덱스 페이지(/)<br/>
	${msg}<br>
	${fMsg}
	<li>New 클래스 
	<table>
	<c:if test="${empty cList}">
		<tr><td>등록된 클래스 정보가 없습니다.</td></tr>
	</c:if>
	<c:if test="${!empty cList}">
		<c:forEach var="eclass" items="${cList}">
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