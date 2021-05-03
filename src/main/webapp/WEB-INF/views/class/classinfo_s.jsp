<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctxPath" value="<%= request.getContextPath() %>"/>
<% //@Author 고연미 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e-쌤</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/basic.css">
<link rel="stylesheet" type="text/css" href="${ctxPath}/resources/css/class.css">
<link rel="icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctxPath}/resources/images/favicon_essam.ico" type="image/x-icon">
</head>
<body>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/nav.jsp" %>
<section>
<div id="contents">
	<div id="aside">
		<div id="aside_area">
			<%@ include file="../common/aside.jsp"%>
		</div>
	</div>
	<div id="contents_area">
<!--------- 본문 시작 -------------->

		<table class="container">
		<tr>
			<td align="left" style="padding:20px 0;">
				<h6><i class="fab fa-edge-legacy"></i> 클래스 <i class="fas fa-angle-right"></i> <span style="font-weight: bold;background-color:#f4edd8;">${classInfo.clsName}</span></h6>
				<hr class="hr_${mbColor}"></td>
		</tr></table>
		
		<%@ include file="../class/inc_classinfo.jsp" %>

<!--------- 본문 끝 -------------->
	</div>
</div>
	
</section>
<%@ include file="../common/footer.jsp" %>
</body>
</html>