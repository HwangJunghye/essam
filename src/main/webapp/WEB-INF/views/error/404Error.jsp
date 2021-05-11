<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 에러페이지 설정 --%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="errText" value='<%=response.getStatus() + " Error"%>'/>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<%-- IE 작동 위해 --%>
<%response.setStatus(200);%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${errText}</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/nav.jsp"%>
	<section>
	<h1>${errText}</h1>
	존재하지 않는 페이지입니다.
	</section>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>