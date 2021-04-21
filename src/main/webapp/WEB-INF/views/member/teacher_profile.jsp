<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<section>
	<h1>teacher_profile.jsp</h1>
	강사닉네임 : ${teacherInfo.mbNickName}<br/>
	강사프로필이미지 : ${teacherInfo.fileNo}<br/>
	강사등급 : ${teacherInfo.teacherGrade}<br/>
	한줄 소개 : ${teacherInfo.teacherIntro}<br/>
	상세 소개 : ${teacherInfo.teacherDetail}<br/>
	${msg}
	<p>
		<a href="${ctxPath}/teacher_profile/write?mbId=${teacherInfo.mbId}">수정</a>
		<a href="${ctxPath}/teacher_profile/delete?mbId=${teacherInfo.mbId}">삭제</a>
	</p>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>