<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>
	<c:if test="${empty login}">
	로그아웃 상태
	</c:if>
	<c:if test="${!empty login}">
		<c:if test="${login=='t'}">
			교사회원
		</c:if>
		<c:if test="${login=='s'}">
			학생회원
		</c:if>
	</c:if>
</body>
</html>