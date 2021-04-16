<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>테스트 페이지</title>
</head>
<body>
	<!-- t를 넣으면 교사회원 s를 넣으면 학생회원, 아랫줄을 지우면 로그아웃 출력 -->
	<% request.setAttribute("login", "t"); %>
	<div class="header">
		<%@ include file="../test/testinclude.jsp" %>
	</div>
	<a href="../test/1">DB 연결 테스트</a><br/>
	<a href="../test/2">이미지 업로드 및 크기변경 테스트</a><br/>
	<a href="../test/3">암호화 테스트</a><br/>
	
	<form action="uploadtest" method="post" enctype="multipart/form-data">
		파일 업로드 <input type="file" name="file" accept="image/*"/><br>
		<input type="submit" value="파일전송"/>
	</form>
</body>
</html>