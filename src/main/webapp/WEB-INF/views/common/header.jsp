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
	<header>
		<h3>header.jsp</h3>
		<c:if test="${empty sessionScope.loginData}">
			<!-- 로그아웃 상태 -->
			<a href="login">로그인</a>
		</c:if>
		<c:if test="${!empty sessionScope.loginData}">
			<!-- 로그인 상태 -->
			<c:if test="${sessionScope.loginData.mbType==1}">
				<!-- 학생인 경우 -->
				학생로그인
			</c:if>
			<c:if test="${sessionScope.loginData.mbType==2}">
				<!-- 강사인 경우 -->
				강사로그인
			</c:if>
		</c:if>
	</header>
</body>
</html>