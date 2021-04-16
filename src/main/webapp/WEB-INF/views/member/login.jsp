<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>로그인 페이지 ${fMsg}</h1>
<form action="access" method="post">
	아이디 <input type="text" name="mbId"/><br>
	비밀번호 <input type="text" name="mbPwd"/><br>
	<input type="submit" value="로그인"/>
</form>
</body>
</html>