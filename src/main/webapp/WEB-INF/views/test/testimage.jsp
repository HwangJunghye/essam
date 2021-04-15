<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="../test/2result" method="post" enctype="multipart/form-data">
		이미지 선택 : <input type="file" name="image" accept=".png,.jpg"/><br/>
		<input type="submit" value="전송" />
	</form>
</body>
</html>