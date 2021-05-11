<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 에러페이지 설정 --%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="errText" value='<%="ERROR CODE " + response.getStatus()%>'/>
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
	잘못된 파라미터입니다.
	<div>
		<%=exception.getMessage()%><br/>
		<c:forEach items="<%=exception.getStackTrace()%>" var="s">
		${s}<br/>
		</c:forEach>
		<br/><br/><br/><br/>
	</div>
	</section>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>