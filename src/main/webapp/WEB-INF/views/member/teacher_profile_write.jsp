<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강사 프로필 작성</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<body>

<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<h1>teacher_profile_write.jsp</h1>
<form action="memberjoin" method="post">
	강사닉네임 ${sessionScope.loginData.mbNickName}
	한줄소개 <input type="text" name="teacherInfo" value="${teacherInfo.teacherIntro}"/><br>
	상세소개 <textarea name="teacherDetail">${teacherInfo.teacherDetail}</textarea><br>
	프로필사진 <input type="file" name="fileNo" /><br>
</form>
</section>
<%@ include file="../common/footer.jsp" %>


</body>
</html>