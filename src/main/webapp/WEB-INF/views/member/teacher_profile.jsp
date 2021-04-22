<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
	<h1>teacher_profile.jsp</h1>
	<c:if test="${!empty teacherInfo}">
		강사닉네임 : ${teacherInfo.mbNickName}<br/>
		강사프로필이미지 : ${teacherInfo.fileNo}<br/>
		강사등급 : ${teacherInfo.teacherGrade}<br/>
		한줄 소개 : ${teacherInfo.teacherIntro}<br/>
		상세 소개 : ${teacherInfo.teacherDetail}<br/>
		<a href="${ctxPath}/teacher_profile/write">수정</a>
		<a href="${ctxPath}/teacher_profile/delete">삭제</a>
	</c:if>
	<c:if test="${empty teacherInfo}">
		${msg}
		<a href="${ctxPath}/teacher_profile/write">등록</a>
	</c:if>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>