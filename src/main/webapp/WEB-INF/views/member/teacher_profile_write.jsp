<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<% //Author : 조참 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강사 프로필 작성</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<style>
#t_intro{
	width : 200 px;
	highth : 50 px;
}

</style>

</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<div id="contents">
	<div id="aside">
		<div id="aside_area">
			<%@ include file="../common/aside.jsp"%>
		</div>
	</div>
	<div id="contents_area">
<!--------- 본문 시작 -------------->

<form action="update" method="post" enctype="multipart/form-data">
	강사닉네임 ${sessionScope.loginData.mbNickName}<br>
	한줄소개 <input id="t_intro" type="text" name="teacherIntro" value="${teacherInfo.teacherIntro}"/><br>
	상세소개 <textarea id="t_detail" name="teacherDetail">${teacherInfo.teacherDetail}</textarea><br>
	프로필사진 <input id="file" type="file" name="file" accept="image/*" /><br>
	<input type="submit" value="등록" />
</form>
<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>