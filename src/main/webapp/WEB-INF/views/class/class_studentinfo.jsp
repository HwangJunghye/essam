<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
</head>
<style>
aside{
float:left;
top:150px;
left:0;
width: 158px;
height: 814px;
background: #E0E0E0;
}


</style>

<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>

<aside>
<ul>
<li>계정관리</li>
<li>마이 클래스</li>
</ul>
</aside>




<section>


<h1>class_studentinfo.jsp</h1>
	
	${sList}
	
	
</section>


<%@ include file="../common/footer.jsp" %>
</body>
</html>