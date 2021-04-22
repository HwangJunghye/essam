<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>클래스 등록</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<body>

<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<h1>class_write.jsp</h1>


<form action="class/classinfo/update" method="post">
	카테고리1:
	<input type="radio" name="cate1Name" value="초등">
	<input type="radio" name="cate1Name" value="중등">
	<input type="radio" name="cate1Name" value="고등">
	<input type="radio" name="cate1Name" value="특목">
	<input type="radio" name="cate1Name" value="입시">
	<input type="radio" name="cate1Name" value="기타">
	<br/>
	카테고리2:
	<input type="radio" name="cate2Name" value="국어">
	<input type="radio" name="cate2Name" value="영어">
	<input type="radio" name="cate2Name" value="수학">
	<input type="radio" name="cate2Name" value="사회">
	<input type="radio" name="cate2Name" value="과학">
	<input type="radio" name="cate2Name" value="독서">
	<input type="radio" name="cate2Name" value="논술">
	<input type="radio" name="cate2Name" value="입시컨설팅">
	<input type="radio" name="cate2Name" value="기타">
	<br/>
	클래스 명: <input type="text" name="clsName">
	<br/>
	클래스 정원: <input type="text" name="clsLimit">
	<br/>
	커버이미지: <input type="file" name="fileNo">///////
	<input type="button" value="첨부">
	<br/>
	한줄 소개:<input type="text" name="clsIntro">
	<br/>
	상세 소개:<input type="text" name="clsDesc">
	<br/>
	가격:<input type="text" name="clsPrice">
	<br/>
	키워드:<input type="text" name="clsKeyword">
	<br/>
	zoom링크:<input type="text" name="zoomLink">
	<br/>
	zoom비밀번호:<input type="text" name="zoomPwd">
	<br/>
	<br/>
	
	<input type="button" value="취소" onclick="reset()">
	<input type="submit" value="등록">
	
	</form>
	<script type="text/javascript">
	
	
	</script>
</section>
<%@ include file="../common/footer.jsp" %>

</body>
</html>