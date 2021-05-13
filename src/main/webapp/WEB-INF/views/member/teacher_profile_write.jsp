<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<% //Author : 조참 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e-쌤</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<style>
input[type="text"],input[type="file"] {
  width:500px;
  height:35px;
  font-size:15px;
}
input[type="submit"]{
	width: 70px;
	height: 36px;
	box-shadow: 3px 3px 4px 1px rgba(0, 0, 0, 0.1);
	border-radius: 10px;
	color: #FFFFFF;
	text-align: center;
	border: none;
	cursor: pointer;
}
.center{
	height: 200px;
	margin-left: 300 px;
    margin-right: auto;
}
th, td{
	text-align: left;	
}
.object{
	width:200px;
	height:35px;
	font-size:15px;
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

<form id="t_profile" action="update" method="post" enctype="multipart/form-data">
	<br><br><br>
	<table class="center">
	<tr>
		<th class="object" width=180><nobr><label for="mbNickName">강사닉네임</label></nobr></th>
		<td>${sessionScope.loginData.mbNickName}</td>
	</tr>
	<tr>
		<th class="object"><label for="teacherIntro">한줄소개</label></th>
		<td><input type="text" name="teacherIntro" value="${teacherInfo.teacherIntro}"/></td>
	</tr>
	<tr>
		<th class="object"><label for="teacherDetail">상세소개</label></th>
		<td><textarea rows=5 cols=90 id="t_detail" name="teacherDetail">${teacherInfo.teacherDetail}</textarea></td>
	</tr>
	<tr>
		<th class="object"><label for="file">프로필사진</label></th>
		<td><input type="file" name="file" accept="image/*" /></td>
	</tr>
	<tr height="100">
		<td></td>
		<td><input type="submit" class="${mbColor}" value="등록" /></td>
	</tr>
	</table>
</form>
<!--------- 본문 끝 -------------->
	</div>
</div>
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>