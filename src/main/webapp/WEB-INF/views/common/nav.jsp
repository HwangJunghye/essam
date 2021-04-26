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

<nav class="${mbColor}">
	<div id="nav_title">
		<c:if test="${!empty navtext}">
			<i class="fas fa-quote-left"></i> ${navtext} <i class="fas fa-quote-right"></i>
		</c:if>
	</div>
</nav>

</body>
</html>