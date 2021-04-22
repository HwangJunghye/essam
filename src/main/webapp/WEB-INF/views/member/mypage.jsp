<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<body>

<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>

<h1>mypage.jsp</h1>
		회원타입 ${myInfo.mbType == 1?"학생":"강사"}<br>
		아이디 ${myInfo.mbId}<br>
	
	<form action="memberjoin" method="post">
		비밀번호 <input type="text" name="mbPwd"/><br>
		비밀번호 확인 <input type="text" name="mbPwdcheck" />
		<input type="button"> 비밀번호 변경 <br> 
		성명 ${myInfo.mbName}<br> 
		닉네임 <input	type="text" name="mbNickName" value="${myInfo.mbNickName}" required /><br> 
		성별 <input type="radio" name="mbGender" value="m" ${myInfo.mbGender=='m'?'checked':''} /> 남 
			<input type="radio" name="mbGender" value="w" ${myInfo.mbGender=='w'?'checked':''} /> 여 <br> 
		생년월일 <input type="date" name="mbBirth" value="${myInfo.mbBirth}" required /><br> 
		연락처 <input type="text" name="mbTel" value="${myInfo.mbTel}" required /><br> 
		주소 <input type="text" name="mbAddr" value="${myInfo.mbAddr}" required />
		<input type="button"> 주소검색 <br> 
		관심카테고리 1
		<input type="checkbox" name="cate1No" value="1"/>유아
		<input type="checkbox" name="cate1No" value="2"/>초등
		<input type="checkbox" name="cate1No" value="3"/>중등
		<input type="checkbox" name="cate1No" value="4"/>고등
		<input type="checkbox" name="cate1No" value="5"/>특목
		<input type="checkbox" name="cate1No" value="6"/>기타<br>
		관심카테고리 2
		<input type="checkbox" name="cate2No" value="1"/>국어
		<input type="checkbox" name="cate2No" value="2"/>영어
		<input type="checkbox" name="cate2No" value="3"/>수학
		<input type="checkbox" name="cate2No" value="4"/>과학
		<input type="checkbox" name="cate2No" value="5"/>논술
		<input type="checkbox" name="cate2No" value="6"/>독서
		<input type="checkbox" name="cate2No" value="7"/>입시컨설팅
		<input type="checkbox" name="cate2No" value="8"/>기타<br>
		<input type="submit" value="회원가입">
</form>
</section>
<%@ include file="../common/footer.jsp" %>


</body>
</html>