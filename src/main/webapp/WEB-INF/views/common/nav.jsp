<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/nav.css">
<body>

<nav class="${sessionScope.loginData.mbType==2?'강사':'강사아님'}">
${navtext}
</nav>

</body>
</html>